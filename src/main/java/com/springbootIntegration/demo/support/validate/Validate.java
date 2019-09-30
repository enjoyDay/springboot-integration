package com.springbootIntegration.demo.support.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用方法：在属性或者参数中加@Validate(type="")这样
 * @author liukun
 * @description 校验注解类
 * @date 2019/9/14
 */
//一般注解类加这两个就够了
@Target({ElementType.FIELD,ElementType.PARAMETER})//注解应用范围
@Retention(RetentionPolicy.RUNTIME)//运用时期
public @interface Validate {
    //是否可为空
    boolean nullable() default true;
    //最大值
    double max() default 0.0D;
    //最小值
    double min() default 0.0D;
    //最大长度
    int maxLength() default 0;
    //最小长度
    int minLength() default 0;
    //正则类的类型,必须是自定义的那些类型
    RegexType type() default RegexType.NONE;

    //正则匹配表达式
    String regex() default "";

    //描述
    String desc() default "";
}
