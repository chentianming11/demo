package com.study.demo.service.blog;

import com.study.demo.entity.blog.BlogArticle;
import com.study.demo.entity.blog.BlogCollection;
import com.study.demo.entity.blog.BlogUser;
import com.study.demo.mapper.blog.BlogArticleMapper;
import com.study.demo.mapper.blog.BlogCollectionMapper;
import com.study.demo.mapper.blog.BlogUserMapper;
import com.study.demo.util.AppAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/8/5
 */
@Slf4j
@Service
public class BlogService {

    @Autowired
    BlogUserMapper blogUserMapper;
    @Autowired
    BlogCollectionMapper blogCollectionMapper;
    @Autowired
    BlogArticleMapper blogArticleMapper;

    public BlogUser login(BlogUser blogUser) {

        BlogUser loginUser = blogUserMapper.selectOne(blogUser);
        AppAssert.notNull(loginUser, "用户名或密码错误");
        return loginUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(BlogUser blogUser) {
        blogUser.setHeadUrl("http://upload.jianshu.io/users/upload_avatars/11323154/7e0ec3ec-0725-4672-907b-a6b9a4a72ee7.jpg");
        blogUser.setCreateTime(new Date());
        blogUserMapper.insert(blogUser);
        blogCollectionMapper.insert(BlogCollection.builder()
                .createTime(new Date())
                .userId(blogUser.getId())
                .name("默认文集")
                .description("默认文集")
                .build());

    }

    public BlogUser autoLogin(Integer loginId) {
        BlogUser blogUser = blogUserMapper.selectByPrimaryKey(loginId);
        AppAssert.notNull(blogUser, "自动登陆失败");
        return blogUser;
    }

    public List<BlogCollection> getCollectionByUserId(Integer userId) {
        return blogCollectionMapper.select(BlogCollection.builder().userId(userId).build());
    }

    public void addCollection(BlogCollection blogCollection) {
        blogCollectionMapper.insert(blogCollection);
    }

    public void addArticle(BlogArticle blogArticle) {
        blogArticle.setCreateTime(new Date());
        blogArticleMapper.insert(blogArticle);
    }

    public List<Map> getAllArticle(Integer pageNo) {
        List<Map> list = blogArticleMapper.getAllArticle();

        return list;
    }
}
