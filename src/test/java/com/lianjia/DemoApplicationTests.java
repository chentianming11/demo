package com.lianjia;

import com.lianjia.entity.Dept;
import com.lianjia.entity.Emp;
import com.lianjia.entity.EmpQuery;
import com.lianjia.entity.view.EmpView;
import com.lianjia.mapper.DeptMapper;
import com.lianjia.mapper.EmpMapper;
import com.lianjia.repository.EmpViewRepository;
import com.lianjia.service.ElasticSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	ElasticSearchService elasticSearchService;

	@Autowired
	EmpViewRepository empViewRepository;

	@Autowired
	EmpMapper empMapper;

	@Test
	public void testSelectAll(){
		List<Dept> depts = deptMapper.selectAll();
		for (Dept dept : depts) {
			System.out.println(dept);
			
		}
	}

	@Test
	public void testES(){
		EmpView empView = new EmpView();
		empView.setId(1L);
		empView.setDeptId(1L);
		empView.setDeptName("号发货");
		empView.setJobName("鹅肉");
		empView.setJobId(1L);
		empView.setName("haha");
		empView.setHireDate(new Date());
		empView.setStatus(1);
		elasticSearchService.save(empView);
	}

	@Test
	public void test3(){
		EmpQuery empQuery = new EmpQuery();
		empQuery.setPageNum(1);
		empQuery.setPageSize(100);
		List<EmpView> empViewList = empMapper.findEmpJoinDeptAndJob(empQuery);
		empViewRepository.save(empViewList);
	}

	/**
	 * 测试java8中将list转为map
	 */
	@Test
	public void test4(){
		EmpQuery empQuery = new EmpQuery();
		empQuery.setPageNum(1);
		empQuery.setPageSize(50);
		List<EmpView> empViewList = empMapper.findEmpJoinDeptAndJob(empQuery);
		Map<Long, String> map = empViewList.stream().collect(Collectors.toMap(Emp::getId, Emp::getName));
		System.out.println(map);
	}

}
