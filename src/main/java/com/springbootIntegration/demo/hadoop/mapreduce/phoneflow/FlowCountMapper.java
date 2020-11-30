package com.springbootIntegration.demo.hadoop.mapreduce.phoneflow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liukun
 * @description 泛型是行号，一行文本，输出手机号，序列化对象
 * @since 2020/11/14
 */
public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    FlowBean flowBean = new FlowBean();
    Text phoneNum = new Text();

    /**
     * key代表读取的文本行号
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取一行数据
        String line = value.toString();
        // 分隔数据
        String[] split = line.split("\t");
        // 统计上行流量和下行流量，并放到一个对象中
        String phone = split[1];
        String upFlow = split[split.length - 3];
        String downFlow = split[split.length - 2];
        phoneNum.set(phone);
        flowBean.set(Long.parseLong(upFlow), Long.valueOf(upFlow));

        // 写出数据
        context.write(phoneNum, flowBean);
    }
}
