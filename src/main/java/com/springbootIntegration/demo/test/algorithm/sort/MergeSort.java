package com.springbootIntegration.demo.test.algorithm.sort;

/**
 * @author liukun
 * @description 归并排序
 * @since 2021/1/3
 */
public class MergeSort {
    public static void mergeSort(int[] arr) {
        int[] newArr = new int[arr.length];
        MSort(arr, newArr, 0, arr.length-1);
        for (int i = 0; i < newArr.length; i++) {
            System.out.println(newArr[i]);
        }
    }

    /**
     *  利用递归，将arr[min..max]归并为newArr[min..max]
     * @param arr 原始数组
     * @param newArr 新数组
     * @param min 最小下标
     * @param max 最大下标
     */
    private static void MSort(int[] arr, int[] newArr, int min, int max) {
        int[] newArr2 = new int[newArr.length];
        if (min == max) {
            newArr[min] = arr[min];
        } else {
            int m = (min + max) / 2;//将arr[min..max]平分为arr[min..m]和arr[m+1..max]
            MSort(arr, newArr2, min, m);//将arr[min..m]归并排序为newArr2[min..m]
            MSort(arr, newArr2, m + 1, max);//将arr[m+1..max]归并排序为newArr2[max+1..m]
            Merge(newArr2, newArr, min, m, max);//将newArr2[min..m]和newArr2[m+1..max]归并为newArr[min..max]
        }
    }

    /**
     * 将有序的newArr2[min..m]和newArr2[m+1..max]归并为newArr[min..max]
     * @param newArr2
     * @param newArr
     * @param min
     * @param m
     * @param max
     */
    private static void Merge(int[] newArr2, int[] newArr, int min, int m, int max) {
        int j, k;
        for (j = m + 1, k = min; j <= max && min <= m; k++) {
            //其中j代表的是newArr2[m+1..n]数组下标，min代表的是newArr2[min..m]的下标，k代表的是newArr的下标，然后由小到大归并入newArr
            if (newArr2[j] < newArr2[min]) {
                newArr[k] = newArr2[j++];
            } else {
                newArr[k] = newArr2[min++];
            }
        }

        if (j <= max) {
            for (int i = 0; i <= max - j; i++) {
                newArr[k + i] = newArr2[j + i];//将剩余的newArr2[m..max]复制到newArr
            }
        }
        // 上下两个if判断必然只有一个会执行，因此不分先后
        if (min <= m) {
            for (int i = 0; i <= m - min; i++) {
                newArr[k + i] = newArr2[min + i];//将剩余的newArr2[min..m]复制到newArr
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 45, 6, 23, 5, 9, 1};
        MergeSort.mergeSort(nums);
    }
}
