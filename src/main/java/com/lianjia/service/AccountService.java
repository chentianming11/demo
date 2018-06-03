package com.lianjia.service;

import com.lianjia.entity.Account;
import com.lianjia.mapper.AccountMapper;
import com.lianjia.util.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenTianMing on 2018/6/2.
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountMapper accountMapper;


    public Account selectByUsernameAndPassword(String username, String password) {
        return accountMapper.selectOne(Account.builder().username(username).password(password).build());
    }

    public Account selectById(Integer id) {

        return accountMapper.selectByPrimaryKey(id);
    }
}
