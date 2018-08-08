package com.study.demo.service.blog;

import com.study.demo.entity.blog.BlogArticle;
import com.study.demo.entity.blog.BlogCollection;
import com.study.demo.entity.blog.BlogUser;
import com.study.demo.mapper.blog.BlogArticleMapper;
import com.study.demo.mapper.blog.BlogCollectionMapper;
import com.study.demo.mapper.blog.BlogUserMapper;
import com.study.demo.util.AppAssert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    private static final List<String> HEAD_LIST = new ArrayList<>();

    static {
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/6467455/452f4532-4e3d-45fe-a1c5-da4d7cf5ad6f.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/5545154/1a3d5ed6-9ad1-44b3-9457-3e6a29dd1b71.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/2153206/4b7d9e0d-2e6b-4757-b5a9-a76737c5aeff.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/3627484/eb973bb9-37ba-4d07-acec-850c0a66d1bb.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/6305091/b1912e7b-20d1-41a6-94f3-5a0d1c81fc5a.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/607979/0da6e559-d2bf-45e6-86d7-ad6fd9a0f39b.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/4287007/b7b9c810-069e-4385-aec7-1823e94ee43d.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/22609/11963c7e-2407-4b7d-bae1-8ff6797de722.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/11557689/ed2ea7f3-9d7d-41fc-a05a-daebb8097b0c.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/3678149/b8a58e70-1126-48c9-97e2-8f21a31dfa94.png?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/8781442/adac7350-1979-4076-add4-581969676519.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/10849033/a3e12f47-c81c-4659-9c80-117f8b337408.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");

        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/4802366/4f86b75d-b61b-4126-8be4-87a151c9cd28.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/2998364/9f8351c3734b.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/11740279/5841dba1-205f-433e-a105-b3feb5981abd.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
        HEAD_LIST.add("https://upload.jianshu.io/users/upload_avatars/2558050/2f4caa01b3ba.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180");
    }

    public static void main(String[] args) {

        for (int a = 0; a < 10; a++) {
            int i = new Random().nextInt(10);
            System.out.println(i);
        }
    }

    public BlogUser login(BlogUser blogUser) {

        BlogUser loginUser = blogUserMapper.selectOne(blogUser);
        AppAssert.notNull(loginUser, "用户名或密码错误");
        return loginUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(BlogUser blogUser) {
        String headUrl = HEAD_LIST.get(new Random().nextInt(HEAD_LIST.size()));
        blogUser.setHeadUrl(headUrl);
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

    public List<Map> getArticleList(Integer pageNo, Integer userId) {

        Map<String, Object> params = new HashedMap();
        params.put("userId", userId);

        List<Map> list = blogArticleMapper.getAllArticle(params);

        return list;
    }

    public List<BlogUser> getUserList() {
        List<BlogUser> userList = blogUserMapper.selectAll();
        return userList;
    }

    public BlogUser getUserInfo(Integer userId) {
        return blogUserMapper.selectByPrimaryKey(userId);
    }
}
