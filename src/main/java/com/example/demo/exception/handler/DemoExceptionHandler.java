package com.example.demo.exception.handler;

import com.example.demo.exception.ErrorMessage;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.TokenInBlackListException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class DemoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleExceptions(DataIntegrityViolationException ex, WebRequest request) {
        if (Objects.nonNull(ex.getCause())
                && ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException violationException =
                    (org.hibernate.exception.ConstraintViolationException) ex.getCause();
            String constantName = violationException.getConstraintName();
            String fieldName = constantName.substring(constantName.lastIndexOf("_") + 1);
            return mapToErrorMessageBody(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request,
                    "Conflict with SQL query");
        }
        return mapToErrorMessageBody(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request, ex.getMessage());
    }

    @ExceptionHandler(TokenInBlackListException.class)
    public ResponseEntity<Object> handleExceptions(TokenInBlackListException ex, WebRequest request) {
        return mapToErrorMessageBody(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleExceptions(NotFoundException ex, WebRequest request) {
        return mapToErrorMessageBody(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, request, null);
    }

    private ResponseEntity<Object> mapToErrorMessageBody(Exception exception, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request, String message) {
        ErrorMessage errorBody = getExceptionEntity(exception, request, status, message);
        return handleExceptionInternal(exception, errorBody, headers, status, request);
    }

    private ErrorMessage getExceptionEntity(Exception exception, WebRequest request, HttpStatus status, String message) {
        String errMessage = message != null ? message : exception.getMessage();
        logger.error(exception.getMessage(), exception);
        return new ErrorMessage(errMessage, status.value(), status, ((ServletWebRequest) request).getRequest().getRequestURI());
    }
}
