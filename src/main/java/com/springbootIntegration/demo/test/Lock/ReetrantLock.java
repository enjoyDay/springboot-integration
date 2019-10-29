package com.springbootIntegration.demo.test.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liukun
 * @description 学习读写锁
 * @date 2019/10/29
 */
public class ReetrantLock {
    private Map<String, Object> map = new HashMap<String, Object>();
    //提供一个锁
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    //读锁
    Lock readLock = rwl.readLock();
    //写锁
    Lock writeLock = rwl.writeLock();

    //读
    public Object get(String key) {
        readLock.lock();

        try {
            //需要加上延迟，不然运行太快
            Thread.sleep(100);
            System.out.println("正在做读的操作,key:" + key + " 开始");
            Object o = map.get(key);
            System.out.println("正在做读的操作,key:" + key + " 结束");
            System.out.println();
            return o;
        } catch (Exception e) {
        } finally {
            readLock.unlock();
        }

        return key;
    }

    //写
    public Object put(String key, Object value) {
        writeLock.lock();

        try {
            Thread.sleep(100);
            System.out.println("正在做写的操作,key:" + key + ",value:" + value + "开始.");
            Object o = map.put(key, value);
            System.out.println("正在做写的操作,key:" + key + ",value:" + value + "结束.");
            System.out.println();
            return o;
        } catch (Exception e) {

        } finally {
            writeLock.unlock();
        }
        return value;
    }

    //清空
    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } catch (Exception e) {

        } finally {
            writeLock.unlock();
        }
    }


    public static void main(String[] args) {
//        两个线程，分别进行读和写
        ReetrantLock reetrantLock1 = new ReetrantLock();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                reetrantLock1.put(String.valueOf(i),i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                reetrantLock1.get(String.valueOf(i));
            }
        }).start();
    }
}
