package com.nexign.cdr_generator_app_service.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Random;

@UtilityClass
public class GeneratorUtil {

    private static final Integer minRandomCdrCount = 300;
    private static final Integer maxRandomCdrCount = 1500;
    private static final Random random = new Random();

    /**
     * Метод, генерирующий случайное число, которое соответствует количеству созданных записей в таблицу call_data
     *
     * @return Количество создаваемых записей в таблицу call_data
     */
    public static int generateRandomNumberCallRecords() {
        return random.nextInt(maxRandomCdrCount - minRandomCdrCount + 1) + minRandomCdrCount;
    }

    /**
     * Метод, генерирующий случайный индекс абонента из списка абонентов в таблице callers
     *
     * @return Индекс абонента из списка абонентов в таблице callers
     */
    public static int generateRandomIndexOfArray(int size) {
        return random.nextInt(size);
    }

    /**
     * Метод, генерирующий случайное число типа long в промежутке (min, max)
     *
     * @return Количество создаваемых записей в таблицу call_data
     */
    public static long generateRandomLong(long min, long max) {
        return random.nextLong(min, max);
    }

    /**
     * Метод, генерирующий случайное значение длительности (Duration)
     *
     * @return Значение длительности (Duration)
     */
    public static Duration generateRandomDuration(Duration start, Duration end) {
        return Duration.ofSeconds(generateRandomLong(start.getSeconds(), end.getSeconds()));
    }

}
