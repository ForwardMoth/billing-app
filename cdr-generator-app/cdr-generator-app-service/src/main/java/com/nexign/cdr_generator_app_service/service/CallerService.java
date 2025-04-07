package com.nexign.cdr_generator_app_service.service;

import com.nexign.cdr_generator_app_service.entity.Caller;
import com.nexign.cdr_generator_app_service.repository.CallerRepository;
import com.nexign.lib_util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nexign.cdr_generator_app_service.util.Constants.Caller.CALLERS_IS_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class CallerService {

    private final CallerRepository callerRepository;

    @Transactional(readOnly = true)
    public List<Caller> getCallers() {
        List<Caller> callers = callerRepository.findAll();
        if (callers.isEmpty()) {
            throw new NotFoundException(CALLERS_IS_NOT_FOUND_EXCEPTION);
        }
        return callers;
    }

}
