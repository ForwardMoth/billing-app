package com.nexign.lib_brt_api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PersonNameDto implements Serializable {

    private String surname;

    private String name;

    private String patronymic;

}
