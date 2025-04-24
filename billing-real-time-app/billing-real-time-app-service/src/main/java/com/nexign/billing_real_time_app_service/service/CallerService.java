package com.nexign.billing_real_time_app_service.service;

import com.nexign.billing_real_time_app_service.entity.Caller;

import java.util.Map;
import java.util.Set;

public interface CallerService {


    Map<String, Caller> createOrFindCallers(Set<String> msisdnSet);

}
