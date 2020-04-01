package com.springbootIntegration.demo.test.JDKProxy;

/**
 * @author liukun
 * @description
 * @date 2019/10/9
 */
public class TestJDKProxy {
    public static void main(String[] args) {
        //因为是非静态方法所以需要一个实例来调用绑定方法
        JDKProxyExample jdk = new JDKProxyExample();
        //bind参数是真实的被代理的对象
        HelloWorld proxy = (HelloWorld)jdk.bind(new HelloWorldImpl());
        //当使用代理对象调用方法时
        //就会通过代理对象进入代理的逻辑方法invoke中
        //在invoke中利用了反射，使用真实对象去调用此方法
        proxy.sayHelloWorld();

        // 如果实现类实现了多个接口，则所有接口都将被代理
        HiWorld proxy1 = (HiWorld) proxy;
        proxy1.sayHi();
    }
}
