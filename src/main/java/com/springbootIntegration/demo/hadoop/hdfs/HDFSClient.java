package com.springbootIntegration.demo.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author liukun
 * @description HDFS客户端API操作，用于连接HDFS
 * @since 2020/10/30
 */
public class HDFSClient {
    /**
     * 上传文件
     *
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    public static void uploadFile() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 配置在集群上运行,注意这里设置了虚拟机中192.168.52.135和localhost本机的端口映射
        // configuration.set("fs.defaultFS", "hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);

        // 第一个参数在hadoop的core.xml文件中有设置；最后一个参数表示使用什么用户操作hadoop
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "root");
        // 2 上传文件
        // 第一个参数是本地windows下的文件路径 第二个参数是hdfs的文件路径
        fs.copyFromLocalFile(
                new Path("D:\\MySourceCode\\testIdea\\springbootIntegration\\src\\main\\java\\com\\springbootIntegration\\demo\\hadoop\\hdfs\\test.txt"),
                new Path("/hello2.txt"));
        // 3 关闭资源
        fs.close();


    }



    /**
     * 删除文件
     *
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    public static void deleteFile() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 配置在集群上运行,注意这里设置了虚拟机中192.168.52.135和localhost本机的端口映射
        // configuration.set("fs.defaultFS", "hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);

        // 第一个参数在hadoop的core.xml文件中有设置；最后一个参数表示使用什么用户操作hadoop
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "root");

        fs.delete(new Path("/hello2.txt"), false);
        // 3 关闭资源
        fs.close();
    }

    /**
     * 下载文件
     *
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    public static void downloadFile() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "root");
        // 2 执行下载操作
        // boolean delSrc 指是否将原文件删除
        // Path src 指要下载的文件路径
        // Path dst 指将文件下载到的路径
        // boolean useRawLocalFileSystem 是否开启文件效验
        fs.copyToLocalFile(false, new Path("/hello2.txt"), new Path("D:\\MySourceCode\\testIdea\\springbootIntegration\\src\\main\\java\\com\\springbootIntegration\\demo\\hadoop\\hdfs"), true);
        // 3 关闭资源
        fs.close();
    }

    /**
     * 创建文件夹
     */
    public static void mkdirs() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "root");
        // 2 创建目录
        fs.mkdirs(new Path("/0906/daxian/banzhang"));
        // 3 关闭资源
        fs.close();
    }

    public static void deleteDirs() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "root");
        // 2 执行删除
        fs.delete(new Path("/0906/"), true);
        // 3 关闭资源
        fs.close();
    }

    public static void renameFile() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "root");
        // 2 修改文件名称
        fs.rename(new Path("/hello2.txt"), new Path("/hello6.txt"));
        // 3 关闭资源
        fs.close();
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//        uploadFile();
//        deleteFile();
//        downloadFile();
//        mkdirs();
//        deleteDirs();
        renameFile();
    }
}
