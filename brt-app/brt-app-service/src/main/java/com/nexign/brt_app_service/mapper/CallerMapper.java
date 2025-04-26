package com.nexign.brt_app_service.mapper;

import com.nexign.brt_app_service.entity.Caller;
import com.nexign.brt_app_service.entity.Operator;
import com.nexign.lib_brt_api.dto.response.CallerResponse;
import com.nexign.lib_util.service.UuidService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = UuidService.class)
public interface CallerMapper {

    @Mapping(target = "callerId", source = "uuid")
    @Mapping(target = "operatorName", source = "operator.name")
    @Mapping(target = "resource.isForbidCalls", source = "resource.forbidCalls")
    CallerResponse map(Caller caller);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "actual", ignore = true)
    @Mapping(target = "uuid", expression = "java(UuidService.generateRandomUuid())")
    @Mapping(target = "operator", source = "operator")
    Caller map(Operator operator, String msisdn);

}
