package com.springbootIntegration.demo.test.algorithm.link;

/**
 * @author liukun
 * @description 单链表，只有一个头节点，并且头节点不保存数据，只指向下一个数据
 * @since 2020/12/2
 */
public class SingleLinkTest {
    Node head;

    private int size;

    public SingleLinkTest() {
        head = new Node("", null);
        // 头节点不算数据
        size = 0;
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
        size++;
    }

    public void insert(int index, String value) {
        if (index < 0) {
            throw new RuntimeException("下标小于0");
        }

        if (index > size) {
            // 尾部插入
            Node tail = tail();
            Node node = new Node(value, null);
            tail.next = node;
        } else {
            // 中间插入
            int i = 1;
            Node next = head.next;
            Node prior = null;
            while (index != i) {
                i++;
                prior = next;
                next = next.next;
            }

            Node node = new Node(value, null);
            node.next = next;
            prior.next = node;
        }

        size++;
    }

    public void delete(String value) {
        // 如果头节点指向的指针为空，说明是空连，直接返回不删除
        Node next = head.next;
        if (next == null) {
            return;
        }

        // 找到要删除的节点以及前置节点
        Node prior = null;
        while (next != null && next.value != value) {
            prior = next;
            next = next.next;
        }

        if (next != null) {
            Node next1 = next.next;
            // 判断前置节点是否是头节点
            if (head.next.value == value) {
                head.next = next1;
            } else {
                prior.next = next1;
            }
        }
        size--;
    }

    /**
     * 获取尾节点
     * @return
     */
    private Node tail() {
        if (head.next == null) {
            return head;
        }
        Node node = head.next;
        while (node.next != null) {
            node = node.next;
        }

        return node;
    }

    /**
     * 获取大小
     * @return
     */
    public int size() {
        return size;
    }

    private static class Node {
        String value;
        Node next;

        Node(String value, Node next) {
            this.value = value;
            this.next = next;
        }

    }

    public static void main(String[] args) {
        SingleLinkTest singleLinkTest = new SingleLinkTest();
        singleLinkTest.insert(1, "10");
        singleLinkTest.put("1");
        singleLinkTest.put("2");
        singleLinkTest.put("3");
        singleLinkTest.put("4");
        singleLinkTest.insert(4, "5");


        singleLinkTest.delete("10");

        // 打印
        Node next = singleLinkTest.head;
        while (next != null) {
            System.out.println(next.value);
            next = next.next;
        }

        System.out.println(singleLinkTest.size());
    }
}
