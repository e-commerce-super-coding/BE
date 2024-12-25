package com.github.ecommerce.service.exception;

import com.github.ecommerce.web.advice.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class S3Exception extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String key;
    public S3Exception(ErrorCode errorCode,String key) {
        super(errorCode.getErrorMessage());
        this.httpStatus = errorCode.getHttpStatus();
        this.key = key;
    }
}
