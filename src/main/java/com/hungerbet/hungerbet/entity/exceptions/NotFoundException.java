package com.hungerbet.hungerbet.entity.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException(String error){
        super(error);
    }
}
