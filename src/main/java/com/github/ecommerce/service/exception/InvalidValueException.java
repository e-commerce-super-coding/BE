package com.github.ecommerce.service.exception;

import com.github.ecommerce.web.advice.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidValueException extends RuntimeException {
    private final HttpStatus httpStatus;
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}
