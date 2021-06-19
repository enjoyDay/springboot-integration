package com.springbootIntegration.demo.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author liukun
 * @description 测试对表的操作
 * @since 2021/4/20
 */
public class DDLApiTest {
    // 对表的操作大部分使用admin
    public static HBaseAdmin admin;
    public static Connection connection;

    @Before
    public void before() throws IOException {
        //使用 HBaseConfiguration 的单例方法实例化
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.52.135"); // 这个属性在hbase-site.xml里有
        conf.set("hbase.zookeeper.property.clientPort", "2181");// 这个属性在hbase-default.xml里有
        connection = ConnectionFactory.createConnection(conf);
        admin = (HBaseAdmin) connection.getAdmin();
//        admin = new HBaseAdmin(conf);

    }

    @After
    public void after() throws IOException {
        if (admin != null) {
            admin.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 判断表是否存在
     */
    @Test
    public void tableIsExist() throws IOException {
        String tableName = "student";
        System.out.println(admin.tableExists(tableName));
    }

    /**
     * 创建表
     */
    @Test
    public void createTable() throws IOException {
        String tableName = "student1";
        String[] columnFamily = {"info1"};
        // 判断表是否存在
        if (admin.tableExists(tableName)) {
            System.out.println("表" + tableName + "已存在");
            //System.exit(0);
        } else {
            // 创建表属性对象,表名需要转字节
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
            // 创建多个列族
            for (String cf : columnFamily) {
                descriptor.addFamily(new HColumnDescriptor(cf));
            }
            // 根据对表的配置，创建表
            admin.createTable(descriptor);
//            admin.createTable(descriptor,"预分区表");
            System.out.println("表" + tableName + "创建成功！");
        }
    }

    /**
     * 创建命名空间
     */
    @Test
    public void createNamespace() throws IOException {
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
            System.out.println(namespaceDescriptor.getName());
        }

        String namespace = "demo";
        NamespaceDescriptor.Builder builder = NamespaceDescriptor.create(namespace);
        NamespaceDescriptor ns = builder.build();
        admin.createNamespace(ns);
    }

    /**
     * 创建命名空间
     */
    @Test
    public void deleteNamespace() throws IOException {
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
            System.out.println(namespaceDescriptor.getName());
        }

        String namespace = "demo";
        admin.deleteNamespace(namespace);
    }

    /**
     * 删除表
     */
    @Test
    public void deleteTable() throws IOException {
        String tableName = "student1";
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("表" + tableName + "删除成功！");
        } else {
            System.out.println("表" + tableName + "不存在！");
        }
    }
}
