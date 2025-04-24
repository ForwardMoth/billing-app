package com.nexign.cdr_generator_app_service.mapper;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CallDataRecordMapper {

    @Mapping(target = "incomingCallerMsisdn", source = "incomingCaller.msisdn")
    @Mapping(target = "outcomingCallerMsisdn", source = "outcomingCaller.msisdn")
    CallDataRecordDto map(CallDataRecord callDataRecord);

}
