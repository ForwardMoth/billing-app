package com.nexign.cdr_generator_app_service.util;

import com.nexign.cdr_generator_app_service.dto.CallerInfoDto;
import com.nexign.cdr_generator_app_service.dto.RandomDurationDto;
import com.nexign.cdr_generator_app_service.entity.Caller;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.List;

import static com.nexign.cdr_generator_app_service.util.GeneratorUtil.generateRandomDuration;
import static com.nexign.cdr_generator_app_service.util.GeneratorUtil.generateRandomIndexOfArray;

@UtilityClass
public class CallerHelper {

    private static final Duration MIN_DURATION_HOURS = Duration.ofHours(1);
    private static final Duration MAX_DURATION_HOURS = Duration.ofHours(24);

    private static final Duration MIN_DURATION_MINUTES = Duration.ofMinutes(1);
    private static final Duration MAX_DURATION_MINUTES = Duration.ofMinutes(240);

    public static CallerInfoDto generateCallerInfo(List<Caller> callers) {
        final var callersCount = callers.size();
        Caller incomingCaller = callers.get(generateRandomIndexOfArray(callersCount));
        Caller outcomingCaller = callers.get(generateRandomIndexOfArray(callersCount));
        while (incomingCaller.equals(outcomingCaller)) {
            outcomingCaller = callers.get(generateRandomIndexOfArray(callersCount));
        }
        return CallerInfoDto.builder()
                .incomingCaller(incomingCaller)
                .outcomingCaller(outcomingCaller)
                .build();
    }

    public static RandomDurationDto generateDurationDto() {
        return RandomDurationDto.builder()
                .durationHours(generateRandomDuration(MIN_DURATION_HOURS, MAX_DURATION_HOURS))
                .durationMinutes(generateRandomDuration(MIN_DURATION_MINUTES, MAX_DURATION_MINUTES))
                .build();
    }

}
