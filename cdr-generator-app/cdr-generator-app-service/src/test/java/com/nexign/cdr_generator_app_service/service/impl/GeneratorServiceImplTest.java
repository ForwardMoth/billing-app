package com.nexign.cdr_generator_app_service.service.impl;

import com.nexign.cdr_generator_app_service.entity.CallDataRecord;
import com.nexign.cdr_generator_app_service.entity.Caller;
import com.nexign.cdr_generator_app_service.repository.CallDataRecordRepository;
import com.nexign.cdr_generator_app_service.service.CallerService;
import com.nexign.cdr_generator_app_service.util.CallerHelper;
import com.nexign.cdr_generator_app_service.util.GeneratorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GeneratorServiceImplTest {

    private CallerService callerService;
    private CallDataRecordRepository callDataRepository;
    private CallerHelper callerHelper;
    private GeneratorServiceImpl generatorService;

    @BeforeEach
    void setUp() {
        callerService = mock(CallerService.class);
        callDataRepository = mock(CallDataRecordRepository.class);
        callerHelper = mock(CallerHelper.class);
        generatorService = new GeneratorServiceImpl(callerService, callDataRepository, callerHelper);
    }

    @Test
    void generate_shouldCreateAndSaveCallDataRecords() {
        try (MockedStatic<GeneratorUtil> mocked = mockStatic(GeneratorUtil.class)) {
            // mock random number call records
            mocked.when(GeneratorUtil::generateRandomNumberCallRecords).thenReturn(1);

            final var startDateTime = LocalDateTime.of(2025, 4, 14, 15, 20, 15);

            // mock last CDR for get start datetime
            when(callDataRepository.findLastCallDataRecordTime()).thenReturn(Optional.of(startDateTime));

            // given callers
            final var callers = getCallers();
            // mock getting callers
            when(callerService.getCallers()).thenReturn(callers);

            // 2025-04-14T17:20:15
            LocalDateTime startTime = startDateTime.plusHours(2);
            // 2025-04-14T17:30:15
            LocalDateTime endTime = startTime.plusMinutes(10);

            CallDataRecord cdr = CallDataRecord.builder()
                    .incomingCaller(callers.get(0))
                    .outcomingCaller(callers.get(1))
                    .startCallTime(startTime)
                    .finishCallTime(endTime)
                    .isSent(false)
                    .build();

            // mock generate CDR
            when(callerHelper.generateCallDataRecords(anyList(), any())).thenReturn(List.of(cdr));

            // when (check generator)
            generatorService.generate();

            // then (out result)
            ArgumentCaptor<List<CallDataRecord>> captor = ArgumentCaptor.forClass(List.class);
            verify(callDataRepository, atLeastOnce()).saveAllAndFlush(captor.capture());

            // check CDR
            List<CallDataRecord> savedRecords = captor.getValue();
            assertThat(savedRecords).isNotEmpty();
            assertThat(savedRecords.get(0).getIncomingCaller().getMsisdn()).isEqualTo("79001112233");
            assertThat(savedRecords.get(0).getOutcomingCaller().getMsisdn()).isEqualTo("79002223344");
            assertThat(savedRecords.get(0).getStartCallTime()).isEqualTo("2025-04-14T17:20:15");
            assertThat(savedRecords.get(0).getFinishCallTime()).isEqualTo("2025-04-14T17:30:15");
        }
    }


    private List<Caller> getCallers() {
        Caller firstCaller = new Caller();
        firstCaller.setId(1L);
        firstCaller.setMsisdn("79001112233");

        Caller secondCaller = new Caller();
        secondCaller.setId(2L);
        secondCaller.setMsisdn("79002223344");

        return List.of(firstCaller, secondCaller);
    }

}
