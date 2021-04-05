package com.springbootIntegration.demo.test.algorithm.huawei;

import java.util.Scanner;

/**
 * @author liukun
 * @description 满足条件的最长子序列
 * @since 2020/12/27
 */
public class Main2 {
    public static void test() {
        Scanner scanner = new Scanner(System.in);
        // 获取输入的完整序列
        String s = scanner.nextLine();
        Integer sum = Integer.valueOf(scanner.nextLine());

        // 循环遍历是否有子序列满足和为sum
        String[] nums = s.split(",");
        // 最长字串。默认没有
        int max = -1;
        for (int i = 0; i < nums.length; i++) {
            // 每取出一个数，就计算和是否为sum
            int compute = compute(nums, i, sum);
            if (max < compute) {
                max = compute;
            }
        }

        System.out.println(max);
    }

    private static int compute(String[] nums, int i, int sum) {
        // k表示有几个数满足要求的
        int k = 1;
        int result = Integer.valueOf(nums[i]);
        if (result > sum) {
            return -1;
        } else if (result == sum) {
            return 1;
        }

        for (int j = i + 1; j < nums.length; j++) {
            k++;
            result += Integer.valueOf(nums[j]);

            if (result > sum) {
                k = -1;
            }
            if (result == sum) {
                break;
            }
        }

        if (result < sum) {
            return -1;
        }

        return k;
    }

    public static void main(String[] args) {
        Main2.test();
    }
}
