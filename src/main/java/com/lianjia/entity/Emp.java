package com.lianjia.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by chen on 2018/1/25.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Emp implements Serializable {
	private static final long serialVersionUID = -8810759070461959819L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String  name;
    private Long deptId;
    private Long jobId;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+08")
    private Date hireDate;
    private Integer status;//状态
    
}
