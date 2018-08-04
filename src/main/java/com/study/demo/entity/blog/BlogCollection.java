package com.study.demo.entity.blog;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 陈添明
 * @date 2018/7/28
 */
@Data
@Table(name = "T_BLOG_COLLECTION")
public class BlogCollection implements Serializable {

    private static final long serialVersionUID = -7588854752397846189L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Date createTime;

}