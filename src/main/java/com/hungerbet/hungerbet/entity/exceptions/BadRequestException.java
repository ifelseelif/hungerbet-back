package com.hungerbet.hungerbet.entity.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(String error){
        super(error);
    }
}
