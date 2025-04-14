package com.nexign.cdr_generator_app_service.service.impl;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.mapper.CallDataRecordMapper;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import com.nexign.cdr_generator_app_service.service.notifyBRT.NotifyBRTMessageService;
import com.nexign.lib_call_data_record.dto.CallDataRecordDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SenderServiceImplTest {

    @Mock
    private CallDataRecordRepository callDataRecordRepository;

    @Mock
    private NotifyBRTMessageService notifyBRTMessageService;

    @Mock
    private CallDataRecordMapper callDataRecordMapper;

    @InjectMocks
    private SenderServiceImpl senderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void send_ShouldSendAndMarkCDRs_WhenListSizeIsExactly10() {
        // given
        final var callDataRecords = generateMockCDR();

        // mock mapped dto
        List<CallDataRecordDto> callDataRecordDtoList = List.of(mock(CallDataRecordDto.class));

        // mock behavior (first times get 10 CDRs, second times - 0 CDRs)
        when(callDataRecordRepository.findFirst10ByIsSentFalseOrderByStartCallTime())
                .thenReturn(callDataRecords)
                .thenReturn(new ArrayList<>());

        // mock mapping
        when(callDataRecordMapper.map(callDataRecords)).thenReturn(callDataRecordDtoList);

        // when (check send method)
        senderService.send();

        // then verify methods
        verify(notifyBRTMessageService, times(1)).sendMessage(callDataRecordDtoList);
        verify(callDataRecordRepository, times(2)).findFirst10ByIsSentFalseOrderByStartCallTime();
        verify(callDataRecordRepository, times(1)).saveAllAndFlush(callDataRecords);

        // Check that isSent = true
        callDataRecords.forEach(cdr -> assertThat(cdr.isSent()).isTrue());
    }

    private List<CallDataRecord> generateMockCDR() {
        List<CallDataRecord> callDataRecordList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            callDataRecordList.add(
                    CallDataRecord.builder()
                            .id((long) i)
                            .startCallTime(LocalDateTime.now())
                            .finishCallTime(LocalDateTime.now().plusMinutes(1))
                            .incomingCaller(null)
                            .outcomingCaller(null)
                            .isSent(false)
                            .build()
            );
        }
        return callDataRecordList;
    }

}
