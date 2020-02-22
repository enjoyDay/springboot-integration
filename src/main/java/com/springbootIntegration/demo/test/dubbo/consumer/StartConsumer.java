package com.springbootIntegration.demo.test.dubbo.consumer;

import com.springbootIntegration.demo.test.dubbo.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liukun
 * @description 消费端调用
 * @date 2020/2/22
 */
public class StartConsumer {
    public static void addOrder() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/dubbo/consumer.xml");
        applicationContext.start();
        System.out.println("###order服務,开始调用会员服务");
        UserService userService=(UserService) applicationContext.getBean("userService");
        String userName = userService.getUser(1L);
        System.out.println("###order服務,结束调用会员服务,userName:" + userName);
    }
    public static void main(String[] args) {
        addOrder();
    }
}
