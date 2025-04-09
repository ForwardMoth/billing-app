package com.nexign.cdr_generator_app_service.service;

import com.nexign.lib_call_data_record.dto.CallDataRecordDto;

import java.util.List;

public interface CallDataRecordSenderService {

    void sendMessage(List<CallDataRecordDto> cdrDto);

}
