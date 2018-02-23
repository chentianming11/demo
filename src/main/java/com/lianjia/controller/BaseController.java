package com.lianjia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chen on 2018/2/10.
 */
public abstract class BaseController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    // -------------异常捕获-----------
    @ExceptionHandler()
    public String handleException(){
        return "统一异常处理";
    }


    // ---------- 日期格式转换 ----------
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
