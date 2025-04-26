package com.nexign.brt_app_service.controller;

import com.nexign.brt_app_service.service.CallerService;
import com.nexign.lib_brt_api.dto.request.CreateCallerRequest;
import com.nexign.lib_brt_api.dto.request.ReplenishmentAmountRequest;
import com.nexign.lib_brt_api.dto.request.UpdateCallerRequest;
import com.nexign.lib_brt_api.dto.request.UpdateTariffRequest;
import com.nexign.lib_brt_api.dto.response.CallerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(("/callers"))
@RequiredArgsConstructor
public class CallerController {

    private final CallerService callerService;

    @GetMapping("/list")
    public ResponseEntity<Page<CallerResponse>> getCallerList() {
        return ResponseEntity.ok(callerService.getCallerList());
    }

    @PostMapping("/create")
    public ResponseEntity<CallerResponse> createCaller(@RequestBody CreateCallerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callerService.createCaller(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CallerResponse> getCaller(@PathVariable("uuid") UUID callerUuid) {
        return ResponseEntity.ok(callerService.getCaller(callerUuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CallerResponse> updateCaller(@PathVariable("uuid") UUID callerUuid, UpdateCallerRequest request) {
        return ResponseEntity.ok(callerService.updateCaller(callerUuid, request));
    }

    @PatchMapping("/{uuid}/updateTariff")
    public ResponseEntity<CallerResponse> updateTariff(@PathVariable("uuid") UUID callerUuid,
                                                       @RequestBody UpdateTariffRequest request) {
        return ResponseEntity.ok(callerService.updateTariff(callerUuid, request));
    }

    @PatchMapping("/{uuid}/replenishment")
    public ResponseEntity<CallerResponse> replenishmentAmount(@PathVariable("uuid") UUID callerUuid,
                                                              @RequestBody ReplenishmentAmountRequest request) {
        return ResponseEntity.ok(callerService.replenishmentAmount(callerUuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCaller(@PathVariable("uuid") UUID callerUuid) {
        callerService.deleteCaller(callerUuid);
        return ResponseEntity.noContent().build();
    }

}
