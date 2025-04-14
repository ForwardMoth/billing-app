package com.nexign.cdr_generator_app_service.service.impl;

import com.nexign.cdr_generator_app_service.mapper.CallDataRecordMapper;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import com.nexign.cdr_generator_app_service.service.SenderService;
import com.nexign.cdr_generator_app_service.service.notifyBRT.NotifyBRTMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private static final Integer CDR_FILE_SIZE = 10;

    private final CallDataRecordRepository callDataRecordRepository;
    private final NotifyBRTMessageService notifyBRTMessageService;
    private final CallDataRecordMapper callDataRecordMapper;

    @Override
    @Transactional
    public void send() {
        var callDataRecordList = callDataRecordRepository.findFirst10ByIsSentFalseOrderByStartCallTime();
        while (CDR_FILE_SIZE.equals(callDataRecordList.size())) {
            notifyBRTMessageService.sendMessage(callDataRecordMapper.map(callDataRecordList));
            callDataRecordList.forEach(callDataRecord -> callDataRecord.setSent(true));
            callDataRecordRepository.saveAllAndFlush(callDataRecordList);
            callDataRecordList = callDataRecordRepository.findFirst10ByIsSentFalseOrderByStartCallTime();
        }
    }

}
