package com.lianjia.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Created by chen on 2018/1/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Emp implements Serializable {
	private static final long serialVersionUID = -8810759070461959819L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Field(index= FieldIndex.analyzed,analyzer="ik",store=true,searchAnalyzer="ik",type = FieldType.String)
    private String  name;
    private Long deptId;
    private Long jobId;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+08")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
    private Integer status;//状态
    
}
