package com.springbootIntegration.demo.test.reflection;

import lombok.Data;

/**
 * @author liukun
 * @description
 * @date 2019/10/29
 */
@Data
public class User {
    public int id = 1;

    private String username;
    private int age;
    private String address;

    private User() {
    }

    public User(String username, int age, String address) {
        this.username = username;
        this.age = age;
        this.address = address;
        System.out.println("有参构造函数被调用");
    }
}
