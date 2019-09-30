package com.springbootIntegration.demo.support.exception;

import com.springbootIntegration.demo.support.http.HttpCode;
import com.springbootIntegration.demo.support.http.HttpCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

/**
 * @author liukun
 * @description 基础异常类，继承RuntimeException，依赖HttpCode
 * @date 2019/9/14
 */
public abstract class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(Throwable ex) {
        super(ex);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable ex) {
        super(message, ex);
    }

    public void handler(ModelMap modelMap) {
        modelMap.put("code", this.getCode().value());
        if (StringUtils.isNotBlank(this.getMessage())) {
            modelMap.put("msg", this.getMessage());
        } else {
            modelMap.put("msg", this.getCode().msg());
        }

        modelMap.put("timestamp", System.currentTimeMillis());
    }

    protected abstract HttpCode getCode();
}