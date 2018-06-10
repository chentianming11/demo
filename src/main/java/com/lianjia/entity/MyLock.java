package com.lianjia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by chenTianMing on 2018/6/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class MyLock {

    @Id
    private Integer id;
}
