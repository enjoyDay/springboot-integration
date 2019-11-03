package com.springbootIntegration.demo.test.annotation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liukun
 * @description 模仿spring用于扫描注解配置类，并存放到上下文中
 * @date 2019/11/1
 * <p>
 * 步骤：
 * 1、扫描指定包下的注解类
 * 2、查看类，方法，属性上是否有注解
 * 3、加载properties配置文件，给注解属性添加值
 * 4、将创建的bean添加到上下文中
 */
public class AnnotationConfigApplicationContext {
    private Map<String, Object> map = new HashMap<>();
    private Properties properties;

    public AnnotationConfigApplicationContext(String packageName) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        properties = loadProperties();
        //第一次加载，是为了单纯给注解类进行创建
        scanPackage(packageName);
        //第二次加载，是为了给某些注解类依赖其他对象进行注入
        scanPackage(packageName);
    }

    public Object getBean(String beanName) throws Exception {
        if (beanName.equals("") || beanName == null) {
            throw new Exception("传进的beanName为null或空，请检查");
        }
        Object o = map.get(beanName);
        if (o == null) {
            throw new Exception("你查找的类没能自动创建，请检查传入参数是否正确或者该类是否添加正确注解");
        }
        return o;
    }

    /**
     * 加载配置文件，默认是annotation.properties
     *
     * @return 加载成功返回配置文件properties
     */
    private Properties loadProperties() throws IOException {
        //加载配置文件properties
        Properties properties = new Properties();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("annotation.properties");
        properties.load(resourceAsStream);
        return properties;
    }

    /**
     * 扫描包下面的类
     *
     * @param packageName 包名
     */
    private void scanPackage(String packageName) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, ClassNotFoundException {
        //扫描指定包下面的类
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if (url != null) {
                String protocol = url.getProtocol();
                if (protocol.equals("file")) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    //对指定路径下的类进行反射
                    addClass(packagePath, packageName);
                } else {
                    //如果不是文件，是文件的话，可能是jar包，目前不支持
                    System.out.println("到这了");
                }
            }
        }
    }

    /**
     * 根据路径和包名，加载类
     *
     * @param packagePath 路径名
     * @param packageName 包名
     */
    private void addClass(String packagePath, String packageName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            //过滤，将class文件和文件夹过滤出来
            @Override
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
            }
        });
        for (File file : files) {
            //获取的文件名
            String fileName = file.getName();
            //如果是文件
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (packageName != null && packageName.trim() != "") {
                    className = packageName + "." + className;
                }
                //反射出类
                doAddClass(className);
            } else {
                //如果是文件夹
                String subPackagePath = fileName;
                if (packagePath != null && packagePath != "") {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackgeName = fileName;
                if (packageName != null && packageName != "") {
                    subPackgeName = packageName + "." + fileName;
                }
                //递归调用
                addClass(subPackagePath, subPackgeName);
            }
        }
    }

    /**
     * 依据注解，来反射类
     *
     * @param className 完整类名
     */
    private void doAddClass(String className) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        //用类名当做map的key
        String key = className.substring(className.lastIndexOf(".") + 1);
        //根据反射出的clazz来扫描类中的注解
        //1、类上的
        getClassAnnotation(clazz, key);
        //2、属性上的,前提第一步要成功，即要成功创建对象
        getFieldAnnotation(clazz, key);
        //3、方法上的，前提第一步要成功，即要成功创建对象
        getMethodAnnotation(clazz, key);
    }

    private void getClassAnnotation(Class<?> clazz, String key) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (clazz != null && clazz.isAnnotationPresent(TestComponent.class)) {
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            Object o = declaredConstructor.newInstance();

            map.put(key, o);
        }
    }

    private void getMethodAnnotation(Class<?> clazz, String key) throws IllegalAccessException, InvocationTargetException {
        if (map.containsKey(key)) {
            //获取clazz方法
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(TestMethod.class)) {
                    TestMethod annotation = method.getAnnotation(TestMethod.class);
                    String value = annotation.value();
                    method.setAccessible(true);
                    int parameterCount = method.getParameterCount();
                    if (parameterCount == 0) {
                        Object result = method.invoke(map.get(key), null);
                        System.out.println("方法执行的结果："+result);
                        System.out.println("反射获取方法的注解值："+value);
                    }
                }
            }
        }
    }

    private void getFieldAnnotation(Class<?> clazz, String key) throws IllegalAccessException {
        if (map.containsKey(key)) {
            //获取clazz属性
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                //如果是TestNum注解，则获取注解值，并反射到对象中的属性中
                if (field.isAnnotationPresent(TestNum.class)) {
                    TestNum annotation = field.getAnnotation(TestNum.class);
                    int value = annotation.value();
                    field.setAccessible(true);
                    field.set(map.get(key), value);
                }
                //如果是TestValue注解，要先到配置文件查找是不是配置过
                if (field.isAnnotationPresent(TestValue.class)) {
                    TestValue annotation = field.getAnnotation(TestValue.class);
                    String value = annotation.value();
                    if (value.startsWith("${") && value.endsWith("}")) {
                        String actualValue = value.substring(value.indexOf("{") + 1, value.lastIndexOf("}"));
                        String property = properties.getProperty(actualValue);
                        if (property != null && property != "") {
                            field.setAccessible(true);
                            field.set(map.get(key), property);
                        }
                    } else {
                        field.setAccessible(true);
                        field.set(map.get(key), value);
                    }
                }
                //如果是@TestAutoWired，则需要查找map中是不是存在其他引用，如果不存在，下载再加载
                if (field.isAnnotationPresent(TestAutowired.class)) {
                    //属性类型
                    Class<?> type = field.getType();
                    String name = type.getName();
                    name = name.substring(name.lastIndexOf(".") + 1);
                    if (map.containsKey(name)) {
                        field.setAccessible(true);
                        field.set(map.get(key), map.get(name));
                    }
                }
            }
        }
    }

}
