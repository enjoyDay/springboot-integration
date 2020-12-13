package com.springbootIntegration.demo.test.algorithm.queue;

/**
 * @author liukun
 * @description
 * @since 2020/12/11
 */
public class MyQueue {
    private Node head;
    private Node tail;

    public MyQueue() {
        this.head = new Node(0,null);
        this.tail = new Node(0,null);
        this.head.next = this.tail;
    }

    public void enqueue(int value) {
        Node node = new Node(value, null);
        if (this.head.next == this.tail) {
            // 空链
            head = node;
            tail = head;
        } else {
            // 队尾入
            tail.next = node;
            tail = node;
        }
    }

    public int dequeue() throws Exception {
        if (this.head == this.tail && this.head.value==0) {
            throw new Exception("空链");
        }

        if (this.head == this.tail) {
            int value = this.head.value;
            this.head = this.tail = new Node(0, null);
            return value;
        }

        int value = head.value;
        head = head.next;
        return value;
    }


    private class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) throws Exception {
        MyQueue myQueue = new MyQueue();
        myQueue.enqueue(1);
        myQueue.enqueue(2);

        System.out.println(myQueue.dequeue());
        System.out.println(myQueue.dequeue());
    }
}
