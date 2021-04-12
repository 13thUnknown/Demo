package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorMessage {
    private String message;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String uri;
}
