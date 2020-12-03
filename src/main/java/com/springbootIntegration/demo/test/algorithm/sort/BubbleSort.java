package com.springbootIntegration.demo.test.algorithm.sort;

/**
 * @author liukun
 * @description 冒泡排序
 * @since 2020/12/3
 */
public class BubbleSort {
    public static void sort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            // 两两比较，如果值大的在前面，则交换位置，循环结束后，最大值在最后面
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    // 交换数据，交换之后，最大数据放到最后
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 45, 6, 23, 5, 9, 1};
        BubbleSort.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
