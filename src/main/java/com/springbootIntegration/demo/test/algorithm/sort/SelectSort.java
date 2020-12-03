package com.springbootIntegration.demo.test.algorithm.sort;

/**
 * @author liukun
 * @description 选择排序
 * @since 2020/12/3
 */
public class SelectSort {
    public static void sort(int[] nums) {
        // 每次一个循环都会将最小值放入最前面
        for (int i = 0; i < nums.length; i++) {
            int min = i;
            for (int j = i+1; j < nums.length; j++) {
                if (nums[min]>nums[j]) {
                    min = j;
                }
            }
            // 通过上面的for循环，找到最小值，然后将最小值放入i位置
            if (min!=i) {
                int temp = nums[i];
                nums[i] = nums[min];
                nums[min] = temp;
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
