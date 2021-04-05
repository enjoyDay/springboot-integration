package com.springbootIntegration.demo.test.algorithm.huawei;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author liukun
 * @description 双端队列实现栈
 * @since 2021/1/3
 */
public class DoubleQueue {
    class MyQueue {
        ArrayList<Integer> arrayList = new ArrayList<>();
        boolean getFlag = false;

        public MyQueue(boolean getFlag) {
            this.getFlag = getFlag;
        }

        public int get() {
            Integer integer = arrayList.get(0);
            arrayList.remove(0);
            return integer;
        }

        public void add(Integer element) {
            arrayList.add(element);
        }

        public Integer size() {
            return arrayList.size();
        }
    }

    MyQueue queue1 = new MyQueue(true);
    MyQueue queue2 = new MyQueue(false);

    public void add(int element) {
        if (queue1.getFlag) {
            queue1.add(element);
        } else {
            queue2.add(element);
        }
    }

    public Integer get() {
        if (queue1.getFlag) {
            if (queue1.size() == 0) {
                if (queue2.size() == 0) {
                    return null;
                }

                queue1.getFlag = false;
                queue2.getFlag = true;
                while (queue2.size() > 1) {
                    queue1.add(queue2.get());
                }
                return queue2.get();
            } else {
                while (queue1.size() > 1) {
                    queue2.add(queue1.get());
                }
                return queue1.get();
            }
        } else {
            if (queue2.size() == 0) {
                if (queue1.size() == 0) {
                    return null;
                }

                queue2.getFlag = false;
                queue1.getFlag = true;
                while (queue1.size() > 1) {
                    queue2.add(queue1.get());
                }
                return queue1.get();
            } else {
                while (queue2.size() > 1) {
                    queue1.add(queue2.get());
                }
                return queue2.get();
            }
        }
    }

    public static void main(String[] args) {
        DoubleQueue doubleQueue = new DoubleQueue();
        doubleQueue.add(1);
        doubleQueue.add(2);
        doubleQueue.add(3);

        System.out.println(doubleQueue.get());
        System.out.println(doubleQueue.get());
        doubleQueue.add(4);
        doubleQueue.add(5);
        System.out.println(doubleQueue.get());
        System.out.println(doubleQueue.get());
        System.out.println(doubleQueue.get());
        doubleQueue.add(21);
        System.out.println(doubleQueue.get());
    }
}
