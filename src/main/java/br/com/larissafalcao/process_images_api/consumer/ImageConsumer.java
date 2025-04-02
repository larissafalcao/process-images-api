package br.com.larissafalcao.process_images_api.consumer;

import br.com.larissafalcao.process_images_api.controller.dto.request.ImageMessage;
import br.com.larissafalcao.process_images_api.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static br.com.larissafalcao.process_images_api.config.ImagesProcessingConfiguration.QUEUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageConsumer {
    private final ImageService imageService;

    @RabbitListener(
            queues = QUEUE,
            concurrency = "${rabbitmq.queue.images.return.concurrency}"
    )
    public void consume(ImageMessage request) {
        log.info("Image processing request received: {}", request);
        imageService.processImage(request);
    }
}
