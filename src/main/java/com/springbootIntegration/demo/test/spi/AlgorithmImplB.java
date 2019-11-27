package com.springbootIntegration.demo.test.spi;

/**
 * @author liukun
 * @description 算法的另一种实现
 * @date 2019/11/27
 */
public class AlgorithmImplB implements AlgorithmInterface {
    @Override
    public void run() {
        System.out.println("AlgorithmImplB is computing");
    }
}
