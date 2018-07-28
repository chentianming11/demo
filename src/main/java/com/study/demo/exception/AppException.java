package com.study.demo.exception;

/**
 * Created by chen on 2018/2/27.
 */
public class AppException extends RuntimeException {

    private int code = 0;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 错误代码
     */
    public enum CodeEnum {
        未知(0),
        未登录(401),
        无权限(403),
        数据过期(498);
        public int code;

        CodeEnum(int code) {
            this.code = code;
        }
    }
}

