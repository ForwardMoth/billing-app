package com.nexign.brt_app_service.service;

import com.nexign.brt_app_service.entity.Caller;
import com.nexign.lib_brt_api.dto.request.CreateCallerRequest;
import com.nexign.lib_brt_api.dto.request.ReplenishmentAmountRequest;
import com.nexign.lib_brt_api.dto.request.UpdateCallerRequest;
import com.nexign.lib_brt_api.dto.request.UpdateTariffRequest;
import com.nexign.lib_brt_api.dto.response.CallerResponse;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface CallerService {


    Map<String, Caller> createOrFindCallers(Set<String> msisdnSet);

    Page<CallerResponse> getCallerList();

    CallerResponse createCaller(CreateCallerRequest request);

    CallerResponse getCaller(UUID callerUuid);

    CallerResponse updateCaller(UUID callerUuid, UpdateCallerRequest request);

    CallerResponse updateTariff(UUID callerUuid, UpdateTariffRequest request);

    CallerResponse replenishmentAmount(UUID callerUuid, ReplenishmentAmountRequest request);

    void deleteCaller(UUID callerUuid);

}
