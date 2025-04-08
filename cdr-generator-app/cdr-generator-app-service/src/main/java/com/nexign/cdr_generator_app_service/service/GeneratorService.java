package com.nexign.cdr_generator_app_service.service;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import com.nexign.cdr_generator_app_service.service.helper.CallerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nexign.cdr_generator_app_service.util.GeneratorUtil.generateRandomNumberCallRecords;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeneratorService implements CommandLineRunner {

    private static final Integer NUMBER_OF_CALL_RECORDS = generateRandomNumberCallRecords();

    private final CallerService callerService;
    private final CallDataRecordRepository callDataRepository;
    private final CallerHelper callerHelper;
    private final SenderService senderService;

    @Override
    public void run(final String... args) {
        log.info("Начало генерации записей звонков...");
        final var callers = callerService.getCallers();
        LocalDateTime startGenerationDateTime = LocalDateTime.now();
        LocalDateTime endGenerationDateTime = startGenerationDateTime.plusYears(1);
        LocalDateTime currentDateTime = startGenerationDateTime;
        List<CallDataRecord> callDataRecords = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CALL_RECORDS; i++) {
            if (currentDateTime.isAfter(endGenerationDateTime)) {
                break;
            }
            var callDataRecordList = callerHelper.generateCallDataRecords(callers, currentDateTime);
            callDataRecords.addAll(callDataRecordList);
            currentDateTime = callDataRecordList.get(callDataRecordList.size() - 1).getFinishCallTime();
        }
        callDataRepository.saveAll(callDataRecords);
        log.info("Сгенерировано {} записей звонков", callDataRecords.size());
        senderService.sendCallDataRecordList();
    }

}
