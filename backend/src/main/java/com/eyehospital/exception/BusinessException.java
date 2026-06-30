package com.eyehospital.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Object data;

    public BusinessException(String message) {
        super(message);
        this.data = null;
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data = data;
    }
}