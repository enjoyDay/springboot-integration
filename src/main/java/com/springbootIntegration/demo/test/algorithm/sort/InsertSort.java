package com.springbootIntegration.demo.test.algorithm.sort;

/**
 * @author liukun
 * @description 直接插入排序
 * @since 2020/12/3
 */
public class InsertSort {
    public static void sort(int[] nums) {
        // 原理就是每个循环取的值都是最小的，然后将数组中对应位置前面的比它大的值都依次往后移动，将最小值放到最前面
        for (int i = 1; i<nums.length;i++) {
            if (nums[i]<nums[i-1]) {
                int min = nums[i];
                int j = i-1;
                for (j=i-1;nums[j]<min;j--) {
                    nums[j+1]= nums[j];
                }
                nums[j+1] = min;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 45, 6, 23, 5, 9, 1};
        SelectSort.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
