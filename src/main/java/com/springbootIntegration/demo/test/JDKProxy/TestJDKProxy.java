package com.springbootIntegration.demo.test.JDKProxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author liukun
 * @description
 * @date 2019/10/9
 */
public class TestJDKProxy {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String properties = System.getProperty("user.dir");
        //因为是非静态方法所以需要一个实例来调用绑定方法
        JDKProxyExample jdk = new JDKProxyExample();
        //bind参数是真实的被代理的对象
        HelloWorld helloWorld = new HelloWorldImpl();
        HelloWorld proxy = (HelloWorld)jdk.bind(helloWorld);
        //当使用代理对象调用方法时
        //就会通过代理对象进入代理的逻辑方法invoke中
        //在invoke中利用了反射，使用真实对象去调用此方法
        proxy.sayHelloWorld();

        // 如果实现类实现了多个接口，则所有接口都将被代理
        HiWorld proxy1 = (HiWorld) proxy;
        proxy1.sayHi();

        // 这里我们将jdk生成的代理类输出了出来，方便后面分析使用
//        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{helloWorld.getClass()});
//        FileOutputStream os = new FileOutputStream(properties+"/src/main/java/com/springbootIntegration/demo/test/JDKProxy/"+"Proxy0.class");
//        os.write(bytes);
//        os.close();
    }
}
