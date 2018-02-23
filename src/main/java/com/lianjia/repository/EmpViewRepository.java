package com.lianjia.repository;

import com.lianjia.entity.view.EmpView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by chen on 2018/2/7.
 */
public interface EmpViewRepository extends ElasticsearchRepository<EmpView, Long> {

    EmpView findById(Long id);

    List<EmpView> findByDeptId(Long deptId);

}
