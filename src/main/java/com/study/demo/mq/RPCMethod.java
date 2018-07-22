package com.study.demo.mq;

public class RPCMethod {
    public static String addOrder(String orderInfo) {
        try {
            System.out.println("orderInfo已添加到数据库");
            return "订单ID";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}