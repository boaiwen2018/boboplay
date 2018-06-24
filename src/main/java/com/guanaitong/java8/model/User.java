package com.guanaitong.java8.model;


import lombok.Data;

@Data
public class User {

    /**
     * id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 为了方便使用直接用string
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

}
