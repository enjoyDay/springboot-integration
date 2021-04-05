//水仙花数是指一个N位正整数（3<=N<=7），它的每个位上的数字的N次幂之和等于它本身。
//        例如:
//        153 = 1^3 + 5^3+ 3^3.
//        1634 = 1^4 + 6^4 + 3^4 + 4^4.
//        本题要求编写程序,计算所有N位水仙花数。
//        输入
//        输入的测试数据只有一行, 一个数字 N(输入数据保证 N >= 3 并且 N < 8)表示要求输出的水仙花数的位数.
//        输出
//        每组测试数据输出包括很多行。首先按照从小到大的顺序输出满足要求的数, 每个数一行；最后输出一个整数, 代表满足要求的数的个数.
//        样例
//        输入样例 1
//        3
//        输出样例 1
//        153
//        370
//        371
//        407
//        4

package com.springbootIntegration.demo.test.algorithm;

import java.util.Scanner;

public class Main {
    public static void test() {
        Scanner scanner = new Scanner(System.in);
        Integer n = Integer.valueOf(scanner.nextLine());

        // 最小的n位数值
        int min = (int) Math.pow(10, n - 1);
        // 最大的n位数值
        int max = (int) Math.pow(10, n) - 1;
        // 遍历
        int count = 0;
        for (int i = min; i <= max; i++) {
            int match = match(i, n);
            if (match == i) {
                System.out.println(match);
                count++;
            }
        }

        System.out.println(count);
    }

    private static int match(int i, int n) {
        int sum = 0;
        int v = 0;
        while (i > 0) {
            v = i % 10;
            sum = sum + (int) Math.pow(v, n);
            i /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        Main.test();
    }
}
