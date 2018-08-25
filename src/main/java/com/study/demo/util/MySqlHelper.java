package com.study.demo.util;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Set;

/**
 * @author 陈添明
 * @date 2018/8/25
 */
public class MySqlHelper {

    /**
     * insert-values()列
     *
     * @param entityClass
     * @param skipId      是否从列中忽略id类型
     * @return
     */
    public static String insertValuesColumns(Class<?> entityClass, boolean skipId) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isId()) {
                continue;
            }
            sql.append("IFNULL(");
            sql.append(column.getColumnHolder() + ",");
            sql.append("DEFAULT(" + column.getColumn() +  ")),");
        }
        sql.append("</trim>");
        return sql.toString();
    }

}
