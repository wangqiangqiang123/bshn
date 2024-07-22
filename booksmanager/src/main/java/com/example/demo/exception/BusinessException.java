package com.example.demo.exception;


import lombok.Data;


public class BusinessException extends RuntimeException {
    private int code;
    private String msSSg;
    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msSSg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msSSg;
    }

    public void setMsg(String msg) {
        this.msSSg = msg;
    }
}

