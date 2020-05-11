package com.springbootIntegration.demo.test.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author liukun
 * @description
 * @date 2019/11/27
 */
public class Test {
    public static void main(String[] args) {
        ServiceLoader<AlgorithmInterface> algorithms = ServiceLoader.load(AlgorithmInterface.class);
        for (AlgorithmInterface algorithmImpl:algorithms){
            algorithmImpl.run();
        }
    }
}
