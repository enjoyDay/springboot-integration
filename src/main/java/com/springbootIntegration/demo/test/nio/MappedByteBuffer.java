package com.springbootIntegration.demo.test.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author liukun
 * @description 测试读取进行大文件的操作
 * @date 2019/11/4
 */
public class MappedByteBuffer {
    @Test
    public void test01() throws IOException {
        long startTime = System.currentTimeMillis();

        FileChannel read = FileChannel.open(Paths.get("F:\\java高级课程-蚂蚁架构师\\java架构师蚂蚁2\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第五节(实际操作直接缓冲区与非缓冲区比较).mp4"), StandardOpenOption.READ);
        FileChannel write = FileChannel.open(Paths.get("F:\\java高级课程-蚂蚁架构师\\java架构师蚂蚁2\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第五节(实际操作直接缓冲区与非缓冲区比较)2.mp4"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        java.nio.MappedByteBuffer readMapperBuffer = read.map(FileChannel.MapMode.READ_ONLY, 0, read.size());
        java.nio.MappedByteBuffer writeMapperBuffer = write.map(FileChannel.MapMode.READ_WRITE, 0, read.size());
        byte[] buffer = new byte[readMapperBuffer.limit()];

        readMapperBuffer.get(buffer);
        writeMapperBuffer.put(buffer);

        write.close();
        read.close();

        long endTime = System.currentTimeMillis();
        System.out.println("使用MappedByteBuffer复制一个文件使用时间：" + (endTime - startTime));
    }

    /**
     * 使用普通channel进行文件读取
     */
    @Test
    public void test02() throws IOException {
        long startTime = System.currentTimeMillis();

        FileInputStream inputStream = new FileInputStream("F:\\java高级课程-蚂蚁架构师\\java架构师蚂蚁2\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第五节(实际操作直接缓冲区与非缓冲区比较).mp4");
        FileOutputStream outputStream = new FileOutputStream("F:\\java高级课程-蚂蚁架构师\\java架构师蚂蚁2\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第五节(实际操作直接缓冲区与非缓冲区比较)2.mp4");

        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        outChannel.close();
        inChannel.close();
        outputStream.close();
        inputStream.close();

        long endTime = System.currentTimeMillis();
        System.out.println("使用channel复制一个文件使用时间：" + (endTime - startTime));
    }
}
