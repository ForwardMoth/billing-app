package com.nexign.cdr_generator_app_service.runner;

import com.nexign.cdr_generator_app_service.service.worker.BackgroundWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartRunner implements ApplicationRunner {

    private final BackgroundWorker backgroundWorker;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        backgroundWorker.generateCallDataRecords();
        backgroundWorker.sendCallDataRecords();
    }

}
