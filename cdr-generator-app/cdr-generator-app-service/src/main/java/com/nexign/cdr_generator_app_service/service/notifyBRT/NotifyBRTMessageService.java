package com.nexign.cdr_generator_app_service.service.notifyBRT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyBRTMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final Binding binding;

    public void sendMessage(List<CallDataRecordDto> cdrDto) {
        try {
            String message = objectMapper.writeValueAsString(cdrDto);
            rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), message);
        } catch (Exception e) {
            log.error("Error of sending file: ", e);
        }
    }

}
