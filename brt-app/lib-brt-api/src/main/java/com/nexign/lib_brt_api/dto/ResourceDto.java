package com.nexign.lib_brt_api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResourceDto implements Serializable {

    private String balance;

    private Boolean isForbidCalls;

    private Integer minutes;

}
