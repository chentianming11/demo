package com.lianjia.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by chen on 2018/2/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
    @JSONField(name = "add_list") // 指定序列化的名称
    private List<Address> addrList;
    @JSONField(format = "yyyy年MM月dd日") // 指定序列化日期的格式
    private LocalDate birthday;
    @JSONField(serialize = false) // 该字段不进行序列化
    private String ignore;
}
