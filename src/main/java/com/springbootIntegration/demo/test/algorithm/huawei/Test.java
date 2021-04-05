package com.springbootIntegration.demo.test.algorithm.huawei;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author liukun
 * @description
 * @since 2020/12/21
 */
public class Test {
    // 石碑排列组合
    static Stack<String> stack = new Stack<String>();

    public static void test() {
        Scanner scanner = new Scanner(System.in);
        Integer n = Integer.valueOf(scanner.nextLine());
        String s = scanner.nextLine();

        String[] split = s.split(" ");
        f(split, n, 0);
    }

    private static void f(String[] shu, int targ, int cur) {
        if (cur == targ) {
            // 判断是不是升序
            boolean isEsc = true;
            String head = stack.get(0);
            for (int i = 1; i < stack.size(); i++) {
                String pop = (String) stack.get(i);
                // 两个字符春之间的比较，先去掉相同的字符
                int j = 0;
                for (; j < (head.length() > pop.length() ? pop.length() : head.length()); j++) {
                    if (head.charAt(j) == pop.charAt(j)) {
                        continue;
                    } else {
                        break;
                    }
                }
                // 再比较是否为升序
                if (head.charAt(j) < pop.charAt(j)) {
                    head = pop;
                    continue;
                } else {
                    isEsc = false;
                    break;
                }
            }
            if (isEsc) {
                System.out.println(stack);
            }
            return;
        }
        for (int i = 0; i < shu.length; i++) {
            if (!stack.contains(shu[i])) {
                stack.add(shu[i]);
                f(shu, targ, cur + 1);
                stack.pop();
            }

        }
    }

    /**
     * 最大花费金额，使用组合的方式，从多个数据中选择三个，并计算这个的值，恰好小于给定金额，并且计算出的金额是最大的
     */
    public static void test2() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        Integer sum = Integer.valueOf(scanner.nextLine());
        Test.sum = sum;
        // 解析字符串
        String[] nums = s.split(" ");
        // 从这里面选择三个
        f(nums, 3, 0, 0);
        if (Test.max > 0) {
            System.out.println(Test.max);
        } else {
            System.out.println(-1);
        }
    }

    /**
     * @param shu  元素
     * @param targ 要选多少个元素
     * @param has  当前有多少个元素
     * @param cur  当前选到的下标
     * <p>
     * 1	2	3     //开始下标到2
     * 1	2	4	  //然后从3开始
     */
    static int sum = 0;
    static int max = 0;

    private static void f(String[] shu, int targ, int has, int cur) {
        if (has == targ) {
            int sum = 0;
            for (int i = 0; i < stack.size(); i++) {
                sum += Integer.valueOf(stack.get(i));
            }

            if (sum < Test.sum && max < sum) {
                Test.max = sum;
            }
//            System.out.println(stack);
            return;
        }

        for (int i = cur; i < shu.length; i++) {
            if (!stack.contains(shu[i])) {
                stack.add(shu[i]);
                f(shu, targ, has + 1, i);
                stack.pop();
            }
        }

    }

    public static void test3() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            String[] s1 = s.split(" ");
            Integer a = Integer.valueOf(s1[0]);
            Integer b = Integer.valueOf(s1[1]);
            System.out.println(a + b);
        }
    }

    public static void test4() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        // 转变为整数
        Integer[] nums = new Integer[s1.length];
        for (int i = 0; i < s1.length; i++) {
            nums[i] = Integer.valueOf(s1[i]);
        }
        Arrays.sort(nums, (o1, o2) -> {
            int o11 = (int) o1;
            int o22 = (int) o2;
            String s11 = String.valueOf(o11);
            String s22 = String.valueOf(o22);

            if (s11.charAt(0) >= s22.charAt(0)) {
                return -1;
            } else {
                return 1;
            }
        });

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < nums.length; i++) {
            stringBuffer.append(nums[i]);
        }

        System.out.println(stringBuffer.toString());
    }

    /**
     * 每次从圆圈里删除第m个数字，最后剩下的一个数字
     */
    public static void test5(int index) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        int k = 0;
        while (arrayList.size() > 1) {
            // 每次删除第index个数字
            k += index;
            k = k % arrayList.size() - 1;
            if (k == -1) {
                k = arrayList.size() - 1;
            }
            System.out.println("删除的数字：" + arrayList.get(k));
            arrayList.remove(k);
        }
        // 删除的还剩最后一个
        if (arrayList.size() == 1) {
            System.out.println(arrayList.get(0));
        }
    }

    public static void test6() {
        int compute = compute(100);
        System.out.println(compute);
    }

    private static int compute(int n) {
        if (n == 0) return 0;

        return n + compute(n-1);
    }

    public static void test7() {
        // 找两个链表第一个公共节点，（前提是，后面都是相同的了）
        ArrayList<Integer> arrayList1 = new ArrayList();
        arrayList1.add(1);
        arrayList1.add(2);
        arrayList1.add(3);
        arrayList1.add(4);
        arrayList1.add(5);


        ArrayList<Integer> arrayList2 = new ArrayList();
        arrayList2.add(10);
        arrayList2.add(11);
        arrayList2.add(12);
        arrayList2.add(3);
        arrayList2.add(4);
        arrayList2.add(5);

        int index1 = arrayList1.size()-1;
        int index2 = arrayList2.size()-1;
        int common=-1;
        while(index1>0 && index2>0) {
            if (arrayList1.get(index1).equals( arrayList2.get(index2))) {
                common = arrayList1.get(index1);
            }

            index1--;
            index2--;
        }

        if (common!=-1) {
            System.out.println(common);
        }

    }

    public static void main(String[] args) {
        Test.test7();
    }
}
