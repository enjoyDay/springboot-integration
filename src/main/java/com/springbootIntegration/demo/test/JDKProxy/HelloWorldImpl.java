package com.springbootIntegration.demo.test.JDKProxy;

/**
 * @author liukun
 * @description 实现接口的子类
 * @date 2019/10/9
 */
public class HelloWorldImpl implements HelloWorld {

    @Override
    public void sayHelloWorld() {
        System.out.println("hello world");
    }
}
