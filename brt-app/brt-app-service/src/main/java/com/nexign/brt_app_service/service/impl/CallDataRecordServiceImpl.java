package com.nexign.brt_app_service.service.impl;

import com.nexign.brt_app_service.entity.CallDataRecord;
import com.nexign.brt_app_service.entity.Caller;
import com.nexign.brt_app_service.mapper.CallDataRecordMapper;
import com.nexign.brt_app_service.repository.CallDataRecordRepository;
import com.nexign.brt_app_service.service.CallDataRecordService;
import com.nexign.brt_app_service.service.CallerService;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CallDataRecordServiceImpl implements CallDataRecordService {

    private final CallerService callerService;
    private final CallDataRecordRepository callDataRecordRepository;
    private final CallDataRecordMapper mapper;

    @Override
    @Transactional
    public void processMessage(List<CallDataRecordDto> callDataRecordDtoList) {
        Set<String> msisdnSet = callDataRecordDtoList.stream()
                .flatMap(cdr -> Stream.of(cdr.getIncomingCallerMsisdn(), cdr.getOutcomingCallerMsisdn()))
                .collect(Collectors.toSet());
        Map<String, Caller> msisdnCallerMap = callerService.createOrFindCallers(msisdnSet);
        List<CallDataRecord> callDataRecordList = callDataRecordDtoList.stream()
                .map(cdr -> mapper.map(cdr, msisdnCallerMap))
                .toList();
        callDataRecordRepository.saveAllAndFlush(callDataRecordList);
        // todo: Учесть расходы, как появится HRS
    }

}
