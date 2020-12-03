package com.springbootIntegration.demo.test.algorithm.link;

/**
 * @author liukun
 * @description 单链表，只有一个头节点，并且头节点不保存数据，只指向下一个数据
 * @since 2020/12/2
 */
public class SingleLinkTest {
    Node head;

    public SingleLinkTest() {
        head = new Node("", null);
    }

    /**
     * 单链表头插法
     *
     * @param value
     */
    public void put(String value) {
        Node node = new Node(value, null);
        Node next = head.next;
        if (next == null) {
            head.next = node;
        } else {
            node.next = next;
            head.next = node;
        }
    }


    static class Node {
        String value;
        Node next;

        Node(String value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        SingleLinkTest singleLinkTest = new SingleLinkTest();
        singleLinkTest.put("1");
        singleLinkTest.put("2");
        singleLinkTest.put("3");
        singleLinkTest.put("4");

        // 打印
        Node next = singleLinkTest.head;
        while (next!=null) {
            System.out.println(next.value);
            next = next.next;
        }
    }
}
