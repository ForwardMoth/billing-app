package com.nexign.cdr_generator_app_service.service.impl;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import com.nexign.cdr_generator_app_service.service.CallerService;
import com.nexign.cdr_generator_app_service.service.GeneratorService;
import com.nexign.cdr_generator_app_service.util.CallerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nexign.lib_util.util.GeneratorUtil.generateRandomNumberCallRecords;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final CallerService callerService;
    private final CallDataRecordRepository callDataRepository;
    private final CallerHelper callerHelper;

    @Override
    @Transactional
    public void generate() {
        final var numberOfCallRecords = generateRandomNumberCallRecords();
        LocalDateTime currentGenerationDateTime = callDataRepository.findLastCallDataRecordTime()
                .orElse(LocalDateTime.now().minusYears(1));
        List<CallDataRecord> callDataRecordList = new ArrayList<>();
        for (int i = 0; i < numberOfCallRecords; i++) {
            // todo: Учесть абонентов, у которых отрицательный баланс (генерация звонков запрещена)
            final var callers = callerService.getCallers();
            var callDataRecords = callerHelper.generateCallDataRecords(callers, currentGenerationDateTime);
            callDataRecordList.addAll(callDataRecords);
            currentGenerationDateTime = callDataRecords.get(callDataRecords.size() - 1).getFinishCallTime();
        }
        callDataRepository.saveAllAndFlush(callDataRecordList);
        log.info("Сгенерировано {} записей звонков", numberOfCallRecords);
    }

}
