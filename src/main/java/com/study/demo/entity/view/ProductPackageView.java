package com.study.demo.entity.view;

import com.study.demo.entity.ProductPackage;
import lombok.Data;

@Data
public class ProductPackageView extends ProductPackage {

    private String supplierName;

    private String payTypeName;

    /**
     * 限购类型
     */
    private String purchaseLimitType;
    /**
     * 限购因素
     */
    private String purchaseLimitFactor;
    /**
     * 所属部门ID
     */
    private String belongOrgId;


}
