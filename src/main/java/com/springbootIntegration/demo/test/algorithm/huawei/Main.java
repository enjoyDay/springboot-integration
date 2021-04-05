package com.springbootIntegration.demo.test.algorithm.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void test0() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

    }

    public static void test() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        while (scanner.hasNext()) {
            ArrayList<R> arrayList = new ArrayList<>();
            arrayList.add(new R(1));
            Integer mounth = Integer.valueOf(scanner.nextLine());
            int k = 1;
            while (k < mounth) {
                int j = 0;
                for (int i = 0; i < arrayList.size(); i++) {
                    R r = arrayList.get(i);
                    // 从开始月份加1
                    r.mounthAdd();
                    int mounth1 = r.getMounth();
                    if (mounth1 >= 3) {
                        j++;
                    }
                }
                for (int i = 0; i < j; i++) {
                    arrayList.add(new R(1));
                }
                k++;
            }

            System.out.println(arrayList.size());
        }

    }

    private static class R {
        int mounth;

        R(int mounth) {
            this.mounth = mounth;
        }

        public void mounthAdd() {
            this.mounth++;
        }

        public int getMounth() {
            return this.mounth;
        }
    }

    /**
     * 将中缀表达式转换为后缀表达式（逆波兰表达式）
     * @param express
     * @return
     */
    public static String transfer(String express){
        Stack<String> stack = new Stack<>();
        List<String> list= new ArrayList<>();
        for (int i=0;i<express.length();i++){
            if ((express.charAt(i)+"").matches("\\d")){
                list.add(express.charAt(i)+"");
            }else if((express.charAt(i)+"").matches("[\\+\\-\\*\\/]")){
                //如果stack为空
                if (stack.isEmpty()){
                    stack.push(express.charAt(i)+"");
                    continue;
                }
                //不为空

                //上一个元素不为（，且当前运算符优先级小于上一个元素则，将比这个运算符优先级大的元素全部加入到队列中
                while (!stack.isEmpty()&&!stack.lastElement().equals("(")&&!comparePriority(express.charAt(i)+"",stack.lastElement())){
                    list.add(stack.pop());
                }
                stack.push(express.charAt(i)+"");
            }else if(express.charAt(i)=='('){
                //遇到左小括号无条件加入
                stack.push(express.charAt(i) + "");
            }else if(express.charAt(i)==')'){
                //遇到右小括号，则寻找上一堆小括号，然后把中间的值全部放入队列中
                while(!("(").equals(stack.lastElement())){
                    list.add(stack.pop());
                }
                //上述循环停止，这栈顶元素必为"("
                stack.pop();
            }
        }
        //将栈中剩余元素加入到队列中
        while (!stack.isEmpty()){
            list.add(stack.pop());
        }
        StringBuffer stringBuffer = new StringBuffer();
        //变成字符串
        for (String s : list) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    /**
     * 比较运算符的优先级
     * @param o1
     * @param o2
     * @return
     */
    public static boolean comparePriority(String o1,String o2){
        return getPriorityValue(o1)>getPriorityValue(o2);
    }

    /**
     * 获得运算符的优先级
     * @param str
     * @return
     */
    private static int getPriorityValue(String str) {
        switch(str){
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 2;
            default:
                throw new RuntimeException("没有该类型的运算符！");
        }
    }

    public static void main(String[] args) {
        System.out.println(Main.transfer("(2+3)*1"));
    }
}
