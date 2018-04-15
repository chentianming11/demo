package com.lianjia.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2018/3/22.
 */
@Data
@Builder
public class LombokEntity {
    private String name;
    private Integer age;
    @Delegate // 它会该类生成一些列的方法，这些方法都来自与List接口
    private List<Employee> employees = new ArrayList<>();
}
