package com.springbootIntegration.demo.support.exception;

import com.springbootIntegration.demo.support.http.HttpCode;

public class InstanceException extends BaseException {
    public InstanceException() {
    }

    public InstanceException(Throwable t) {
        super(t);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}