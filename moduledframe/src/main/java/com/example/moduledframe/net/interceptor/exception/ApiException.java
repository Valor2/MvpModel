package com.example.moduledframe.net.interceptor.exception;

/**
 * Creation Time: 2018/8/20 10:44.
 * Author: King.
 * Description: 服务端的异常处理类，根据与服务端约定的code判断
 */
public class ApiException extends RuntimeException{

    private int errorCode;
    private String detailMsg;

    public ApiException(int errorCode, String msg,String detailMsg) {
        super(msg);
        this.errorCode = errorCode;
        this.detailMsg=detailMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }
}
