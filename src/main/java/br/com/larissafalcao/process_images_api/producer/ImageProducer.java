package br.com.larissafalcao.process_images_api.producer;

import br.com.larissafalcao.process_images_api.controller.dto.request.ImageMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static br.com.larissafalcao.process_images_api.config.ImagesProcessingConfiguration.EXCHANGE;

@Service
@AllArgsConstructor
@Slf4j
public class ImageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void enqueue(ImageMessage request) {
        log.info("Image processing request received: {}", request);
        rabbitTemplate.convertAndSend(EXCHANGE, "", request);
    }

}
