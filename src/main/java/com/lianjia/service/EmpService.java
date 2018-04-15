package com.lianjia.service;

import com.github.pagehelper.PageHelper;
import com.lianjia.entity.Emp;
import com.lianjia.entity.EmpQuery;
import com.lianjia.entity.view.EmpView;
import com.lianjia.mapper.EmpMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * EmpService服务类
 */
@Service
public class EmpService {
	
	@Autowired
	private EmpMapper empMapper;

	/**
	 * 分页查询用户列表通（PageHelper）
	 * @return 分页结果信息
	 */
	public Map<String, Object> findByPage(EmpQuery query) {
		String idOrName = query.getIdOrName();
		boolean numeric = StringUtils.isNumeric(idOrName);
		if(numeric){
			query.setFlag(true);
		}else {
			query.setFlag(false);
		}
		HashMap<String, Object> map = new HashMap<>();
		com.github.pagehelper.Page<EmpView> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
		empMapper.findEmpJoinDeptAndJob(query);
		map.put("emps", page.getResult());
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		map.put("pageSize", page.getPageSize());
		map.put("pageNum", page.getPageNum());
		return map;

	}


	/**
	 * 新增一个员工
	 * @param emp 员工
	 */
	public void save(Emp emp) {
		empMapper.insert(emp);
	}

	
	/**
	 * 更新员工信息
	 * @param emp  员工对象
	 */
	public void update(Emp emp) {
		
		empMapper.updateByPrimaryKey(emp);
	}

	/**
	 * 根据员工ids删除员工信息
	 * @param ids 员工ids
	 */
	@Transactional
	public void deleteByIds(Long[] ids) {
		empMapper.deleteByIds(ids);
	}

	/**
	 * 根据id获取员工详细信息
	 * @param id 员工id
	 * @return	EmpView对象
	 */
	public EmpView findEmpViewById(Long id) {
		return empMapper.findEmpViewById(id);
	}

}
