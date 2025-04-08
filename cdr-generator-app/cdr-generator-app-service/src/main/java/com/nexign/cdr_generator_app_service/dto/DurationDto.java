package com.nexign.cdr_generator_app_service.dto;

import java.time.LocalDateTime;

/**
 * Информация о дате и времени начала и окончания звонка
 *
 * @param startDateTime  - дата начала звонка
 * @param finishDateTime - дата окончания звонка
 */
public record DurationDto(LocalDateTime startDateTime, LocalDateTime finishDateTime) {
}