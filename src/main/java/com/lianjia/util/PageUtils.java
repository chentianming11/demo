package com.lianjia.util;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.*;

/**
 * 分页工具类
 *
 * @author chenTianMing
 * @date 2018/4/11
 */
public class PageUtils {

    /**
     * 每页显示数量
     */
    public static final Integer PAGE_SIZE = 2;

    /**
     * 封装分页查询结果
     * @param pageNum
     * @param pageSize
     * @param total
     * @param results
     * @return
     */
    public static Map<String, Object> packagingResult(Integer pageNum, Integer pageSize, Integer total, List results) {
        // 计算总页数
        Double pages = Math.ceil((double) total / pageSize);
        // 封装查询结果
        Map<String, Object> result = new HashMap<>();
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("total", total);
        result.put("pages", pages.intValue());
        result.put("results", results);
        return result;
    }


    /**
     * 获取员工状态信息集合
     * @param empStatus
     * @return
     */
    public static List<String> getEmpStatusList(String empStatus) {
        List<String> empStatusList = null;
        if (Objects.equals("在职", empStatus)) {
            empStatusList = ImmutableList.of("试用期", "正式", "申请离职");
        } else if (Objects.equals("离职", empStatus)) {
            empStatusList = ImmutableList.of("离职");
        }
        return empStatusList;
    }

    /**
     * 将关键字转换成一个整数
     * @param keyword
     * @return  整数或者null
     */
    public static Integer parseInteger(String keyword) {
        Integer number = null;
        if(StringUtils.isNumeric(keyword)){
            number = Integer.valueOf(keyword);
        }
        return number;
    }

    /**
     * 获取一天的最后时刻
     */
    public static Date getLastMoment(Date date){
        if (date == null){
            return null;
        }
        Date date1 = DateUtils.addDays(date, 1);
        return DateUtils.addMilliseconds(date1, -2);
    }

}
