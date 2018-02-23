package com.lianjia.controller;

import java.util.List;

import com.lianjia.entity.Dept;
import com.lianjia.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin
public class DeptController {
	
	@Autowired
	private DeptService deptService;
	
	@GetMapping("/dept")
	public List<Dept> findAll(){
		return deptService.findAll();
	}

	@PostMapping("/dept")
	public void insert(Dept dept){
		deptService.insert(dept);
	}
	
	
}
