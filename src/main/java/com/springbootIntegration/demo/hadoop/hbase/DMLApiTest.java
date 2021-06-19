package com.springbootIntegration.demo.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author liukun
 * @description 对表数据的增删改查
 * @since 2021/4/20
 */
public class DMLApiTest {
    // 对表的操作大部分是对table对象操作
    public static Table table;
    public static Connection connection;
    String tableName = "student";

    @Before
    public void before() throws IOException {
        //使用 HBaseConfiguration 的单例方法实例化
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.52.135"); // 这个属性在hbase-site.xml里有
        conf.set("hbase.zookeeper.property.clientPort", "2181");// 这个属性在hbase-default.xml里有
        connection = ConnectionFactory.createConnection(conf);
        table = connection.getTable(TableName.valueOf(tableName));
//        admin = (HBaseAdmin) connection.getAdmin();
//        admin = new HBaseAdmin(conf);

    }

    @After
    public void after() throws IOException {
        if (table != null) {
            table.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 往表里插入数据。也可以用于修改数据Z
     */
    @Test
    public void insertDate() throws IOException {
        // 表名在初始化处创建
        String rowKey = "1001";
        String columnFamily = "info";
        String columnName = "address";
        String value = "shanghai1";

        // 创建Put对象
        Put put = new Put(Bytes.toBytes(rowKey));
        // 给put对象赋值，put可以重复使用，多次addColumn
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), Bytes.toBytes(value));
        // 插入数据
        table.put(put);
    }

    /**
     * 查数据
     */
    @Test
    public void getData() throws IOException {
        String rowKey = "1001";
        Get get = new Get(Bytes.toBytes(rowKey));
//        get.addFamily()
        Result result = table.get(get);

        System.out.println("遍历");
        // 遍历
        for (Cell cell : result.rawCells()) {
            // rowKey
            System.out.print("rowKey:" + Bytes.toString(CellUtil.cloneRow(cell)));
            // 列族
            System.out.print(",列族:" + Bytes.toString(CellUtil.cloneFamily(cell)));
            // 列名
            System.out.print(",列名:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            // 列值
            System.out.print(",列值:" + Bytes.toString(CellUtil.cloneValue(cell)));
            System.out.println("");
        }

        System.out.println("遍历结束");
        byte[] row = result.getRow();
        String rowKeyResult = Bytes.toString(row);
        System.out.println(rowKeyResult);

        // 单个行
        byte[] value = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
        System.out.println(Bytes.toString(value));
    }

    /**
     * scan浏览数据
     */
    @Test
    public void scanData() throws IOException {
        Scan scan = new Scan();
        // 什么也不加是全表扫描，如果scan中加了参数，相当于加了过滤器
        // 可以获取rowKey从哪行开始，哪行结束，还可以加过滤器
        scan.addFamily(Bytes.toBytes("info"));
        ResultScanner scanner = table.getScanner(scan);
        scanner.forEach(result -> {
            // 遍历
            for (Cell cell : result.rawCells()) {
                // rowKey
                System.out.print("rowKey:" + Bytes.toString(CellUtil.cloneRow(cell)));
                // 列族
                System.out.print(",列族:" + Bytes.toString(CellUtil.cloneFamily(cell)));
                // 列名
                System.out.print(",列名:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                // 列值
                System.out.print(",列值:" + Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("");
            }
        });
    }

    /**
     * 删除数据
     */
    @Test
    public void deleteData() throws IOException {
        String rowKey = "1001";
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        // 只指定rowKey，会删除多个版本的数据
        // 只指定rowKey，和columnFamily，也会删除多个版本
        // 指定rowKey,columnFamily,columnName,使用addColumns方法，会删除指定列所有版本，如果加上时间戳，则会删除小于等于这个时间戳的版本
//                                           ,使用addColumn方法，不传时间戳，会删除最新版本的数据，加上时间戳，会删除指定时间戳的那个版本，标记是DELETE，也就是只有一个
        delete.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name1"));
        table.delete(delete);
    }
}
