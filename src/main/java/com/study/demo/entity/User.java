package com.study.demo.entity;

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

    public enum QuestionEnum {

        交易管家(1, "交易管家好看吗？", 1),
        交易员(2, "交易员好看吗？", 1);

        public final Integer id;
        public final String question;
        public final Integer valid;

        QuestionEnum(Integer id, String question, Integer valid) {
            this.id = id;
            this.question = question;
            this.valid = valid;
        }
    }

    public enum ValidEnum {
        有效,
        无效
    }

    public enum StatusEnum {

        未评价(0),
        已评价(1);

        public final Integer status;

        StatusEnum(Integer status) {
            this.status = status;
        }
    }

}
