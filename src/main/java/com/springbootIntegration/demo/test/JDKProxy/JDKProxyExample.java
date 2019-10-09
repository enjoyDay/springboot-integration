package com.springbootIntegration.demo.test.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liukun
 * @description 代理逻辑类
 * @date 2019/10/9
 */
public class JDKProxyExample implements InvocationHandler {

    // 真实的对象
    private Object target = null;

    /**
     * 建立代理对象和真实对象的代理关系，并返回代理对象
     *
     * @param target 真实对象
     * @return 代理对象
     */
    public Object bind(Object target) {
        //首先使用类的属性target保存真实对象
        this.target = target;
        //三个参数，分别是
        //类加载器，
        //把生成的动态代理对象下挂在哪些接口下，因此没有接口的类就无法实现jdk动态代理
        //定义实现方法逻辑的代理类，this表示当前对象，它必须实现InvocationHandler的invoke方法，是代理逻辑方法的现实方法
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 代理方法逻辑
     *
     * @param proxy  代理对象，上面的bind方法生成的对象
     * @param method 当前调度方法
     * @param args   当前方法参数
     * @return 代理结果返回
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在调用真实对象之前的服务");
        //这一步相当于使用target去调用这个方法，参实就是后面的这个参数,如果得到了结果，就是obj
        Object obj = method.invoke(target, args);
        System.out.println("在调用真实对象之后的服务");
        return obj;
    }
}
