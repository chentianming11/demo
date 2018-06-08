package com.lianjia.util;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

public class MySelectProvider extends MapperTemplate {

    public MySelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 忽略id列全部插入
     * @param ms
     * @return
     */
    public String insertAllSkipId(MappedStatement ms) {
        return insertAll(ms, true);
    }

    /**
     * 不忽略id列全部插入
     * @param ms
     * @return
     */
    public String insertAllNotSkipId(MappedStatement ms) {
        return insertAll(ms, false);
    }


    private String insertAll(MappedStatement ms, boolean skipId) {
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, skipId, false, isNotEmpty()));
        sql.append("VALUES ");
        sql.append("<foreach collection=\"collection\" item=\"item\" separator=\",\" > ");
        sql.append(SqlHelper.insertValuesColumns(entityClass, skipId, false, isNotEmpty()).replaceAll("VALUES", ""));
        sql.append(" </foreach>");
        String s = sql.toString().replaceAll("(#)(\\{)", "#{item.")
                .replaceAll("test=\"", "test=\"item.");
        return s;
    }
}