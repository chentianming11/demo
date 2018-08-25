package com.study.demo.entity.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 陈添明
 * @date 2018/7/28
 */
@Data
@Table(name = "T_BLOG_COLLECTION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogCollection implements Serializable {

    private static final long serialVersionUID = -7588854752397846189L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String name;
    private String description;

    @Column(insertable = false, updatable = false)
    private Date createTime;

}
