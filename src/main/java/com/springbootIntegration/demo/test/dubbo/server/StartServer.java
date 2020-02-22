package com.springbootIntegration.demo.test.dubbo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author liukun
 * @description 服务端启动
 * @date 2020/2/22
 */
public class StartServer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/dubbo/provider.xml");
        applicationContext.start();
        System.out.println("会员服务启动成功...");
        System.in.read();
    }

}
