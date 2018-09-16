package com.study.demo.exception;

/**
 * Created by chen on 2018/2/27.
 */
public class AppException extends RuntimeException {

    private int code ;

    public AppException(String message) {
        super(message);
        this.code = CodeEnum.未知系统错误.code;
    }

    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AppException(String message, int code, Throwable throwable){
        super(message, throwable);
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 业务错误代码
     */
    public enum CodeEnum {
        正常(0),
        未知系统错误(10000),
        未知业务失败(20000),
        未登录(20001),
        无权限(20002);
        public int code;

        CodeEnum(int code) {
            this.code = code;
        }
    }
}

