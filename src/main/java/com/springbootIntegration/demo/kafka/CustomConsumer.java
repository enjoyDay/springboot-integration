package com.springbootIntegration.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * @author liukun
 * @description
 * @since 2021/11/3
 */
public class CustomConsumer {
    // 普通消费者
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.52.135:9092");
//        props.put("group.id", "test1");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("key.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        // 订阅
//        consumer.subscribe(Arrays.asList("first"));
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(100);
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//            }
//        }
//    }

    private static Map<TopicPartition, Long> currentOffset = new
            HashMap<>();

    // 自定义offset存储
    public static void main(String[] args) {
        //创建配置信息
        Properties props = new Properties();
        //Kafka 集群
        props.put("bootstrap.servers", "hadoop102:9092");
        //消费者组，只要 group.id 相同，就属于同一个消费者组
        props.put("group.id", "test");
        //关闭自动提交 offset
        props.put("enable.auto.commit", "false");
        //Key 和 Value 的反序列化类
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        //创建一个消费者
        KafkaConsumer<String, String> consumer = new
                KafkaConsumer<>(props);
        //消费者订阅主题
        consumer.subscribe(Arrays.asList("first"), new ConsumerRebalanceListener() {
            //该方法会在 Rebalance 之前调用
            @Override
            public void
            onPartitionsRevoked(Collection<TopicPartition> partitions) {
                // 在Rebalance之前正常提交即可
                commitOffset(currentOffset);
            }

            //该方法会在 Rebalance 之后调用
            @Override
            public void
            onPartitionsAssigned(Collection<TopicPartition> partitions) {
                currentOffset.clear();
                for (TopicPartition partition : partitions) {
                    // 定位到最近提交的 offset 位置继续消费
                    consumer.seek(partition, getOffset(partition));
                }
            }
        });
        while (true) {
            ConsumerRecords<String, String> records =
                    consumer.poll(100);//消费者拉取数据
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = % s % n ", record.offset(), record.key(), record.value());
                currentOffset.put(new TopicPartition(record.topic(),
                        record.partition()), record.offset());
            }
            commitOffset(currentOffset);//异步提交
        }
    }

    //获取某分区的最新 offset
    private static long getOffset(TopicPartition partition) {
        return 0;
    }

    //提交该消费者所有分区的 offset
    // 这个就是自己自定义的，提交offset，可以保存到数据库中
    private static void commitOffset(Map<TopicPartition, Long> currentOffset) {
    }
}
