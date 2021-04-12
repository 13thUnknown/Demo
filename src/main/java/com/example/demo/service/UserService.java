package com.example.demo.service;

import com.example.demo.common.RolesEnum;
import com.example.demo.dto.UserInfo;
import com.example.demo.dto.create.UserCreateDto;
import com.example.demo.event.AuthorizationFailedEvent;
import com.example.demo.event.AuthorizationSuccessEvent;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findByLogin(String login){
        User user = userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException(User.class));
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Transactional(readOnly = true)
    public String findUserByCreds(UserInfo userInfo){
        Optional<User> userOptional = userRepository.findByLogin(userInfo.getLogin());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (!passwordEncoder.matches(userInfo.getPassword(),user.getPassword())){
                applicationEventPublisher.publishEvent(new AuthorizationFailedEvent(this));
                throw new BadCredentialsException("BadCredentials");
            }
            applicationEventPublisher.publishEvent(new AuthorizationSuccessEvent(this));
            return jwtProvider.generateToken(user.getLogin());
        }
        else {
            applicationEventPublisher.publishEvent(new AuthorizationFailedEvent(this));
            throw new BadCredentialsException("BadCredentials");
        }
    }

    @Transactional
    public User createUser(UserCreateDto userCreateDto){
        Role role = roleRepository.findByTitle(RolesEnum.USER.getTitle())
                .orElseThrow(()->new NotFoundException(Role.class));
        Transaction transaction = Transaction.builder()
                .value(new BigDecimal(8))
                .currency("USD")
                .build();
        User user = User.builder()
                .login(userCreateDto.getLogin())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .roles(Collections.singletonList(role))
                .transactions(Collections.singletonList(transaction))
                .build();
        transaction.setUser(user);

       return userRepository.save(user);
    }

}
