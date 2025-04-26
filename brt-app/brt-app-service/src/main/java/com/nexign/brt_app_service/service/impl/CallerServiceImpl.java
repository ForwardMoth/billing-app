package com.nexign.brt_app_service.service.impl;

import com.nexign.brt_app_service.entity.Caller;
import com.nexign.brt_app_service.entity.Operator;
import com.nexign.brt_app_service.mapper.CallerMapper;
import com.nexign.brt_app_service.mapper.ResourceMapper;
import com.nexign.brt_app_service.repository.CallerRepository;
import com.nexign.brt_app_service.repository.OperatorRepository;
import com.nexign.brt_app_service.service.CallerService;
import com.nexign.brt_app_service.service.PersonNameService;
import com.nexign.lib_brt_api.dto.request.CreateCallerRequest;
import com.nexign.lib_brt_api.dto.request.ReplenishmentAmountRequest;
import com.nexign.lib_brt_api.dto.request.UpdateCallerRequest;
import com.nexign.lib_brt_api.dto.request.UpdateTariffRequest;
import com.nexign.lib_brt_api.dto.response.CallerResponse;
import com.nexign.lib_util.exception.BadRequestException;
import com.nexign.lib_util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.nexign.lib_util.util.GeneratorUtil.generateRandomIndexOfArray;


@Service
@RequiredArgsConstructor
public class CallerServiceImpl implements CallerService {

    private final CallerRepository callerRepository;
    private final OperatorRepository operatorRepository;
    private final PersonNameService personNameService;
    private final ResourceMapper resourceMapper;
    private final CallerMapper mapper;

    @Override
    @Transactional
    public Map<String, Caller> createOrFindCallers(Set<String> msisdnSet) {
        Map<String, Caller> msisdnCallerMap = new HashMap<>();

        List<Caller> foundCallers = callerRepository.findCallersByMsisdnIn(msisdnSet);
        foundCallers.forEach(caller -> msisdnCallerMap.put(caller.getMsisdn(), caller));

        List<Caller> createdCallers = createCallersFromCDR(msisdnCallerMap, msisdnSet);
        createdCallers.forEach(caller -> msisdnCallerMap.put(caller.getMsisdn(), caller));

        return msisdnCallerMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CallerResponse> getCallerList() {
        // todo: Поиск по абонентам (actual, filter, pagination)
        return null;
    }

    @Override
    @Transactional
    public CallerResponse createCaller(CreateCallerRequest request) {
        final var msisdn = request.getMsisdn();

        if (callerRepository.existsByMsisdn(msisdn)) {
            throw new BadRequestException(String.format("Абонент с номером %s уже существует", msisdn));
        }

        var operator = operatorRepository.findByNameRomashka();
        var caller = mapper.map(operator, msisdn);

        var personName = personNameService.createOrFindPersonName(request.getPersonName());
        caller.setPersonName(personName);

        var resource = resourceMapper.create(caller, request.getMinutes());
        caller.setResource(resource);

        return mapper.map(callerRepository.save(caller));
    }

    @Override
    @Transactional(readOnly = true)
    public CallerResponse getCaller(UUID callerUuid) {
        return callerRepository.findByUuidAndActualIsTrue(callerUuid)
                .map(mapper::map)
                .orElseThrow(() -> new NotFoundException(String.format("Абонент с идентификатором %s не существует", callerUuid)));
    }

    @Override
    @Transactional
    public CallerResponse updateCaller(UUID callerUuid, UpdateCallerRequest request) {
        // todo: обновить параметры абонента (actual, ФИО, номер телефона)
        return null;
    }

    @Override
    @Transactional
    public CallerResponse updateTariff(UUID callerUuid, UpdateTariffRequest request) {
        // todo: Сделать метод после создания HRS
        return null;
    }

    @Override
    @Transactional
    public CallerResponse replenishmentAmount(UUID callerUuid, ReplenishmentAmountRequest request) {
        // todo: Пополнение счета + проверка был ли запрет на звонки
        return null;
    }

    @Override
    @Transactional
    public void deleteCaller(UUID callerUuid) {
        var caller = callerRepository.findByUuid(callerUuid)
                .orElseThrow(() -> new NotFoundException(String.format("Абонент с идентификатором %s не существует", callerUuid)));
        if (!caller.isActual()) {
            throw new BadRequestException(String.format("Абонент с идентификатором %s удален", callerUuid));
        }
        caller.setActual(false);
        callerRepository.save(caller);
    }

    private List<Caller> createCallersFromCDR(Map<String, Caller> msisdnCallerMap, Set<String> msisdnSet) {
        if (msisdnCallerMap.size() == msisdnSet.size()) {
            return List.of();
        }
        List<Operator> operators = operatorRepository.findByNameNotRomashka();
        final var operatorsSize = operators.size();
        List<Caller> callers = msisdnSet.stream()
                .filter(msisdn -> !msisdnCallerMap.containsKey(msisdn))
                .map(msisdn -> mapper.map(operators.get(generateRandomIndexOfArray(operatorsSize)), msisdn))
                .toList();
        return callerRepository.saveAllAndFlush(callers);
    }

}
