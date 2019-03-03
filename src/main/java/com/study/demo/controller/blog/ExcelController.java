package com.study.demo.controller.blog;

import com.study.demo.controller.base.BaseController;
import com.study.demo.entity.view.ProductPackageView;
import com.study.demo.service.blog.BlogService;
import com.study.demo.util.ExcelUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    /**
     * 测试导入
     */
    @PostMapping("/import/test")
    public ResponseEntity importTest(MultipartFile file) throws IOException {

        ExcelUtils.Mapping mapping =  ExcelUtils.Mapping.newInstance();
        mapping.put("套餐价格（元/月）", "price");
        mapping.put("房源量", "houseNum");
        mapping.put("推广币", "promotion");
        mapping.put("名称", "name");
        mapping.put("标签", "remark");
        mapping.put("限购因素", "purchaseLimitFactor");
        mapping.put("刷新次数", "flushNum");
        mapping.put("渠道", "supplierName");
        mapping.put("付费类型", "payTypeName");
        mapping.put("版本类型", "version");
        mapping.put("有效期", "validPeriod");
        mapping.put("所属部门ID", "belongOrgId");
        mapping.put("限购方式", "purchaseLimitType");
        mapping.put("有效期", "validPeriod");
        mapping.put("开通时间", "createdAt");

        List<ProductPackageView> productPackageViews = ExcelUtils.toList(file.getInputStream(), mapping, ProductPackageView.class);
        System.out.println(productPackageViews);
        return ok().body(productPackageViews).build();
    }

}
