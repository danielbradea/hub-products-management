package com.bid90.hpm.exceptions;


import lombok.Getter;

@Getter
public class ProductException extends RuntimeException{

    private final int statusCodes;
    public ProductException(String message, int statusCodes) {
        super(message);
        this.statusCodes = statusCodes;
    }

    public ProductException(String message,int statusCodes, Throwable cause) {
        super(message, cause);
        this.statusCodes = statusCodes;
    }
}
