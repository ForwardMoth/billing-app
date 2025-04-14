package com.nexign.cdr_generator_app_service.service.impl;

import com.nexign.cdr_generator_app_service.entity.Caller;
import com.nexign.cdr_generator_app_service.repository.CallerRepository;
import com.nexign.cdr_generator_app_service.service.CallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallerServiceImpl implements CallerService {

    private final CallerRepository callerRepository;

    @Transactional(readOnly = true)
    public List<Caller> getCallers() {
        return callerRepository.findAll();
    }

}
