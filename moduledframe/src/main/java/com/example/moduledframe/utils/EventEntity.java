package com.example.moduledframe.utils;



/**
 * Created by DELL on 2018/5/22.
 * 事件传递
 */

public class EventEntity {

    private String msg;
    private int code;
    private Object data;


    public EventEntity(int type, String msg, Object data) {
        this.code = type;
        this.msg = msg;
        this.data = data;
    }

    public Object getExpress() {
        return data;
    }

    public void setExpress(Object data) {
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
