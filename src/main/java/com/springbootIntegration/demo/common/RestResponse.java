package com.springbootIntegration.demo.common;

/**
 * @author liukun
 * @description 采用JSON方式给前端回复
 * @date 2019/9/1
 */
public class RestResponse {
    private final static String RESP_SUCCESS = "SUCCESS";
    private final static String RESP_ERROR = "ERROR";

    public static <T> RestResult<T> responseOK() {
        return new RestResult<T>().setCode(RestCode.SUCCESS).setMsg(RESP_SUCCESS);
    }

    public static <T> RestResult<T> responseOK(T data) {
        return new RestResult<T>().setCode(RestCode.SUCCESS).setMsg(RESP_SUCCESS).setData(data);
    }

    public static <T> RestResult<T> responseError() {
        return new RestResult<T>().setCode(RestCode.FAIL).setMsg(RESP_ERROR);
    }

    public static <T> RestResult<T> responseError(T data){
        return new RestResult<T>().setCode(RestCode.FAIL).setMsg(RESP_ERROR).setData(data);
    }

    public static <T> RestResult<T> makeRsp(int code, String msg) {
        return new RestResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> RestResult<T> makeRsp(int code, String msg, T data) {
        return new RestResult<T>().setCode(code).setMsg(msg).setData(data);
    }
}
