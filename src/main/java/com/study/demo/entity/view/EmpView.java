package com.study.demo.entity.view;


import com.study.demo.entity.Emp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpView extends Emp {

    private static final long serialVersionUID = -8627959366921505919L;
    private String deptName;
    private String jobName;
}
