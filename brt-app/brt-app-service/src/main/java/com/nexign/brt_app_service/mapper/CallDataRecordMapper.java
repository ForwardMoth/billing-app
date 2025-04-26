package com.nexign.brt_app_service.mapper;

import com.nexign.brt_app_service.entity.CallDataRecord;
import com.nexign.brt_app_service.entity.Caller;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import com.nexign.lib_util.service.UuidService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", imports = UuidService.class)
public interface CallDataRecordMapper {

    @Mapping(target = "uuid", expression = "java(UuidService.generateRandomUuid())")
    @Mapping(target = "incomingCaller", expression = "java(msisdnCallerMap.get(cdr.getIncomingCallerMsisdn()))")
    @Mapping(target = "outcomingCaller", expression = "java(msisdnCallerMap.get(cdr.getOutcomingCallerMsisdn()))")
    CallDataRecord map(CallDataRecordDto cdr, Map<String, Caller> msisdnCallerMap);

}
