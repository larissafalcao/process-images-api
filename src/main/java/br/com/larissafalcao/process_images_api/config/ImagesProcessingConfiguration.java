package br.com.larissafalcao.process_images_api.config;

import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ImagesProcessingConfiguration {
    public static final String QUEUE = "images-queue";
    public static final String QUEUE_DLQ = "images-queue-dlq";
    public static final String EXCHANGE = "images.exchange";


    @Bean
    @Qualifier("imagesExchange")
    public FanoutExchange imagesExchange() {
        return (FanoutExchange)ExchangeBuilder.fanoutExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    @Qualifier("imagesQueue")
    public Queue imagesQueue() {
        return QueueBuilder.durable(QUEUE).withArgument("x-dead-letter-exchange", "").withArgument("x-dead-letter-routing-key", QUEUE_DLQ).build();
    }

    @Bean
    public Binding bindingImagesImport(@Qualifier("imagesExchange") FanoutExchange imagesExchange, @Qualifier("imagesQueue") Queue imagesQueue) {
        return BindingBuilder.bind(imagesQueue).to(imagesExchange);
    }

    @Bean
    public Queue imagesQueueDLQ() {
        return new Queue(QUEUE_DLQ, true);
    }
}
