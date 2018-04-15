package com.lianjia.controller;

import java.util.List;

import com.lianjia.entity.Job;
import com.lianjia.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class JobController{
	
	@Autowired
	private JobService jobService;
	
	@GetMapping("/job")
	public List<Job> findByDeptId(Long deptId){
		return jobService.findByDeptId(deptId);
	}
}
