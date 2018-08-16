package com.study.demo.mapper.blog;

import com.study.demo.entity.blog.BlogArticle;
import com.study.demo.util.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/8/5
 */
public interface BlogArticleMapper extends MyMapper<BlogArticle> {

    List<Map> getAllArticle(Map<String, Object> params);

    Map<String,Object> getArticleDetail(Integer articleId);
}
