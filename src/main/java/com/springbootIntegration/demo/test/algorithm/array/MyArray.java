package com.springbootIntegration.demo.test.algorithm.array;

/**
 * @author liukun
 * @description
 * @since 2020/12/7
 */
public class MyArray {
    private int[] data;
    private int size;

    public MyArray(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    /**
     * 插入
     *
     * @param index   数组下标，从1开始
     * @param element 元素值
     */
    public void insert(int index, int element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("数组下标越界");
        }

        // 如果插入数大于数组数量，扩容
        if (size >= data.length) {
            resize();
        }


        for (int i = size - 1; i >= index - 1; i--) {
            data[i + 1] = data[i];
        }

        data[index - 1] = element;
        size++;
    }

    private void resize() {
        int[] newArray = new int[data.length * 2];
        System.arraycopy(data, 0, newArray, 0, data.length);
        data = newArray;
    }

    public int delete(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("超出数组实际元素范围");
        }

        int deleteNum = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;

        return deleteNum;
    }

    public static void main(String[] args) {
        // 注意，该类必须从下标为1处添加元素
        MyArray myArray = new MyArray(5);
        myArray.insert(1, 1);
        myArray.insert(2, 2);
        myArray.insert(2, 6);
        myArray.insert(3, 3);
        myArray.insert(2, 4);
        myArray.insert(5, 5);
    }
}
