package com.nexign.lib_call_data_record.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Информация о действиях абонентов (CDR)
 */
@Data
@Builder
public class CallDataRecordDto implements Serializable {

    /**
     * Номер телефона входящего абонента, обязательный
     */
    private String incomingCallerMsisdn;

    /**
     * Номер телефона исходящего абонента, обязательный
     */
    private String outcomingCallerMsisdn;

    /**
     * Дата и время начала звонка, обязательный
     */
    private LocalDateTime startCallTime;

    /**
     * Дата и время окончания звонка, обязательный
     */
    private LocalDateTime finishCallTime;

}
