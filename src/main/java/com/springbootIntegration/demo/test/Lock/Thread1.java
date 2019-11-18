package com.springbootIntegration.demo.test.Lock;

/**
 * @author liukun
 * @description 实现线程1和线程2持有同一对象锁进行交替打印
 * @date 2019/11/13
 */
public class Thread1 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //和线程2持有同一个对象锁，这样会产生互斥行为，交替打印
            synchronized (ThreadTest.obj) {
                System.out.println("线程1打印，第"+(i+1)+"次");
                // 随机唤醒等待的线程，并且主动释放锁，锁的释放是在当前代码块执行完之后
                ThreadTest.obj.notify();

                try {
                    // 当前线程阻塞，释放锁，释放CPU
                    ThreadTest.obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
