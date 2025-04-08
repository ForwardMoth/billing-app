package com.nexign.cdr_generator_app_service.mapper;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CallDataRecordMapper {

    List<CallDataRecordDto> map(List<CallDataRecord> callDataRecord);

}
