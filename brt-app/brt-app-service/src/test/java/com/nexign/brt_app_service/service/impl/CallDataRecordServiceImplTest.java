package com.nexign.brt_app_service.service.impl;

import com.nexign.brt_app_service.entity.CallDataRecord;
import com.nexign.brt_app_service.entity.Caller;
import com.nexign.brt_app_service.mapper.CallDataRecordMapper;
import com.nexign.brt_app_service.repository.CallDataRecordRepository;
import com.nexign.brt_app_service.service.CallerService;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallDataRecordServiceImplTest {

    @Captor
    ArgumentCaptor<List<CallDataRecord>> captor;

    @Mock
    CallerService callerService;

    @Mock
    CallDataRecordRepository callDataRecordRepository;

    @Mock
    CallDataRecordMapper mapper;

    @InjectMocks
    CallDataRecordServiceImpl callDataRecordService;


    @Test
    void processMessage() {
        // given
        var startTimeFirst = LocalDateTime.of(2025, 4, 24, 10, 40, 1);
        var finishTimeFirst = LocalDateTime.of(2025, 4, 24, 10, 42, 16);
        var cdrFirst = getCallCallDataRecordDto(startTimeFirst, finishTimeFirst,
                "79871111111", "79871111112");

        var startTimeSecond = LocalDateTime.of(2025, 4, 24, 12, 42, 1);
        var finishTimeSecond = LocalDateTime.of(2025, 4, 24, 12, 46, 31);
        var cdrSecond = getCallCallDataRecordDto(startTimeSecond, finishTimeSecond,
                "79871111112", "79871111113");

        var cdrList = List.of(cdrFirst, cdrSecond);

        var caller1 = Caller.builder().msisdn("79871111111").build();
        var caller2 = Caller.builder().msisdn("79871111112").build();
        var caller3 = Caller.builder().msisdn("79871111113").build();

        Map<String, Caller> msisdnCallerMap = Map.of(
                "79871111111", caller1,
                "79871111112", caller2,
                "79871111113", caller3
        );
        when(callerService.createOrFindCallers(any())).thenReturn(msisdnCallerMap);

        when(mapper.map(any(CallDataRecordDto.class), anyMap()))
                .thenAnswer(invocation -> {
                    CallDataRecordDto dto = invocation.getArgument(0);
                    Map<String, Caller> map = invocation.getArgument(1);
                    return CallDataRecord.builder()
                            .incomingCaller(map.get(dto.getIncomingCallerMsisdn()))
                            .outcomingCaller(map.get(dto.getOutcomingCallerMsisdn()))
                            .startCallTime(dto.getStartCallTime())
                            .finishCallTime(dto.getFinishCallTime())
                            .build();
                });

        // when
        callDataRecordService.processMessage(cdrList);

        // then
        verify(callDataRecordRepository).saveAllAndFlush(captor.capture());

        List<CallDataRecord> savedCDRs = captor.getValue();
        assertEquals(2, savedCDRs.size());

        CallDataRecord savedFirst = savedCDRs.get(0);
        assertAll(() -> {
            assertEquals(caller1, savedFirst.getIncomingCaller());
            assertEquals(caller2, savedFirst.getOutcomingCaller());
            assertEquals(startTimeFirst, savedFirst.getStartCallTime());
            assertEquals(finishTimeFirst, savedFirst.getFinishCallTime());
        });

        CallDataRecord savedSecond = savedCDRs.get(1);
        assertAll(() -> {
            assertEquals(caller2, savedSecond.getIncomingCaller());
            assertEquals(caller3, savedSecond.getOutcomingCaller());
            assertEquals(startTimeSecond, savedSecond.getStartCallTime());
            assertEquals(finishTimeSecond, savedSecond.getFinishCallTime());
        });

        verify(callerService).createOrFindCallers(Set.of("79871111111", "79871111112", "79871111113"));
        verify(callDataRecordRepository).saveAllAndFlush(any());

    }

    private CallDataRecordDto getCallCallDataRecordDto(LocalDateTime startTime, LocalDateTime finishTime,
                                                       String incomingCallerMsisdn, String outcomingCallerMsisdn) {
        return CallDataRecordDto.builder()
                .startCallTime(startTime)
                .finishCallTime(finishTime)
                .incomingCallerMsisdn(incomingCallerMsisdn)
                .outcomingCallerMsisdn(outcomingCallerMsisdn)
                .build();
    }

}