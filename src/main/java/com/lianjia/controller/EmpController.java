package com.lianjia.controller;

import static com.google.common.base.Preconditions.*;
import com.lianjia.entity.Emp;
import com.lianjia.entity.EmpQuery;
import com.lianjia.entity.view.EmpView;
import com.lianjia.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
@CrossOrigin
public class EmpController {

    @GetMapping("/emp/detail/{id}")
    public EmpView findById(@PathVariable("id") Long id) {

        return empService.findEmpViewById(id);

    }

    @Autowired
    private EmpService empService;

    @GetMapping(value = "/emp")
    public Map<String, Object> findByPage(EmpQuery empQuery) {
        if (empQuery.getPageNum() == null) {
            empQuery.setPageNum(1);
        }
        if (empQuery.getPageSize() == null) {
            empQuery.setPageSize(5);
        }
        return empService.findByPage(empQuery);


    }

    @PostMapping("/emp")
    public void save(@RequestBody Emp emp) {
        empService.save(emp);
    }
    @PostMapping("/emp/test")
    public void saveTest(Emp emp) {
        System.out.println(emp);
    }




    @PutMapping("/emp")
    public void update(@RequestBody Emp emp) {
        empService.update(emp);

    }

    @DeleteMapping("/emp")
    public void delete(@RequestParam("ids") Long[] ids) {

        empService.deleteByIds(checkNotNull(ids, "ids不能为空"));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

}
