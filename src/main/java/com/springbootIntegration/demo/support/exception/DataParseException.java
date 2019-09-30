package com.springbootIntegration.demo.support.exception;

import com.springbootIntegration.demo.support.http.HttpCode;

public class DataParseException extends BaseException {
    public DataParseException() {
    }

    public DataParseException(Throwable ex) {
        super(ex);
    }

    public DataParseException(String message) {
        super(message);
    }

    public DataParseException(String message, Throwable ex) {
        super(message, ex);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}