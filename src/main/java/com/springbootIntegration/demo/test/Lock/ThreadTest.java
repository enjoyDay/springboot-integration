package com.springbootIntegration.demo.test.Lock;

/**
 * @author liukun
 * @description
 * @date 2019/11/13
 */
public class ThreadTest {
    //创建一个共同的对象锁
    public static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
    }

}
