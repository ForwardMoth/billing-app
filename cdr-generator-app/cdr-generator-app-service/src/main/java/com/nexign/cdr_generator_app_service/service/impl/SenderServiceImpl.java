package com.nexign.cdr_generator_app_service.service.impl;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.mapper.CallDataRecordMapper;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import com.nexign.cdr_generator_app_service.service.CallDataRecordSenderService;
import com.nexign.cdr_generator_app_service.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final CallDataRecordRepository callDataRecordRepository;
    private final CallDataRecordSenderService callDataRecordSenderService;
    private final CallDataRecordMapper callDataRecordMapper;

    @Override
    @Transactional
    public void sendCallDataRecordList() {
        log.info("Начало отправки сгенерированных записей...");
        List<CallDataRecord> callDataRecordList = callDataRecordRepository.findFirst10ByIsSentFalseOrderByCreated();
        while (!callDataRecordList.isEmpty()) {
            callDataRecordSenderService.sendMessage(callDataRecordMapper.map(callDataRecordList));
            callDataRecordList.forEach(callDataRecord -> callDataRecord.setSent(true));
            callDataRecordRepository.saveAllAndFlush(callDataRecordList);
            callDataRecordList = callDataRecordRepository.findFirst10ByIsSentFalseOrderByCreated();
        }
        log.info("Сгенерированные записи отправлены в BRT...");
    }

}
