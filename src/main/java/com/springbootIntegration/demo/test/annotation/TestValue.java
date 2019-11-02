package com.springbootIntegration.demo.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liukun
 * @description 模拟spring@Value，注解在属性上，用于设置值
 * @date 2019/11/1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestValue {
    String value();
}
