package com.app.userservice.components.responses;

import org.springframework.http.HttpStatus;

public class Error {

    private String code;
    private String message;

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(HttpStatus code, String message) {
        this.code = Integer.toString(code.value());
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
