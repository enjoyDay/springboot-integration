package com.springbootIntegration.demo.test.algorithm.huawei;

import java.util.Scanner;

/**
 * @author liukun
 * @description
 * @since 2020/12/27
 */
public class Main3 {

    /**
     * 最长元音字串
     */
    public static void test() {
        Scanner scanner = new Scanner(System.in);
        // 要求的瑕疵度
        Integer flaw = Integer.valueOf(scanner.nextLine());
        String s = scanner.nextLine();
        // 最长元音字符串长度，默认没有
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            // 第一个是元音字符才可以
            if (isVowel(s.charAt(i))) {
                // 找到从i开头的符合条件的最长元音字串
                int i1 = subString(s, i, flaw);
                if (max < i1) {
                    max = i1;
                }
            }
        }

        System.out.println(max);
    }

    /**
     * 找到从i开头的符合条件的最长元音字串
     *
     * @param s    完整字符串
     * @param i    i开头
     * @param flaw 瑕疵度
     * @return 字串长度
     */
    private static int subString(String s, int i, int flaw) {
        int len = 1;
        // 瑕疵度
        int d = 0;
        // 当瑕疵度为0时，找都是元音的字串
        if (flaw == 0) {
            for (int j = i + 1; j < s.length(); j++) {
                if (isVowel(s.charAt(j))) {
                    len++;
                } else {
                    break;
                }
            }
            return len;
        } else {
            // 不为0时
            int j = i+1;
            for (; j < s.length(); j++) {
                len++;
                if (!isVowel(s.charAt(j))) {
                    d++;
                    if (d > flaw) {
                        len--;
                        break;
                    }
                }
            }

            if (d < flaw || !isVowel(s.charAt(j-1))) {
                return 0;
            }
        }

        return len;
    }

    /**
     * 是否是元音字符
     *
     * @param ch 输入字符
     * @return 是否是元音字符
     */
    private static boolean isVowel(char ch) {
        switch (ch) {
            case 'a':
            case 'u':
            case 'e':
            case 'i':
            case 'o':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
            default:
                return false;
        }
    }

    public static void main(String[] args) {
        Main3.test();
    }
}
