package com.springbootIntegration.demo.test.reflection;

import io.swagger.models.auth.In;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liukun
 * @description 使用Class进行反射创建对象
 * @date 2019/10/29
 */
public class UseClassReflection {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
//        refletionNoConstructorParams();
//        refletionConstructorParams();
        setFieldtoPrivateParams();
    }

    /**
     * 无参构造方法反射对象
     *
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static void refletionNoConstructorParams() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //1、使用java的反射机制创建对象
        Class<?> forName = Class.forName("com.springbootIntegration.demo.test.reflection.User");
        //2、使用默认无参构造函数来创建对象,如果类的构造方法是私有的，抛非法访问异常
        User user = (User) forName.newInstance();
        //3、通过前面创建的对象，可以设置一些属性
        user.setUsername("哈哈");
        System.out.println(user);
    }

    private static void refletionConstructorParams() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> forName = Class.forName("com.springbootIntegration.demo.test.reflection.User");
        //获取构造函数,参数就是需要传进构造方法中的类型
        Constructor<?> constructor = forName.getConstructor(String.class, int.class, String.class);
        //通过构造方法来创建实例
        User user = (User) constructor.newInstance("嘻嘻", 12, "山东省");
        System.out.println(user);
    }

    /**
     * 为类的私有属性设值
     */
    private static void setFieldtoPrivateParams() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        //加载类，生成class类
        Class<?> forName = Class.forName("com.springbootIntegration.demo.test.reflection.User");
        //获取类的全限定类名，也就是加载的类
        System.out.println(forName.getName());
        System.out.println("---------------------------------");
        //获取所有的属性
        Field[] declaredFields = forName.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("变量名为："+field.getName()+"，类型为："+field.getType());
        }
        System.out.println("---------------------------------");
        //获取当前所有方法
        Method[] declaredMethods = forName.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println("方法名为："+method.getName()+",返回类型为："+method.getReturnType()+",参数个数为："+method.getParameterCount());
        }
        System.out.println("---------------------------------");
        //初始化对象
        Constructor<?> constructor = forName.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        User user = (User)constructor.newInstance();
//        User user  = (User)forName.newInstance();
        //给public的属性设值
        Field id = forName.getDeclaredField("id");
        id.setAccessible(true);
        id.set(user,2);
        //给private的属性设值
        Field username = forName.getDeclaredField("username");
        username.setAccessible(true);
        username.set(user,"六六");
        System.out.println(user);
    }
}
