package com.nexign.cdr_generator_app_service.service.worker;

import com.nexign.cdr_generator_app_service.service.GeneratorService;
import com.nexign.cdr_generator_app_service.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackgroundWorker {

    private final GeneratorService generatorService;
    private final SenderService senderService;

    @Async
    public void generateCallDataRecords() {
        while (true) {
            generatorService.generate();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Async
    public void sendCallDataRecords() {
        while (true) {
            senderService.send();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
