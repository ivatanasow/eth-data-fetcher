package com.home.ethfetcher.controller;

import com.home.ethfetcher.exception.JwtParsingException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(JwtParsingException.class)
    public ResponseEntity<ErrorDetails> handleJwtParsingException(JwtParsingException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Setter
    @Getter
    public static class ErrorDetails {
        private String message;
    }

}
