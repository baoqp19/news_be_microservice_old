package com.example.user_service.exception.payload;

import com.example.user_service.exception.EnumConfig.ErrorCode;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException{

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
