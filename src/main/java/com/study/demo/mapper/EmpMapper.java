package com.study.demo.mapper;

import com.study.demo.entity.Emp;
import com.study.demo.entity.EmpQuery;
import com.study.demo.entity.view.EmpView;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 员工Mapper接口
 *
 * @author chen
 */
public interface EmpMapper extends Mapper<Emp> {

    /**
     * 员工表连接部门表和岗位表，分页查询员工详细信息列表
     *
     * @return 员工详细信息列表
     */
    public List<EmpView> findEmpJoinDeptAndJob(@Param("query") EmpQuery query);

    /**
     * 根据ids删除员工信息
     *
     * @param ids
     */
    public void deleteByIds(@Param("ids") Long[] ids);

    /**
     * 根据id获取员工详细信息
     *
     * @param id 员工id
     * @return EmpView对象
     */
    public EmpView findEmpViewById(Long id);
}
