package com.nexign.cdr_generator_app_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.cdr_generator_app_service.config.RabbitMqProperty;
import com.nexign.cdr_generator_app_service.service.CallDataRecordSenderService;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallDataRecordSenderServiceImpl implements CallDataRecordSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperty rabbitMqProperty;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(List<CallDataRecordDto> cdrDto) {
        try {
            String message = objectMapper.writeValueAsString(cdrDto);
            rabbitTemplate.convertAndSend(rabbitMqProperty.getQueueName(), message);
            log.info("Записи успешно отправлены из МС cdr-generator-app в МС brt-app");
        } catch (Exception e) {
            log.error("Ошибка отправки в BRT: ", e);
        }
    }

}
