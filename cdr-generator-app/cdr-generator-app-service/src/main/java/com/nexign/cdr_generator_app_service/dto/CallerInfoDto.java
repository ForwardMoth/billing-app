package com.nexign.cdr_generator_app_service.dto;

import com.nexign.cdr_generator_app_service.entity.Caller;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallerInfoDto {

    private Caller incomingCaller;

    private Caller outcomingCaller;
    
}
