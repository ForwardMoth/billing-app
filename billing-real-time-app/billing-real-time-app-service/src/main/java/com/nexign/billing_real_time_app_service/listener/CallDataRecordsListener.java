package com.nexign.billing_real_time_app_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallDataRecordsListener {

    private final Queue rabbitQueue;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "#{rabbitQueue.getName()}")
    public void processMessage(String message) {
        try {
            var callDataRecordList = objectMapper.readValue(message, new TypeReference<List<CallDataRecordDto>>() {
            });
            // todo: Сохранение записей в БД (временно выводится в лог)
            log.info(callDataRecordList.toString());
        } catch (JsonProcessingException e) {
            log.error("Ошибка обработки CDR записей: ", e);
        }
        log.info("Получено сообщение от МС cdr-generator-app");
    }

}
