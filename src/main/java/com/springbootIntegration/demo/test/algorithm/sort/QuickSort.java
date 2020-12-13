package com.springbootIntegration.demo.test.algorithm.sort;

/**
 * @author liukun
 * @description 快速排序
 * @since 2020/12/3
 */
public class QuickSort {
//    通过一趟排序将待记录分割成独立的两部分，
//    其中一部分记录的关键字均比另一部分记录的关键字小，
//    则可分别对这两部分记录继续进行排序，以达到整个序列有序的目的。
    public static void sort(int[] nums) {        qsort(nums, 0 , nums.length);
    }

    private static void qsort(int[] nums, int low, int high) {
        int pivot;
        if (low < high) {
            // 将L->r[low .. high]一分为二，算出枢轴值pivot
            pivot = Partition(nums , low , high);
            qsort(nums,low,pivot);
            qsort(nums,pivot+1, high);
        }
    }

    private static int Partition(int[] nums, int low, int high) {
        int pivotkey = nums[low];
        while(low < high){
            while(low < high && nums[high]>=pivotkey) {
                high--;
            }
            // 将比枢轴记录小的记录交换到低端
            int temp = nums[low];
            nums[low] = nums[high];
            nums[high] = temp;

            while(low < high && nums[low]<=pivotkey) {
                low++;
            }
            // 将比枢轴记录大的记录交换到高端
            int temp1 = nums[low];
            nums[low] = nums[high];
            nums[high] = temp1;
        }

        return low;
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 45, 6, 23, 5, 9, 1};
        SelectSort.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
