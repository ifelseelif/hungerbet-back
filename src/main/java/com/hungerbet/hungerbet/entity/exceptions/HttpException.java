package com.hungerbet.hungerbet.entity.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends Exception {
    private final HttpStatus httpStatus;

    public HttpException(String error, HttpStatus httpStatus) {
        super(error);
        this.httpStatus = httpStatus;
    }

}
