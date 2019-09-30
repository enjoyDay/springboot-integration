package com.springbootIntegration.demo.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author liukun
 * @description
 * @date 2019/9/1
 */
public class RestResult<T> implements Serializable {
    private static final long serialVersionUID = 3758864789222317092L;

    public int code;

    private String msg;

    private T data;

    public RestResult<T> setCode(RestCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RestResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RestResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
