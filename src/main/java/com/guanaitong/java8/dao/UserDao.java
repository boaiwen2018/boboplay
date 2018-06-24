package com.guanaitong.java8.dao;

import com.guanaitong.java8.model.User;

import java.util.Arrays;
import java.util.List;

public class UserDao {

    public List<User> getAllUser(){

        User user1 = new User();
        user1.setId(1);
        user1.setName("李晓博");
        user1.setSex("男");
        user1.setAge(28);

        User user2 = new User();
        user2.setId(2);
        user2.setName("波波");
        user2.setSex("男");
        user2.setAge(30);

        User user3 = new User();
        user3.setId(3);
        user3.setName("博博");
        user3.setSex("女");
        user3.setAge(18);

        return Arrays.asList(user1,user2,user3);
    }
}
