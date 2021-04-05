package com.springbootIntegration.demo.test.algorithm.huawei;

import java.util.Scanner;

/**
 * @author liukun
 * @description 两数之和为n
 * @since 2020/12/27
 */
public class Main1 {
    public static void test() {
        Scanner scanner = new Scanner(System.in);
        Integer peopleNum = Integer.valueOf(scanner.nextLine());
        String a = scanner.nextLine();
        Integer n = Integer.valueOf(scanner.nextLine());

        // 满足的数量
        int num = 0;
        // 循环遍历数据，求能够满足两个数之和为n
        String[] arr = a.split(" ");
        for (int i = 0; i < peopleNum; i++) {
            int a1 = Integer.valueOf(arr[i]);
            for (int j = i + 1; j < peopleNum; j++) {
                int a2 = Integer.valueOf(arr[j]);
                if (a1 + a2 == n) {
                    num++;
                }
            }
        }

        System.out.println(num);
    }

    public static void main(String[] args) {
        Main1.test();
    }
}
