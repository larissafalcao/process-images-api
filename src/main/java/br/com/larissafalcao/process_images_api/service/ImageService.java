package br.com.larissafalcao.process_images_api.service;

import br.com.larissafalcao.process_images_api.ProfileEnum;
import br.com.larissafalcao.process_images_api.controller.dto.request.ImageMessage;
import br.com.larissafalcao.process_images_api.controller.dto.request.ImageRequest;
import br.com.larissafalcao.process_images_api.controller.dto.response.ImageResponse;
import br.com.larissafalcao.process_images_api.exception.ErrorProcessingImageException;
import br.com.larissafalcao.process_images_api.exception.ErrorSavingImageException;
import br.com.larissafalcao.process_images_api.persistence.domain.ImageEntity;
import br.com.larissafalcao.process_images_api.persistence.repository.ImageRepository;
import br.com.larissafalcao.process_images_api.producer.ImageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageProducer imageProducer;
    private final EmailService emailService;
    private final UserService userService;

    @Value("${default.directory}")
    private String defaultDirectory;

    @Value("${image.quota}")
    private int imageQuota;

    public void processImage(ImageMessage imageMessage) {
        final var imageEntity = imageRepository.findById(imageMessage.getImageId()).orElseThrow(() -> new RuntimeException("Image not found"));
        final var user = userService.findByLogin(imageMessage.getUserLogin());
        imageEntity.setUserId(user.getId());
        if(user.getProfile().equals(ProfileEnum.BASIC.name())){
            int imagesPerDayCount = imageRepository.countByUserIdAndRequestDateTimeBetween(
                    user.getId(),
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now());
            if(imagesPerDayCount + 1 > imageQuota){
                log.error("Limit of images reached for user: {}", user.getId());
                throw new ErrorProcessingImageException("Limit of images reached");
            }
        }
        if(imageEntity.getGrayscaleFilter().equals(Boolean.TRUE)){
            applyGrayscaleFilter(imageEntity);
        }
        if(imageEntity.getResizePercentage() != null){
            processImageResize(imageEntity);
        }
    }

    private void applyGrayscaleFilter(ImageEntity imageEntity) {
        try {
            BufferedImage image = ImageIO.read(new File(imageEntity.getImageName()));
            if (imageEntity.getGrayscaleFilter()) {
                image = applyGrayscaleFilter(image);
            }
            // Salvar a imagem processada
            ImageIO.write(image, "jpg", new File("processed_" + imageEntity.getImageName()));
        } catch (IOException e) {
            log.error("Error processing grayscale", e);
            throw new ErrorProcessingImageException("Error processing grayscale");
        }
    }

    private BufferedImage applyGrayscaleFilter(BufferedImage originalImage) {
        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayscaleImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();
        return grayscaleImage;
    }

    public void processImageResize(ImageEntity imageEntity) {
        try {
            BufferedImage image = ImageIO.read(new File(imageEntity.getImageName()));
            if (imageEntity.getGrayscaleFilter().equals(Boolean.TRUE)) {
                image = applyGrayscaleFilter(image);
            }
            if (imageEntity.getResizePercentage() != null) {
                image = resizeImage(image, imageEntity.getResizePercentage());
            }
            // Salvar a imagem processada
            String processedImagePath = defaultDirectory + "processed_" + imageEntity.getImageName();
            ImageIO.write(image, "jpg", new File(processedImagePath));
            imageEntity.setProcessedImagePath(processedImagePath);
            imageEntity.setStatus("PROCESSED");
            imageRepository.save(imageEntity);
        } catch (IOException e) {
            log.error("Error processing resize", e);
            throw new ErrorProcessingImageException("Error processing resize");
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int percentage) {
        int newWidth = originalImage.getWidth() * percentage / 100;
        int newHeight = originalImage.getHeight() * percentage / 100;
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    public ImageResponse sendImageRequest(ImageRequest imageProcessRequest) {
        String filePath = defaultDirectory + imageProcessRequest.getFile().getOriginalFilename();
        final var user = userService.findByLogin(imageProcessRequest.getUserLogin());
        try {
            imageProcessRequest.getFile().transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("Error saving image", e);
            throw new ErrorSavingImageException("Error saving image");
        }

        ImageEntity image = ImageEntity.builder()
                .userId(user.getId())
                .requestDateTime(LocalDateTime.now())
                .imageName(imageProcessRequest.getFile().getOriginalFilename())
                .resizePercentage(imageProcessRequest.getResizePercentage())
                .grayscaleFilter(imageProcessRequest.getGrayscaleFilter())
                .originalImagePath(filePath)
                .status("PENDING")
                .build();
        ImageEntity imageSaved = imageRepository.save(image);
        ImageMessage imageMessage = ImageMessage.builder()
                .imageId(imageSaved.getImageId())
                .build();
        imageProducer.enqueue(imageMessage);
        emailService.sendEmail(user.getEmail(), "Image processing started", "Your image is being processed");
        return ImageResponse.builder()
                .status("Image process successfully sent")
                .build();
    }
}
