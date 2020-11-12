package com.springbootIntegration.demo.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liukun
 * @description
 * @since 2020/11/11
 */
// 泛型是输入的key, LongWritable 行号
// 输入的value, Text 一行内容
// 输出的key, Text 单词
// 输出的value IntWritable 单词的个数
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable(1);

    /**
     * 每遍历一行都会执行一次这个map
     * @param key
     * @param value value对应每行读取的数据
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1 获取一行内容
        String line = value.toString();
        // 2 切割
        String[] words = line.split(" ");
        // 3 输出
        for (String word : words) {
            k.set(word);
            context.write(k, v);
        }
    }

}
