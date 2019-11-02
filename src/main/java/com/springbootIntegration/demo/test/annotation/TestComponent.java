package com.springbootIntegration.demo.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liukun
 * @description 模拟spring@Componnent注解类，注解在类上，可以自动生成对象
 * @date 2019/11/1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestComponent {
}
