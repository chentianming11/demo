package com.study.demo.service;

import java.util.List;


import com.study.demo.entity.Dept;
import com.study.demo.entity.Job;
import com.study.demo.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Job服务类
 *
 * @author chen
 */
@Service
public class JobService {

    public static final String HAHA = "haha";
    public static final String SS = "sss";
    @Autowired
    private JobMapper jobMapper;
    private Dept dept;
    private Job job;

    /**
     * 根据部门id查询岗位信息
     *
     * @param deptId 部门id
     * @return 岗位对象
     */
    public List<Job> findByDeptId(Long deptId) {

        job = new Job();
        job.setDeptId(deptId);
        System.out.println(SS);
        return jobMapper.select(job);

    }

    public Dept getDept(String ss) {
        System.out.println(ss);
        dept = new Dept();
        if (dept != null) {
            validate();
            System.out.println(HAHA);
            System.out.println(HAHA);
            System.out.println(HAHA);
        }
        return dept;
    }

    private void validate() {
        System.out.println(HAHA);
        System.out.println(HAHA);
        System.out.println(HAHA);
    }

}
