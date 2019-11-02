package com.springbootIntegration.demo.test.annotation.testAnnotation;

import com.springbootIntegration.demo.test.annotation.TestComponent;
import com.springbootIntegration.demo.test.annotation.TestNum;
import com.springbootIntegration.demo.test.annotation.TestValue;

/**
 * @author liukun
 * @description 测试注解
 * @date 2019/11/2
 */
@TestComponent
public class BananaBox {
    private String name = "banana";

    @TestNum(value = 1)
    private int num=2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "BananaBox{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
