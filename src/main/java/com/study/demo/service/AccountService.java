package com.study.demo.service;

import com.study.demo.entity.Account;
import com.study.demo.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author chen.tian.ming
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountMapper accountMapper;


    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: 陈添明
     * @Date: 2018/6/23
     */

    public Account selectByUsernameAndPassword(String username, String password) {
        return accountMapper.selectOne(Account.builder().username(username).password(password).build());
    }

    public Account selectById(Integer id) {

        return accountMapper.selectByPrimaryKey(id);
    }
}
