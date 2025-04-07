package com.nexign.lib_util.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

}