package com.springbootIntegration.demo.hadoop.hdfs;

import org.apache.commons.httpclient.URIException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author liukun
 * @description
 * @since 2020/11/4
 */
public class HDFSClientStreamIO {
    public static void uploadFile() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration,"root");
        // 2 创建输入流
        FileInputStream fis = new FileInputStream(new File("D:\\MySourceCode\\testIdea\\springbootIntegration\\src\\main\\java\\com\\springbootIntegration\\demo\\hadoop\\hdfs\\hello.txt"));
        // 3 获取输出流
        FSDataOutputStream fos = fs.create(new Path("/hello4.txt"));
        // 4 流对拷
        IOUtils.copyBytes(fis, fos, configuration);
        // 5 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }

    public static void downloadFile() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration,"root");
        // 2 获取输入流
        FSDataInputStream fis = fs.open(new Path("/hello4.txt"));
        // 3 获取输出流
        // 这里可以将 System.out 变为输出到文件中
        IOUtils.copyBytes(fis, System.out, configuration);
        // 4 流对拷
        // 5 关闭资源
        IOUtils.closeStream(fis);
    }



    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
//        uploadFile();
        downloadFile();
    }
}
