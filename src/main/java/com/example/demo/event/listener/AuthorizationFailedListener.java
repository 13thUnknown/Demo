package com.example.demo.event.listener;

import com.example.demo.event.AuthorizationFailedEvent;
import com.example.demo.security.JwtProvider;
import com.example.demo.security.LoginAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorizationFailedListener implements ApplicationListener<AuthorizationFailedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthorizationFailedEvent event) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String remoteAddr = request.getRemoteAddr();

        LOGGER.info("Auth failed");
        loginAttemptService.loginFailed(remoteAddr);
    }
}
