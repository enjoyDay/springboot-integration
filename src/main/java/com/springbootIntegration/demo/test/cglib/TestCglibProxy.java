package com.springbootIntegration.demo.test.cglib;

/**
 * @author liukun
 * @description
 * @date 2019/10/9
 */
public class TestCglibProxy {
    public static void main(String[] args) {
        CGLIBProxyExample cglibProxyExample = new CGLIBProxyExample();
        HelloWorldImpl proxy = (HelloWorldImpl)cglibProxyExample.getProxy( HelloWorldImpl.class);
        proxy.sayHello("cetc");
        System.out.println("--------------------------");
        new HelloWorldImpl().sayHello("java");
    }
}
