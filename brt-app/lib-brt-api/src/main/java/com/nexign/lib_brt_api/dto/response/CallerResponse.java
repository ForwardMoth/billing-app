package com.nexign.lib_brt_api.dto.response;

import com.nexign.lib_brt_api.dto.PersonNameDto;
import com.nexign.lib_brt_api.dto.ResourceDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class CallerResponse implements Serializable {

    private UUID callerId;

    private String msisdn;

    private String operatorName;

    private PersonNameDto personName;

    private ResourceDto resource;

    private Boolean actual;

}
