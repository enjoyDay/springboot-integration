package com.springbootIntegration.demo.test.Lock;

/**
 * @author liukun
 * @description synchronized关键字的测试
 * @date 2019/11/13
 */
public class SynchronizedTest implements Runnable {

    private int i;

    @Override
    public synchronized void run() {
        for (int j = 0; j <10 ; j++) {
            String name = Thread.currentThread().getName();
            System.out.println("当前线程名为" + name + ",数量为" + i);
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        //这个注释说明一个问题，如果是不同对象，即使synchronized也没有意义，操作的不是共享变量
//        new Thread(new SynchronizedTest()).start();
//        new Thread(new SynchronizedTest()).start();

        // 下面这个使用同一个对象,操作的是共享变量
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        Thread thread1 = new Thread(synchronizedTest);
        Thread thread2 = new Thread(synchronizedTest);
        thread1.start();
        thread2.start();
    }
}
