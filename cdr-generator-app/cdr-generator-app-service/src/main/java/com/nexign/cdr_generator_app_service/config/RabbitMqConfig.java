package com.nexign.cdr_generator_app_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final RabbitMqProperty rabbitMqProperty;

    @Bean
    Queue queue() {
        return new Queue(rabbitMqProperty.getQueueName(), false);
    }

}