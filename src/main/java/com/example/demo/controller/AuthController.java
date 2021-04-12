package com.example.demo.controller;

import com.example.demo.dto.UserInfo;
import com.example.demo.dto.create.UserCreateDto;
import com.example.demo.dto.get.ResponseGetDto;
import com.example.demo.dto.get.UserGetDto;
import com.example.demo.service.UserService;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final LoadingCache<String, Integer> blacklist;

    @PostMapping("/register")
    public ResponseGetDto registerUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseGetDto("User created");
    }

    @PostMapping("/login")
    public UserGetDto auth(@RequestBody UserInfo userInfo) {
        return new UserGetDto(userService.findUserByCreds(userInfo));
    }

    @PostMapping("/user/logout")
    public ResponseGetDto logout() {
        String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        blacklist.put(jwt,0);
        return new ResponseGetDto("Logout success");
    }
}
