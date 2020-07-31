package com.springbootIntegration.demo.test.jvm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author liukun
 * @description
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 * @since 2020/7/28
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field declaredField = Unsafe.class.getDeclaredFields()[0];
        declaredField.setAccessible(true);
        Unsafe unsafe = (Unsafe) declaredField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
