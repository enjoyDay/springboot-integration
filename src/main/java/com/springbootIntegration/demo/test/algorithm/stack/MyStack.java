package com.springbootIntegration.demo.test.algorithm.stack;

/**
 * @author liukun
 * @description 自定义栈
 * @since 2020/12/11
 */
public class MyStack {
    private int size;
    private int capacity;
    private int[] data;

    public MyStack(int capacity) {
        this.capacity = capacity;
        data = new int[this.capacity];
        this.size = 0;
    }

    public int pop() {
        if (size < 1) {
            throw new RuntimeException("has no value");
        }
        size--;
        return data[size];
    }

    public void push(int value) {
        if (size >= capacity) {
            throw new RuntimeException("has over capacity");
        }
        size++;
        data[size - 1] = value;
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack(2);

        myStack.push(1);
        myStack.push(2);
        int pop = myStack.pop();
        System.out.println(pop);
        int pop1 = myStack.pop();
        System.out.println(pop1);
        myStack.push(7);
        int pop2 = myStack.pop();
        System.out.println(pop2);
    }
}
