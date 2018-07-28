package com.study.demo.entity;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by chen on 2018/1/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Dept implements Serializable {
    private static final long serialVersionUID = 3408603036360582621L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;//
}
