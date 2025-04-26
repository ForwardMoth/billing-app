package com.nexign.brt_app_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.brt_app_service.service.CallDataRecordService;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallDataRecordsListener {

    private final ObjectMapper objectMapper;
    private final CallDataRecordService callDataRecordService;

    @RabbitListener(queues = "#{queue.getName()}")
    public void processCDRMessage(String message) {
        try {
            var callDataRecordList = objectMapper.readValue(message, new TypeReference<List<CallDataRecordDto>>() {
            });
            callDataRecordService.processMessage(callDataRecordList);
            log.info("Сообщение от cdr-generator обработано");
        } catch (JsonProcessingException e) {
            log.error("Ошибка обработки CDR записей: ", e);
        }
    }

}
