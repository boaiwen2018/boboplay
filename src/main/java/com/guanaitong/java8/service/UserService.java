package com.guanaitong.java8.service;


import com.guanaitong.java8.custom.collector.MyCollectors;
import com.guanaitong.java8.dao.UserDao;
import com.guanaitong.java8.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {

    private UserDao userDao = new UserDao();


    /**
     * 获取所有员工信息
     * @return
     */
    public List<User> getAllUser(){
        return userDao.getAllUser();
    }


    /**
     * 获取所有员工的名字
     * @return
     */
    public List<String> getAllUserName(){
        List<User> userList = userDao.getAllUser();
        return userList.stream().map(User::getName).collect(Collectors.toList());
    }


    /**
     * 是否存在女员工
     */
    public boolean hasFemaleInAllUser(){
        List<User> userList = userDao.getAllUser();
        return userList.stream().anyMatch(user->"女".equals(user.getSex()));
    }


    /**
     * 获取所有男员工的名字
     */
    public List<String> getAllMaleUserName(){
        List<User> userList = userDao.getAllUser();
        return userList.stream().filter(user->"男".equals(user.getSex())).map(User::getName).collect(Collectors.toList());
    }

    /**
     * 将所有员工得姓名前加上企业名称前缀 (关爱通_) 后返回员工姓名列表
     */
    public List<String> getUserNameForAddEnterpriseName(){
        List<User> userList = userDao.getAllUser();
        userList.forEach(user->user.setName("关爱通_"+user.getName()));
        //这边也可以直接在map里写改名字得逻辑
        return userList.stream().map(User::getName).collect(Collectors.toList());
    }


    /**
     * 统计所有员工中 男员工与女员工的总数
     */
    public Map<String,Long> getCountBySex(){
        List<User> userList = userDao.getAllUser();
        Map<String,Long> map = userList.stream().collect(
                Collectors.groupingBy(
                        User::getSex, Collectors.counting()
                )
        );
        return map;
    }


    /**
     * 获取年龄最大的员工 如果年龄相同则按姓名拼音正序排列
     * @return
     */
    public List<User> getUserOrderByAgeDesc(){
        List<User> userList = userDao.getAllUser();
        List<User> sortList= userList.stream().sorted((x,y) -> {
            if(x.getAge().equals(y.getAge())) {
                return y.getName().compareTo(x.getName());
            }else {
                return y.getAge().compareTo(x.getAge());
            }
        }).collect(Collectors.toList());
        return sortList;
    }


    /**
     * 将所有员工按个数分组
     * @param number
     * @return
     */
    public List<List<User>> groupByNumberForAllUser(int number){
        List<User> userList = userDao.getAllUser();
        return userList.stream().collect(MyCollectors.groupByNumber(number));
    }


}
