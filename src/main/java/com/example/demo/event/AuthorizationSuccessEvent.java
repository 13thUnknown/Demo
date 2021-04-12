package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

public class AuthorizationSuccessEvent extends ApplicationEvent {

    public AuthorizationSuccessEvent(Object source) {
        super(source);
    }
}
