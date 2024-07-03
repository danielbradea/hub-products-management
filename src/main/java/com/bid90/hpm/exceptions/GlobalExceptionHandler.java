package com.bid90.hpm.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductException.class)
    ResponseEntity<ExceptionResponse> handleProductException(ProductException ex){
        logger.warn(ex.getMessage());
        var exceptionResponse = new ExceptionResponse(ex.getMessage(),ex.getStatusCodes());
        return ResponseEntity.status(ex.getStatusCodes()).body(exceptionResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(RuntimeException ex) {
        logger.error(ex.getMessage(),ex.getCause());
        var exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}