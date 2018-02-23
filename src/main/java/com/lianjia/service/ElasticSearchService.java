package com.lianjia.service;

import com.lianjia.entity.view.EmpView;
import com.lianjia.repository.EmpViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chen on 2018/2/7.
 */
@Service
public class ElasticSearchService {
    @Autowired
    EmpViewRepository empViewRepository;

    /**
     * ES新增一个文档对象
     * @param empView
     */
    public void save(EmpView empView){
        EmpView empView1 = empViewRepository.save(empView);
    }

    /**
     * ES通过id查询一个对象
     * @param id
     * @return
     */
    public EmpView findById(Long id){
        EmpView empView = empViewRepository.findById(id);
        return empView;
    }

    /**
     *  通过部门id查询对象列表
     * @param deptId
     * @return
     */
    public List<EmpView> findByDeptId(Long deptId){
        return empViewRepository.findByDeptId(deptId);
    }

}
