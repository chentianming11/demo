package com.study.demo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询条件对象
 *
 * @author chen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpQuery implements Serializable {
    private static final long serialVersionUID = -5766058412951594560L;
    private Integer pageNum;
    private Integer pageSize;
    private Integer status;
    private Date start;
    private Date end;
    private String idOrName;
    private Boolean flag;
}
