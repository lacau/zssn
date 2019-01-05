package com.zssn.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictApiException extends ApiException {

    public ConflictApiException(String code) {
        super(code);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
