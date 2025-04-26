package com.nexign.brt_app_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    @Value("${rabbitmq.queue-name}")
    private String queueName;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

}