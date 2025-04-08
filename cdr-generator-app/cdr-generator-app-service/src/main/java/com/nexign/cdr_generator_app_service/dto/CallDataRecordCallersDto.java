package com.nexign.cdr_generator_app_service.dto;

import com.nexign.cdr_generator_app_service.entity.Caller;
import lombok.Builder;
import lombok.Data;

/**
 * Информация о входящем и исходящем абоненте
 */
@Data
@Builder
public class CallDataRecordCallersDto {

    /**
     * Входящий абонент
     */
    private Caller incomingCaller;

    /**
     * Исходящий абонент
     */
    private Caller outcomingCaller;

}
