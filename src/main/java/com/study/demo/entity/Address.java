package com.study.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chen on 2018/2/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String province;
    private String city;
    private String district;
    private String detail;
}
