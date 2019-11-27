package com.springbootIntegration.demo.test.spi;

/**
 * @author liukun
 * @description 接口的实现类，假定作为规范中实现的一种算法
 * @date 2019/11/27
 */
public class AlgorithmImplA implements AlgorithmInterface {
    @Override
    public void run() {
        System.out.println("AlgorithmImplA is computing");
    }
}
