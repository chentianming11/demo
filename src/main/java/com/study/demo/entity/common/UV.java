package com.study.demo.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 陈添明
 * @date 2018/11/6
 */
@Table(name = "t_common_uv")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UV {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String pageViewUicode;

    private Integer count;

    private Date date;

    @Column(insertable = false, updatable = false)
    private Date ctime;

}
