package com.nexign.brt_app_service.mapper;

import com.nexign.brt_app_service.entity.Caller;
import com.nexign.brt_app_service.entity.Resource;
import com.nexign.lib_util.service.UuidService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = {UuidService.class, BigDecimal.class})
public interface ResourceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "actual", ignore = true)
    @Mapping(target = "uuid", expression = "java(UuidService.generateRandomUuid())")
    @Mapping(target = "caller", source = "caller")
    @Mapping(target = "balance", expression = "java(new BigDecimal(100))")
    Resource create(Caller caller, Integer minutes);

}
