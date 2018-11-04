package com.study.demo.util;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * @author 陈添明
 */
public class MySelectProvider extends MapperTemplate {

    public MySelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


    /**
     * 全量插入
     * @param ms
     * @return
     */
    public String insertAll(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        boolean skipId = true;
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, skipId, false, isNotEmpty()));
        sql.append("VALUES  ");
        sql.append("<foreach collection=\"collection\" item=\"item\" separator=\",\" > ");
        sql.append(MySqlHelper.insertValuesColumns(entityClass, skipId));
        sql.append(" </foreach>");
        String s = sql.toString().replaceAll("#\\{", "#{item.")
                .replaceAll("test=\"", "test=\"item.");
        return s;
    }
}