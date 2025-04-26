package com.nexign.brt_app_service.service;

import com.nexign.lib_call_data_record.dto.CallDataRecordDto;

import java.util.List;

public interface CallDataRecordService {

    void processMessage(List<CallDataRecordDto> callDataRecordDtoList);

}
