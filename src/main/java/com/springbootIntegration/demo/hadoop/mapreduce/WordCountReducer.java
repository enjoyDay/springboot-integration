package com.springbootIntegration.demo.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liukun
 * @description
 * @since 2020/11/11
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    /**
     * 对每一组相同 k 的<k,v>组调用一次 reduce()方法
     *
     * @param key key代表一组相同<k,v>的key
     * @param value   value代表一组相同key的个数
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException {
        // 1 累加求和
        int sum = 0;
        for (IntWritable count : value) {
            sum += count.get();
        }
        // 2 输出
        context.write(key, new IntWritable(sum));
    }

}
