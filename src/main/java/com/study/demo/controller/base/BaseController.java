package com.study.demo.controller.base;

import com.study.demo.exception.AppException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/9/11
 */
@Slf4j
public abstract class BaseController {


    /**************** 异常处理 *************/
    @ExceptionHandler(AppException.class)
    public ResponseEntity handleAppException(AppException ex) {
        log.info("", ex);
        return fail(ex.getMessage(), ex.getCode()).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.info(ex.getMessage());
        return fail(ex.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        Throwable t = ex;
        while (true) {
            if (t instanceof AppException) {
                return handleAppException((AppException) t);
            }
            if ((t = t.getCause()) == null) {
                break;
            }
        }
        log.error("", ex);
        return fail(ex.getMessage(), AppException.CodeEnum.未知系统错误.code).build();
        /**************** 异常处理 *************/
    }


    //----------------------- 返回定义 ---------------------//

    /**
     * 操作正常返回
     *
     * @return
     */
    public Result ok() {
        return new Result("ok", AppException.CodeEnum.正常.code);
    }

    /**
     * 操作业务异常返回
     *
     * @return
     */
    public Result fail(String msg) {
        return new Result(msg, AppException.CodeEnum.未知业务失败.code);
    }

    public Result fail(String msg, int code) {
        return new Result(msg, code);
    }

    /**
     * Controller返回值，spring会自动转化为json，可以链式操作<br>
     * body：直接返回javaBean<br>
     */
    public class Result {
        private Object body;
        @Getter
        private int code;
        @Getter
        private String msg;

        public Result(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }

        /**
         * 直接返回javaBean
         *
         * @param body
         * @return
         */
        public Result body(Object body) {
            this.body = body;
            return this;
        }

        public ResponseEntity build() {
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8);
            Map result = new HashMap<String, Object>(3);
            result.put("code", this.code);
            result.put("msg", this.msg);
            result.put("data", this.body);
            return bodyBuilder.body(result);
        }
    }
}
