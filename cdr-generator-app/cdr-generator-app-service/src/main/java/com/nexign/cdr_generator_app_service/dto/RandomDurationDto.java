package com.nexign.cdr_generator_app_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

/**
 * Случайная длительность часов и минут
 */
@Data
@Builder
public class RandomDurationDto {

    /**
     * Случайная длительность часов (от 1 до 24 часов) в секундах
     */
    private Duration durationHours;

    /**
     * Случайная длительность минут (от 1 до 240 минут) в секундах
     */
    private Duration durationMinutes;

}
