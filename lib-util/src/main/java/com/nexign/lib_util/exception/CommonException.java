package com.nexign.lib_util.exception;

import lombok.Builder;

@Builder
public record CommonException(int code, String message) {

}