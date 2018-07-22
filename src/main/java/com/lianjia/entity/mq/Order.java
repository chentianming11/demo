package com.lianjia.entity.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 陈添明
 * @date 2018/7/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    /**
     * 订单编号
     **/
    private String orderId;

    /**
     * 订单金额
     **/
    private BigDecimal orderAmount;


}
