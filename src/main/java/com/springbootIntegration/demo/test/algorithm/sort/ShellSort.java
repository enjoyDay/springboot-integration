package com.springbootIntegration.demo.test.algorithm.sort;

/**
 * @author liukun
 * @description 希尔排序
 * @since 2020/12/3
 */
public class ShellSort {
    // 实现原理是在直接插入排序的改版
//    将待排序的数组元素分成多个子序列，使得每个子序列的元素个数相对较少，
//    然后对各个子序列分别进行直接插入排序，待整个待排序列“基本有序”后，
//    最后对所有元素进行一次直接插入排序。
//    采用的策略：将相距某个“增量”的记录组成一个子序列，
//    这样才能保证子序列内分别进行直接插入排序后得到的结果是所谓的基本有序。

    public static void sort(int[] nums) {
        int increment = nums.length;
        do {
            increment = increment/2;
            for (int i = increment; i< nums.length; i++) {
                if (nums[i]< nums[i-increment]) {
                    int min = nums[i];
                    int j;
                    for (j = i-increment; nums[j]>min;j-=increment) {
                        nums[j+increment] = nums[j];
                    }

                    nums[j+increment] = min;
                }
            }
        } while (increment>1);
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 45, 6, 23, 5, 9, 1};
        SelectSort.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
