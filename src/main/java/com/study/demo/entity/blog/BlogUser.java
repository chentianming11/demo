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
@Table(name = "T_BLOG_USER")
public class BlogUser implements Serializable {

    private static final long serialVersionUID = -1827399869075796711L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String headUrl;
    private String description;
    private Date date;


}
