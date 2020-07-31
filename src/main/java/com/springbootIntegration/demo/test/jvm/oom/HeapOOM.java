package com.springbootIntegration.demo.test.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liukun
 * @description java堆溢出
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @since 2020/7/27
 */
public class HeapOOM {
    static class OOMObject {}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
