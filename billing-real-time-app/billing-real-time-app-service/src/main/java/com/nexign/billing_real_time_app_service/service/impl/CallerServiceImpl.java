package com.nexign.billing_real_time_app_service.service.impl;

import com.nexign.billing_real_time_app_service.entity.Caller;
import com.nexign.billing_real_time_app_service.entity.Operator;
import com.nexign.billing_real_time_app_service.repository.CallerRepository;
import com.nexign.billing_real_time_app_service.repository.OperatorRepository;
import com.nexign.billing_real_time_app_service.service.CallerService;
import com.nexign.lib_util.service.UuidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.nexign.lib_util.util.GeneratorUtil.generateRandomIndexOfArray;


@Service
@RequiredArgsConstructor
public class CallerServiceImpl implements CallerService {

    private final CallerRepository callerRepository;
    private final OperatorRepository operatorRepository;

    @Override
    @Transactional
    public Map<String, Caller> createOrFindCallers(Set<String> msisdnSet) {
        Map<String, Caller> msisdnCallerMap = new HashMap<>();

        List<Caller> callers = callerRepository.findCallersByMsisdnIn(msisdnSet);
        callers.forEach(caller -> msisdnCallerMap.put(caller.getMsisdn(), caller));

        List<Caller> callersForCreate = getCreatedCallers(msisdnCallerMap, msisdnSet);
        callersForCreate.forEach(caller -> msisdnCallerMap.put(caller.getMsisdn(), caller));

        return msisdnCallerMap;
    }

    private List<Caller> getCreatedCallers(Map<String, Caller> msisdnCallerMap, Set<String> msisdnSet) {
        if (msisdnCallerMap.size() == msisdnSet.size()) {
            return List.of();
        }
        List<Operator> operators = operatorRepository.findByNameNotRomashka();
        List<Caller> callers = msisdnSet.stream()
                .filter(msisdn -> !msisdnCallerMap.containsKey(msisdn))
                .map(msisdn -> createCaller(operators, msisdn))
                .toList();
        return callerRepository.saveAllAndFlush(callers);
    }

    private Caller createCaller(List<Operator> operators, String msisdn) {
        return Caller.builder()
                .uuid(UuidService.generateRandomUuid())
                .msisdn(msisdn)
                .operator(operators.get(generateRandomIndexOfArray(operators.size())))
                .build();
    }

}
