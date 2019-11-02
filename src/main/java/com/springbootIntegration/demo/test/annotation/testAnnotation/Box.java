package com.springbootIntegration.demo.test.annotation.testAnnotation;

import com.springbootIntegration.demo.test.annotation.TestAutowired;
import com.springbootIntegration.demo.test.annotation.TestComponent;

/**
 * @author liukun
 * @description 被注解类，添加其他对象
 * @date 2019/11/2
 *
 * spring的思想是：？（不确定）
 * 第一次扫描包注入属性和创建bean的时候，不注入
 * 第二次当真正用的时候才给注入
 *
 * 另外一个思路：扫描两次包，先给独立的有注解的类创建，
 * 第二次再扫描包，给有引用的有注解的类注入
 */
@TestComponent
public class Box {
    @TestAutowired
    private AppleBox appleBox;
    @TestAutowired
    private BananaBox bananaBox;

    @Override
    public String toString() {
        return "Box{" +
                "appleBox=" + appleBox +
                ", bananaBox=" + bananaBox +
                '}';
    }
}
