package com.nexign.lib_util.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidService {

    /**
     * Метод генерации внешнего идентификатора записи
     *
     * @return UUID
     */
    public static UUID generateRandomUuid() {
        return UUID.randomUUID();
    }
}
