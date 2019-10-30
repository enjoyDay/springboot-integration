package com.springbootIntegration.demo.test.reflection;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liukun
 * @description 模仿spring的类，手写实现。
 * @date 2019/10/29
 * <p>
 * 主要功能就是加载xml文件，并对xml文件中的配置进行反射
 * <p>
 * 这个是基于xml文件配置进行实现，注解实现在其他类
 * 使用的技术DOM4j+java反射
 * 步骤：
 * 1、读取xml文件
 * 2、根据getBean中的类名找到对应xml文件中的id，以及class属性的值
 * 3、根据class属性值用反射创建对应的对象
 * 4、给对象的私有属性设值
 */
public class ClassPathXmlApplicationContext {
    //模拟容器用
    private Map<String, Object> map = new HashMap<>();
    private String xmlPath;

    public ClassPathXmlApplicationContext(String path) throws Exception {
        this.xmlPath = path;
        loadXmlProperty();
    }
    /**
     * 获取一个bean
     *
     * @param beanName bean的名字
     * @return 如果找到返回对应的对象，如果没有则返回null
     */
    public Object getBean(String beanName) throws Exception {
        if (StringUtils.isEmpty(beanName)) {
            //这里应该自定义一个异常类，然后抛异常
            throw new Exception("beanName不能传为空");
        }
        if (map.containsKey(beanName)) {
            return map.get(beanName);
        }
        throw new Exception("找不到此类");
    }

    /**
     * 加载xml配置文件
     */
    private void loadXmlProperty() throws Exception {
        //1、读取xml文件
        //1.1 以下是对xml文件的操作
        SAXReader saxReader = new SAXReader();
        //默认文件在resource包下
        URL resource = this.getClass().getClassLoader().getResource("user.xml");
        Document document = null;
        try {
            document = saxReader.read(resource);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //1.2 获取根节点beans
        Element rootElement = document.getRootElement();
        //1.3 获取根节点下的所有子节点bean（未知子元素的情况下使用elements(),已知情况下使用elementText()）
        List<Element> elements = rootElement.elements();
        //1.4 遍历所有子节点，利用反射技术创建对象，并添加到容器中
        for (Element element : elements) {
            //获取节点上的属性(这是在已知属性名的情况下，如果是未知属性名，使用attributes())
            String id = element.attributeValue("id");
            String aClass = element.attributeValue("class");
            if (StringUtils.isEmpty(id) || id.equals(aClass)) {
                throw new Exception("id或class属性不能为空");
            }
            //简单利用反射创建类
            Class<?> forName = null;
            try {
                forName = Class.forName(aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Constructor<?> declaredConstructor = null;
            try {
                declaredConstructor = forName.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            declaredConstructor.setAccessible(true);
            Object o = null;
            try {
                o = declaredConstructor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //获取各个属性
            Field[] declaredFields = forName.getDeclaredFields();

            //获取子节点的子节点property
            List<Element> childElements = element.elements();
            for (Element childElement : childElements) {
                String name = childElement.attributeValue("name");
                String value = childElement.attributeValue("value");
                //获取类指定的属性name
                Field declaredField = forName.getDeclaredField(name);
                declaredField.setAccessible(true);
                //获取这个属性的类型
                String name1= declaredField.getType().getName();
                if (name1.equals("int")) {
                    try {
                        declaredField.set(o, Integer.valueOf(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        declaredField.set(o, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
            map.put(id, o);
        }
    }
}
