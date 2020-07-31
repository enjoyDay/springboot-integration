package com.springbootIntegration.demo.test.jvm;

/**
 * @author liukun
 * @description 用于测试栈大小崩溃，为调优做准备
 * @date 2019/10/11
 */
public class StackTest {
    private static int count=0;
    public static void recursion(long a,long b,long c){
        long e=1,f=2,g=3,h=4,i=5,k=6,q=7,x=8,y=9,z=10;
        count++;
        recursion(a,b,c);
    }
    public static void main(String args[]){
        try{
            //主要测试函数调用深度
            recursion(0L,0L,0L);
        }catch(Throwable e){
            System.out.println("deep of calling = "+count);
            e.printStackTrace();
        }
    }

}
