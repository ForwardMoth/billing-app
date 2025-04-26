package com.nexign.brt_app_service.service.impl;

import com.nexign.brt_app_service.entity.Caller;
import com.nexign.brt_app_service.entity.Operator;
import com.nexign.brt_app_service.mapper.CallerMapper;
import com.nexign.brt_app_service.repository.CallerRepository;
import com.nexign.brt_app_service.repository.OperatorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallerServiceImplTest {

    @Mock
    CallerRepository callerRepository;

    @Mock
    OperatorRepository operatorRepository;

    @Mock
    CallerMapper mapper;

    @InjectMocks
    CallerServiceImpl callerService;

    @Test
    void shouldReturnCreatedAndFoundCallersTest() {
        Set<String> msisdnSet = Set.of("79871111111", "79871111112", "79871111113", "79871111114", "79871111115");
        var foundCallers = getFoundCallers();
        when(callerRepository.findCallersByMsisdnIn(msisdnSet)).thenReturn(foundCallers);

        var operators = getOperators();
        when(operatorRepository.findByNameNotRomashka()).thenReturn(operators);

        var createdCallers = getCreatedCallers();
        when(callerRepository.saveAllAndFlush(any())).thenReturn(createdCallers);

        var msisdnCallerMap = callerService.createOrFindCallers(msisdnSet);

        assertThat(msisdnCallerMap).hasSize(msisdnSet.size());
        msisdnSet.forEach(msisdn -> assertThat(msisdnCallerMap.containsKey(msisdn)).isTrue());
    }

    @Test
    void shouldReturnOnlyFoundCallersTest() {
        Set<String> msisdnSet = Set.of("79871111111", "79871111112");
        var foundCallers = getFoundCallers();
        when(callerRepository.findCallersByMsisdnIn(msisdnSet)).thenReturn(foundCallers);

        var msisdnCallerMap = callerService.createOrFindCallers(msisdnSet);
        assertThat(msisdnCallerMap).hasSize(msisdnSet.size());
        msisdnSet.forEach(msisdn -> assertThat(msisdnCallerMap.containsKey(msisdn)).isTrue());
    }

    @Test
    void shouldReturnOnlyCreatedCallersTest() {
        Set<String> msisdnSet = Set.of("79871111113", "79871111114", "79871111115");
        when(callerRepository.findCallersByMsisdnIn(msisdnSet)).thenReturn(List.of());

        var operators = getOperators();
        when(operatorRepository.findByNameNotRomashka()).thenReturn(operators);

        var createdCallers = getCreatedCallers();
        when(callerRepository.saveAllAndFlush(any())).thenReturn(createdCallers);

        var msisdnCallerMap = callerService.createOrFindCallers(msisdnSet);

        assertThat(msisdnCallerMap).hasSize(msisdnSet.size());
        msisdnSet.forEach(msisdn -> assertThat(msisdnCallerMap.containsKey(msisdn)).isTrue());
    }

    private List<Caller> getCreatedCallers() {
        var firstCaller = Caller.builder().msisdn("79871111113").build();
        var secondCaller = Caller.builder().msisdn("79871111114").build();
        var thirdCaller = Caller.builder().msisdn("79871111115").build();
        return List.of(firstCaller, secondCaller, thirdCaller);
    }

    private List<Caller> getFoundCallers() {
        var firstCaller = Caller.builder().msisdn("79871111111").build();
        var secondCaller = Caller.builder().msisdn("79871111112").build();
        return List.of(firstCaller, secondCaller);
    }

    private List<Operator> getOperators() {
        var firstOperator = Operator.builder().name("роза").build();
        var secondOperator = Operator.builder().name("георгины").build();
        return List.of(firstOperator, secondOperator);
    }

}