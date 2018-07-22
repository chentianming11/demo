package com.study.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final Integer PAGE_SIZE = 20;

    /**
     * 封装分页查询结果
     *
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


}
