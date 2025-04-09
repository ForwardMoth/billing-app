package com.nexign.lib_call_data_record.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Информация об абоненте
 */
@Data
@Builder
public class CallerDto implements Serializable {

    /**
     * Номер телефона абонента, обязательный
     */
    private String msisdn;

}
