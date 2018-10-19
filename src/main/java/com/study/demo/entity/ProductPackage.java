package com.study.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 套餐类
 *
 * @author chen
 * @date 2018/4/10
 */
@Data
@NoArgsConstructor
@Table(name = "T_DUANKOU_PRODUCT_PACKAGE")
public class ProductPackage implements Serializable {
    @Id
    private Integer id;
    private Integer supplierType;
    private BigDecimal price;
    private Integer valid;
    private Integer validPeriod;
    private String name;
    private String remark;
    private Date createdAt;
    private Integer creatorId;
    private String creatorName;
    private String promotion;
    private String houseNum;
    private String flushNum;
    private String version;

    /**
     * 付费类型
     *
     * @See com.dooioo.duankou.enums.PayTypeEnum
     */
    private Integer payType;


}
