package com.springbootIntegration.demo.test.cache;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author liukun
 * @description 手写实现LRU缓存，最近最久未被使用策略
 * 实现思路：使用双向链表+hashmap实现
 * 1、当访问某个节点时，将其从原来的位置删除，并将其插入到链表的头部，这样保证链表尾部存储的就是最近最久未使用的节点
 * 当节点数量大于缓存最大空间时就淘汰链表最末尾的节点。
 * 2、为了使用删除的操作时间复杂度为O(1),就不能采用遍历的方式找到需要删除的节点。使用hashmap存储key到节点的映射
 * ，通过key就能以O(1)的时间得到节点，然后再以O(1)的时间将其从双向链表中删除
 * @date 2020/1/8
 */
public class LRUCache<K, V> implements Iterable<K> {

    // 双向链表的头, 注意，head和tail表示的都是空节点！！！
    private Node head;
    // 双向链表的尾
    private Node tail;
    // 用于key和node之间的映射
    private HashMap<K, Node> map;
    // 用于表示map保存的最大值
    private int maxSize;

    private class Node {
        Node pre;
        Node next;
        K k;
        V v;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        map = new HashMap<>(maxSize * 4 / 3);

        head.next = tail;
        tail.pre = head;
    }

    // 用于获取key对应的节点值
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        Node node = map.get(key);
        unlink(node);
        appendHead(node);

        return node.v;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            unlink(node);
        }

        Node node = new Node(key, value);
        appendHead(node);

        if (map.size() > maxSize) {
            // 从双向链表中删除
            Node toRemove = removeTail();
            // 从map中删除
            map.remove(toRemove.k);
        }
    }

    // 用于断开链表node两端
    private void unlink(Node node) {
        //删除节点口诀，从后往前走！
        Node pre = node.pre;
        Node next = node.next;
        pre.next = next;
        next.pre = pre;

        node.pre = null;
        node.next = null;
    }

    // 用于将节点放到链表头结点中
    private void appendHead(Node node) {
        Node next = head.next;
        // 插入的口诀，s节点的前驱和后继，后继节点的前驱，前节点的后继
        node.pre = head;
        node.next = next;
        next.pre = node;
        head.next = node;
    }

    // 用于删除双向链表尾部的节点
    public Node removeTail() {
        Node node = tail.pre;
        Node pre = node.pre;

        tail.pre = pre;
        pre.next = tail;

        node.pre = null;
        node.next = null;

        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private Node node = head.next;

            @Override
            public boolean hasNext() {
                return node != tail;

            }

            @Override
            public K next() {
                Node cur = node;
                node = node.next;
                return cur.k;
            }
        };
    }
}
