package com.springbootIntegration.demo.test.annotation.testAnnotation;

import com.springbootIntegration.demo.test.annotation.TestComponent;
import com.springbootIntegration.demo.test.annotation.TestMethod;
import com.springbootIntegration.demo.test.annotation.TestNum;
import com.springbootIntegration.demo.test.annotation.TestValue;

/**
 * @author liukun
 * @description 被注解类
 * @date 2019/11/1
 */
@TestComponent
public class AppleBox {

    @TestNum(value=3)
    private int num;

    @TestValue("${apple.name}")
    private String name;

    @TestMethod
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppleBox{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
