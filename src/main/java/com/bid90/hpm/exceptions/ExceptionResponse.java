package com.bid90.hpm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    String errorMessage;
    int statusCode;

}
