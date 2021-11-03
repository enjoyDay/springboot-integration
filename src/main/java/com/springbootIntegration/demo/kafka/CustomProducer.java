package com.springbootIntegration.demo.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author liukun
 * @description
 * @since 2021/11/3
 */
public class CustomProducer {
    /**
     * kafka不需要回调函数的生产者，异步发送
     * <p>
     * 运行该程序，需要启动虚拟机上的kafka，而kafka又需要启动zk
     *
     * @param args
     */
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        //kafka 集群，broker-list
//        props.put("bootstrap.servers", "192.168.52.135:9092");
//        props.put("acks", "all");
//        //重试次数
//        props.put("retries", 1);
//        //批次大小
//        props.put("batch.size", 16384);
//        //等待时间
//        props.put("linger.ms", 1);
//        //RecordAccumulator 缓冲区大小
//        props.put("buffer.memory", 33554432);
//        props.put("key.serializer",
//                "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer",
//                "org.apache.kafka.common.serialization.StringSerializer");
//        Producer<String, String> producer = new
//                KafkaProducer<>(props);
//        for (int i = 0; i < 10; i++) {
//            // first是topic
//            producer.send(new ProducerRecord<String, String>("first",
//                    Integer.toString(i), Integer.toString(i)));
//        }
//
//        producer.close();
//    }

    /**
     * 带回调函数的生产者发送
     *
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        //kafka 集群，broker-list
        props.put("bootstrap.servers", "192.168.52.135:9092");
        props.put("acks", "all");
        //重试次数
        props.put("retries", 1);
        //批次大小
        props.put("batch.size", 16384);
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new
                KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            // first是topic
            producer.send(new ProducerRecord<String, String>("first",
                    Integer.toString(i), Integer.toString(i)), new Callback() {
                // 回调函数
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("success->" + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }

        producer.close();
    }
}
