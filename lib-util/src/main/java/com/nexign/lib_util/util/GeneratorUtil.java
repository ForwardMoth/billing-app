package com.nexign.lib_util.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Random;

@UtilityClass
public class GeneratorUtil {

    private static final Integer MIN_RANDOM_CDR_COUNT = 10;
    private static final Integer MAX_RANDOM_CDR_COUNT = 100;
    private final Random random = new Random();

    /**
     * Метод, генерирующий случайное число, которое соответствует количеству созданных записей в таблицу call_data
     *
     * @return Количество создаваемых записей в таблицу call_data
     */
    public static int generateRandomNumberCallRecords() {
        return random.nextInt(MAX_RANDOM_CDR_COUNT - MIN_RANDOM_CDR_COUNT + 1) + MIN_RANDOM_CDR_COUNT;
    }

    /**
     * Метод, генерирующий случайный индекс из списка
     *
     * @return Индекс из списка
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
