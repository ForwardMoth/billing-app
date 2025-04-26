package com.nexign.brt_app_service.mapper;

import com.nexign.brt_app_service.entity.PersonName;
import com.nexign.lib_brt_api.dto.PersonNameDto;
import com.nexign.lib_util.service.UuidService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = UuidService.class)
public interface PersonNameMapper {

    @Mapping(target = "uuid", expression = "java(UuidService.generateRandomUuid())")
    PersonName map(PersonNameDto personNameDto);

}
