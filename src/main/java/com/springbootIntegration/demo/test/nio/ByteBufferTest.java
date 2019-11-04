package com.springbootIntegration.demo.test.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author liukun
 * @description 对byteBuffer进行测试
 * @date 2019/11/3
 */
public class ByteBufferTest {
    @Test
    public void test01() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("--------初始化------------");
        byteBuffer.clear();
        System.out.println(byteBuffer.position());//能够填充数据的位置
        System.out.println(byteBuffer.limit());//limit位置之外不能填充数据
        System.out.println(byteBuffer.capacity());//总的数据容量

        //2、想缓冲区存入5个数据
        byteBuffer.put("abcde".getBytes());
        System.out.println("---------存入数据-----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //3、开启读模式
        byteBuffer.flip();
        System.out.println("---------开启读模式-----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println("---------读完之后的位置-----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println("----------重复读模式...----------");
        // 4.开启重复读模式
        byteBuffer.rewind();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        byte[] bytes2 = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes2);
        System.out.println(new String(bytes2, 0, bytes2.length));

        // 5.clean 清空缓冲区  数据依然存在,只不过数据被遗忘
        System.out.println("----------清空缓冲区...之后还是能获的数据----------");
        byteBuffer.clear();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println((char)byteBuffer.get());

    }
}
