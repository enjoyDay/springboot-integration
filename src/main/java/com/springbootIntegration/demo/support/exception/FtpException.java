package com.springbootIntegration.demo.support.exception;

import com.springbootIntegration.demo.support.http.HttpCode;
import com.springbootIntegration.demo.support.http.HttpCode;

public class FtpException extends BaseException {
    public FtpException() {
    }

    public FtpException(String message) {
        super(message);
    }

    public FtpException(String message, Throwable throwable) {
        super(message, throwable);
    }
    @Override
    protected HttpCode getCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}
