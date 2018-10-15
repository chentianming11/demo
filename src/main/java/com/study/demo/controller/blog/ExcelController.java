package com.study.demo.controller.blog;

import com.study.demo.controller.base.BaseController;
import com.study.demo.service.blog.BlogService;
import com.study.demo.util.ExcelUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 陈添明
 * @date 2018/10/15
 */
@Controller
@RequestMapping("/v1/excel")
public class ExcelController extends BaseController {

    @Autowired
    BlogService blogService;

    @GetMapping("/export/article")
    public ResponseEntity exportArticle(HttpServletResponse response){

        SXSSFWorkbook workbook = blogService.getWorkbook();
        ExcelUtils.export(response, workbook, "测试导出");
        return ok().build();
    }
}
