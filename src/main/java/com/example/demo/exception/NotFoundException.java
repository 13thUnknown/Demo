package com.example.demo.exception;

public class NotFoundException extends RuntimeException{
    private final static String MESSAGE = " not found";

    public NotFoundException(Class cls){
        super(cls + MESSAGE);
    }
}
