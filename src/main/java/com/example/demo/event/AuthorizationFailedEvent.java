package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

public class AuthorizationFailedEvent extends ApplicationEvent {

    public AuthorizationFailedEvent(Object source) {
        super(source);
    }
}
