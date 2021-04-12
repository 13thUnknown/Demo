package com.example.demo.exception;

public class TokenInBlackListException extends RuntimeException{
    private final static String MESSAGE = "Sing up again";
    public TokenInBlackListException(){
        super();
    }
}
