package com.zssn.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundApiException extends ApiException {

    public NotFoundApiException(String code) {
        super(code);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
