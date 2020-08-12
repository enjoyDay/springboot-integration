package com.springbootIntegration.demo.test.jvm.jconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liukun
 * @description jconsole测试内存使用情况
 * @since 2020/8/12
 */
public class MonitorTest {
    static class OOMObject {
        public byte[] placeholder = new byte[6 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }

        System.gc();
    }

    /**
     * 线程死循环
     */
    public static void createBusyThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                ;
            }

        }, "testBusyThread");

        thread.start();
    }

    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "testLockThread");

        thread.start();
    }

    /**
     * 线程死锁
     */
    static class SynAddRunnable implements Runnable {
        int a, b;

        public SynAddRunnable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
//        fillHeap(1000);

        BufferedReader br = new BufferedReader(new InputStreamReader(
                System.in
        ));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object object = new Object();
        createLockThread(object);
        br.readLine();

        for (int i = 0; i < 100; i++) {
            new Thread(new SynAddRunnable(1,2)).start();
            new Thread(new SynAddRunnable(2,1)).start();
        }
    }
}
