package com.zssn.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties({"cause", "localizedMessage", "message", "stackTrace", "suppressed"})
public abstract class ApiException extends RuntimeException {

    final private String code;

    public abstract HttpStatus getStatus();

    public ApiException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
