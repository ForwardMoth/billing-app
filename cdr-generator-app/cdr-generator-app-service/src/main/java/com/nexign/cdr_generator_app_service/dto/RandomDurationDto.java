package com.nexign.cdr_generator_app_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class RandomDurationDto {

    Duration durationHours;

    Duration durationMinutes;

}
