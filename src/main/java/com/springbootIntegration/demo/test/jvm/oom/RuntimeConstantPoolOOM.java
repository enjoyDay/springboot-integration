package com.springbootIntegration.demo.test.jvm.oom;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liukun
 * @description
 * -XX:PermSize=6M -XX:MaxPermSize=6M
 * @since 2020/7/28
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();

        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
