package com.study.demo.entity.enums;

/**
 * @author 陈添明
 * @date 2018/8/28
 */
public enum CmsArticleStatusEnum {

    编辑中(1000900001, 10),
    待审核(1000900002, 20),
    已发布(1000900003, 30),
    被打回(1000900004, 40),
    已下线(1000900005, 50),
    更新中(1000900006, 60);


    public final Integer value;

    public final Integer sort;

    CmsArticleStatusEnum(Integer value, Integer sort) {
        this.value = value;
        this.sort = sort;
    }

}
