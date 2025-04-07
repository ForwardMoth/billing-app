package com.nexign.cdr_generator_app_service.service;

import com.nexign.cdr_generator_app_service.dto.CallerInfoDto;
import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.entity.Caller;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nexign.cdr_generator_app_service.util.CallerHelper.generateCallerInfo;
import static com.nexign.cdr_generator_app_service.util.CallerHelper.generateDurationDto;
import static com.nexign.cdr_generator_app_service.util.GeneratorUtil.generateRandomNumberCallRecords;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeneratorService implements CommandLineRunner {

    private static final Integer NUMBER_OF_CALL_RECORDS = generateRandomNumberCallRecords();

    private final CallerService callerService;
    private final CallDataRecordRepository callDataRepository;

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
            var callDataRecordList = generateCallDataRecords(callers, currentDateTime);
            callDataRecords.addAll(callDataRecordList);
            currentDateTime = callDataRecordList.get(callDataRecordList.size() - 1).getFinishCallTime();
        }
        callDataRepository.saveAll(callDataRecords);
        log.info("Сгенерировано {} записей звонков", callDataRecords.size());
    }

    /**
     * @param callers         - Список абонентов
     * @param currentDateTime - Текущее время и дата для отслеживания хронологического порядка
     * @return Записи для справочника CDR
     */
    private List<CallDataRecord> generateCallDataRecords(List<Caller> callers,
                                                         LocalDateTime currentDateTime) {
        final var callersInfo = generateCallerInfo(callers);
        final var randomDuration = generateDurationDto();

        final var startDateTime = currentDateTime.plus(randomDuration.getDurationHours());
        final var finishDateTime = startDateTime.plus(randomDuration.getDurationMinutes());

        if (startDateTime.toLocalDate().equals(finishDateTime.toLocalDate())) {
            return List.of(getCallDataRecord(callersInfo, new DurationDto(startDateTime, finishDateTime)));
        }

        final var endOfFirstDay = startDateTime.toLocalDate().atTime(23, 59, 59);
        final var firstDuration = new DurationDto(startDateTime, endOfFirstDay);

        final var startOfSecondDay = finishDateTime.toLocalDate().atStartOfDay();
        final var secondDuration = new DurationDto(startOfSecondDay, finishDateTime);

        return List.of(getCallDataRecord(callersInfo, firstDuration), getCallDataRecord(callersInfo, secondDuration));
    }

    private CallDataRecord getCallDataRecord(CallerInfoDto callerInfoDto,
                                             DurationDto durationDto) {
        return CallDataRecord.builder()
                .incomingCaller(callerInfoDto.getIncomingCaller())
                .outcomingCaller(callerInfoDto.getOutcomingCaller())
                .startCallTime(durationDto.startDateTime)
                .finishCallTime(durationDto.finishDateTime)
                .build();
    }


    /**
     * Вспомогательный объект для учёта даты и времени начала и окончания звонка
     *
     * @param startDateTime  - дата начала звонка
     * @param finishDateTime - дата окончания звонка
     */
    private record DurationDto(LocalDateTime startDateTime, LocalDateTime finishDateTime) {
    }

}
