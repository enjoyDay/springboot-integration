package com.springbootIntegration.demo.test.reflection;

/**
 * @author liukun
 * @description 手写springioc的简单实现
 * @date 2019/10/29
 */
public class SpringIOCTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("user.xml");
        User user = (User) classPathXmlApplicationContext.getBean("user");
        System.out.println(user);
        Role role = (Role) classPathXmlApplicationContext.getBean("role");
        System.out.println(role);
    }
}
