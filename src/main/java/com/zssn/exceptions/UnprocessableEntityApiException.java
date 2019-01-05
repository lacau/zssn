package com.zssn.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityApiException extends ApiException {

    public UnprocessableEntityApiException(String code) {
        super(code);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
