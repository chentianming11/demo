package com.study.demo.entity.blog;

import lombok.AllArgsConstructor;
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
@Table(name = "T_BLOG_ARTICLE")
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticle implements Serializable {

    private static final long serialVersionUID = -1041526867294109555L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer collectionId;
    private String title;
    private String content;
    @Column(insertable = false, updatable = false)
    private Date createTime;
    private Integer isValid;


}
