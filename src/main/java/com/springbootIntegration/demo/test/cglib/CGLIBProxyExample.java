package com.springbootIntegration.demo.test.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liukun
 * @description 代理类只需要实现MethodInterceptor类，
 * @date 2019/10/9
 */
public class CGLIBProxyExample implements MethodInterceptor {

    /**
     * 生成CGLIB代理对象
     *
     * @param cls——Class类
     * @return Class类的CGLIB代理对象
     */
    public Object getProxy(Class cls) {
        //CGLIB enhancer增强类对象
        Enhancer enhancer = new Enhancer();
        //设置增强类型，将cls作为超类，生成子类,子类就是代理类，父类就是被代理类
        enhancer.setSuperclass(cls);
        //定义代理逻辑对象为当前对象，要求当前对象实现MethodInterceptor
        //也就是说通过CglibProxyExample的对象来调用getProxy方法
        enhancer.setCallback(this);
        //生成并返回代理对象
        return enhancer.create();
    }

    /**
     * 代理逻辑方法
     *
     * @param proxy       代理对象
     * @param method      方法
     * @param args        参数
     * @param methodProxy 方法代理
     * @return 代理逻辑返回
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("在调用真实对象之前的服务");
        //CGLIB反射调用真实对象方法,也就是调用父类的方法
        Object obj = methodProxy.invokeSuper(proxy, args);
        System.out.println("在调用真实对象之后的服务");
        return obj;
    }
}
