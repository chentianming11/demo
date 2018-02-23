package com.lianjia.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.lianjia.entity.Emp;
import com.lianjia.entity.EmpQuery;
import com.lianjia.entity.view.EmpView;
import com.lianjia.mapper.EmpMapper;
import com.lianjia.repository.EmpViewRepository;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EmpService服务类
 */
@Service
public class EmpService {
	
	@Autowired
	private EmpMapper empMapper;
	@Autowired
	EmpViewRepository empViewRepository;

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
	 * 分页查询用户列表通（ElasticSerach）
	 * @return 分页结果信息
	 */
	public Map<String, Object> findByPageWithES(EmpQuery query) {

		Pageable pageable = new PageRequest(query.getPageNum(), query.getPageSize());


		QueryBuilder queryBuilder = new TermQueryBuilder("status",query.getStatus());

		Page<EmpView> page = empViewRepository.search(queryBuilder, pageable);

		Map map = new HashMap<String, Object>();
		map.put("pageNum",page.getNumber());
		map.put("pageSize",page.getSize());
		map.put("total",page.getTotalElements());
		map.put("pages",page.getTotalPages());
		map.put("emps",page.getContent());

		return	map;
	}

	/**
	 * 新增一个员工
	 * @param emp 员工
	 */
	public void save(Emp emp) {
		empMapper.insert(emp);
		System.out.println(emp.getId());
		// 新增员工的同时，查询员工详细信息，添加到索引库
		EmpView empView = empMapper.findEmpViewById(emp.getId());
		empViewRepository.save(empView);
	}

	
	/**
	 * 更新员工信息
	 * @param emp  员工对象
	 */
	public void update(Emp emp) {
		
		empMapper.updateByPrimaryKey(emp);
		EmpView empView = empMapper.findEmpViewById(emp.getId());
		empViewRepository.save(empView);
	}

	/**
	 * 根据员工ids删除员工信息
	 * @param ids 员工ids
	 */
	@Transactional
	public void deleteByIds(Long[] ids) {
		empMapper.deleteByIds(ids);
		for (Long id : ids) {
			empViewRepository.delete(id);
		}
	}

	/**
	 * 根据id获取员工详细信息
	 * @param id 员工id
	 * @return	EmpView对象
	 */
	public EmpView findEmpViewById(Long id) {
		EmpView empView = empViewRepository.findById(id);
		System.out.println("从ES中查询员工信息" + empView);
		return empView;
	}

}
