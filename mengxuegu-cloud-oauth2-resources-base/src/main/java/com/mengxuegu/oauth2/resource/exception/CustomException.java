package com.mengxuegu.oauth2.resource.exception;

public class CustomException extends RuntimeException{

    private  int code;
    private String msg;

    public CustomException(ErrorInfo errorInfo){
        super(errorInfo.message);
        this.code = errorInfo.code;
        this.msg = errorInfo.message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
