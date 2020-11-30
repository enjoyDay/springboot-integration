package com.springbootIntegration.demo.hadoop.mapreduce.phoneflow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liukun
 * @description
 * @since 2020/11/16
 */
public class FlowCountReducer  extends Reducer<Text, FlowBean, Text, FlowBean> {
    /**
     * key代表mapper输出的key，也就是手机号
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long sum_upFlow = 0;
        long sum_downFlow = 0;
        
        // 遍历手机号相同的value都是多少，然后返回
        for (FlowBean flowBean : values) {
            sum_upFlow += flowBean.getSumFlow();
            sum_downFlow += flowBean.getDownFlow();
        }

        // 2 封装对象
        FlowBean resultBean = new FlowBean(sum_upFlow, sum_downFlow);
        // 3 写出
        context.write(key, resultBean);
    }
}
