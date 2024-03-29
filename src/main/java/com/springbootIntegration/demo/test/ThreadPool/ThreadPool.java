package com.springbootIntegration.demo.test.ThreadPool;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liukun
 * @description
 * @date 2019/10/24
 */
public class ThreadPool {

    private static void test(int temp) {
        System.out.println(Thread.currentThread().getName() + ",i:" + temp);
    }

    // 不固定线程池
    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int temp = i;
            //只执行一次
            newCachedThreadPool.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                test(temp);
            });
        }
        newCachedThreadPool.shutdown();

        // 固定线程数线程
//    public static void main(String[] args) {
//        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 10; i++) {
//            final int temp = i;
//            newFixedThreadPool.execute(() -> System.out.println(Thread.currentThread().getId() + ",i:" + temp));
//        }
//    }

        // 定时器线程
//    public static void main(String[] args) {
//        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
//        for (int i = 0; i < 10; i++) {
//            final int temp = i;
//            newScheduledThreadPool.schedule(new Runnable() {
//                public void run() {
//                    System.out.println("i:" + temp);
//                }
//            }, 3, TimeUnit.SECONDS);
//        }
//    }

        // 单线程
//    public static void main(String[] args) {
//        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            newSingleThreadExecutor.execute(() -> {
//                System.out.println("index:" + index);
//                try {
//                    int a = 1 / 0;
//                    Thread.sleep(200);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//    }
    }
}
