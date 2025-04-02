package br.com.larissafalcao.process_images_api.service;

import br.com.larissafalcao.process_images_api.controller.dto.request.ImageMessage;
import br.com.larissafalcao.process_images_api.exception.ErrorSavingImageException;
import br.com.larissafalcao.process_images_api.persistence.domain.UserEntity;
import br.com.larissafalcao.process_images_api.persistence.repository.ImageRepository;
import br.com.larissafalcao.process_images_api.producer.ImageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageProducer imageProducer;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(imageService, "imageQuota", 10);
        ReflectionTestUtils.setField(imageService, "defaultDirectory", "/Users/larissafalcao/images-processing");
    }

    @Test
    void processImage_withNonExistentImage_shouldThrowException() {
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setImageId(1L);

        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> imageService.processImage(imageMessage));
    }

    @Test
    void sendImageRequest_withIOException_shouldThrowErrorSavingImageException() throws IOException {
        String userLogin = "userLogin";
        Integer resizePercentage = 50;
        Boolean grayscaleFilter = true;

        when(userService.findByLogin(userLogin)).thenReturn(new UserEntity());
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        doThrow(IOException.class).when(file).transferTo(any(File.class));

        assertThrows(ErrorSavingImageException.class, () -> imageService.sendImageRequest(userLogin, resizePercentage, grayscaleFilter, file));
    }


}
