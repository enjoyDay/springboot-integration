package com.springbootIntegration.demo.test.annotation.testAnnotation;

import com.springbootIntegration.demo.test.annotation.TestAutowired;
import com.springbootIntegration.demo.test.annotation.TestComponent;

/**
 * @author liukun
 * @description
 * @date 2019/11/2
 */
@TestComponent
public class MixBox {
    @TestAutowired
    private Box Box;

    @Override
    public String toString() {
        return "MixBox{" +
                "Box=" + Box +
                '}';
    }
}
