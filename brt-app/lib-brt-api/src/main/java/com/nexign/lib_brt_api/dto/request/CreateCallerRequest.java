package com.nexign.lib_brt_api.dto.request;

import com.nexign.lib_brt_api.dto.PersonNameDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCallerRequest {

    private String msisdn;

    private PersonNameDto personName;

    private Integer minutes;

}
