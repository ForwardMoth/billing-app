package com.nexign.cdr_generator_app_service.util;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.entity.Caller;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.nexign.cdr_generator_app_service.util.GeneratorUtil.generateRandomDuration;
import static com.nexign.cdr_generator_app_service.util.GeneratorUtil.generateRandomIndexOfArray;

@Component
public class CallerHelper {

    private static final Duration MIN_DURATION_HOURS = Duration.ofHours(1);
    private static final Duration MAX_DURATION_HOURS = Duration.ofHours(4);

    private static final Duration MIN_DURATION_MINUTES = Duration.ofMinutes(1);
    private static final Duration MAX_DURATION_MINUTES = Duration.ofMinutes(240);

    /**
     * @param callers         - Список абонентов
     * @param currentDateTime - Текущее время и дата для отслеживания хронологического порядка
     * @return Записи для справочника CDR
     */
    public List<CallDataRecord> generateCallDataRecords(List<Caller> callers,
                                                        LocalDateTime currentDateTime) {
        final var callersInfo = generateCallerInfo(callers);
        final var randomDuration = generateDurationDto();

        final var startDateTime = currentDateTime.plus(randomDuration.durationHours);
        final var finishDateTime = startDateTime.plus(randomDuration.durationMinutes);

        if (startDateTime.toLocalDate().equals(finishDateTime.toLocalDate())) {
            return List.of(getCallDataRecord(callersInfo, new DurationDto(startDateTime, finishDateTime)));
        }

        final var endOfFirstDay = startDateTime.toLocalDate().atTime(23, 59, 59);
        final var firstDuration = new DurationDto(startDateTime, endOfFirstDay);

        final var startOfSecondDay = finishDateTime.toLocalDate().atStartOfDay();
        final var secondDuration = new DurationDto(startOfSecondDay, finishDateTime);

        return List.of(getCallDataRecord(callersInfo, firstDuration), getCallDataRecord(callersInfo, secondDuration));
    }

    /**
     * Метод выбора двух случайных абонентов звонка
     *
     * @param callers - абоненты
     * @return CallDataRecordCallersDto
     */
    private CallDataRecordCallersDto generateCallerInfo(List<Caller> callers) {
        final var callersCount = callers.size();
        Caller incomingCaller = callers.get(generateRandomIndexOfArray(callersCount));
        Caller outcomingCaller = callers.get(generateRandomIndexOfArray(callersCount));
        while (incomingCaller.equals(outcomingCaller)) {
            outcomingCaller = callers.get(generateRandomIndexOfArray(callersCount));
        }
        return new CallDataRecordCallersDto(incomingCaller, outcomingCaller);
    }

    /**
     * Метод генерации случайной длительности в часах и минутах
     *
     * @return RandomDurationDto
     */
    private RandomDurationDto generateDurationDto() {
        final var durationHours = generateRandomDuration(MIN_DURATION_HOURS, MAX_DURATION_HOURS);
        final var durationMinutes = generateRandomDuration(MIN_DURATION_MINUTES, MAX_DURATION_MINUTES);
        return new RandomDurationDto(durationHours, durationMinutes);
    }

    private CallDataRecord getCallDataRecord(CallDataRecordCallersDto callerInfoDto,
                                             DurationDto durationDto) {
        return CallDataRecord.builder()
                .incomingCaller(callerInfoDto.incomingCaller)
                .outcomingCaller(callerInfoDto.outcomingCaller)
                .startCallTime(durationDto.startDateTime)
                .finishCallTime(durationDto.finishDateTime)
                .build();
    }

    record CallDataRecordCallersDto(Caller incomingCaller, Caller outcomingCaller) {

    }

    record RandomDurationDto(Duration durationHours, Duration durationMinutes) {

    }

    public record DurationDto(LocalDateTime startDateTime, LocalDateTime finishDateTime) {

    }

}
