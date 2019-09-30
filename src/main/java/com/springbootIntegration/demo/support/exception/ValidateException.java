package com.springbootIntegration.demo.support.exception;

import com.springbootIntegration.demo.support.http.HttpCode;

/**
 * @author liukun
 * @description
 * @date 2019/9/14
 */
public class ValidateException extends BaseException {
    public ValidateException() {
    }

    public ValidateException(Throwable ex) {
        super(ex);
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable ex) {
        super(message, ex);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.PRECONDITION_FAILED;
    }
}
