package com.example.demo.exception;

public class NotEnoughMoney extends RuntimeException{
    private final static String MESSAGE = "Not enough money";
    public NotEnoughMoney(){
        super(MESSAGE);
    }
}
