package com.lianjia.service;

import com.lianjia.entity.Dept;
import com.lianjia.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Dept服务类
 * @author chen
 *
 */
@Service
public class DeptService {
	
	@Autowired
	private DeptMapper deptMapper;
	/**
	 * 查询部门列表
	 * @return 部门列表
	 */
	public List<Dept> findAll() {
        List<Dept>	depts = deptMapper.selectAll();
			System.out.println("从数据库中获取部门列表");
        return depts;
	}

    /**
     * 新增一个部门
     * @param dept
     */
    public void insert(Dept dept) {
	    deptMapper.insert(dept);
    }
}
