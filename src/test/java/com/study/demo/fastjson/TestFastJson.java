package com.study.demo.fastjson;

import com.alibaba.fastjson.JSON;
import com.study.demo.entity.Address;
import com.study.demo.entity.UserTest;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2018/2/27.
 */
public class TestFastJson {


    @Test
    public void testJson() {
        UserTest user = new UserTest();
        user.setId(1);
        user.setName("ricky");
        user.setAge(27);
        user.setBirthday(LocalDate.now());

        List<Address> addrList = new ArrayList<>();
        Address addr1 = new Address();
        addr1.setProvince("北京");
        addr1.setCity("北京市");
        addr1.setDistrict("朝阳区");
        addr1.setDetail("大望路金地中心");
        addrList.add(addr1);

        Address addr2 = new Address();
        addr2.setProvince("湖北省");
        addr2.setCity("武汉市");
        addr2.setDistrict("武昌区");
        addr2.setDetail("江汉路步行街");
        addrList.add(addr2);

        user.setAddrList(addrList);

        // bean转换为JsonString
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);
        // JsonString转Bean
        UserTest parseUser = JSON.parseObject(jsonString, UserTest.class);
        System.out.println(parseUser);


    }

}
