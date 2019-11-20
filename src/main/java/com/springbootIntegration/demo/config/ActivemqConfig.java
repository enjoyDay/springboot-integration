package com.springbootIntegration.demo.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

/**
 * @author liukun
 * @description
 * @date 2019/11/20
 */
@Configuration
public class ActivemqConfig {
    @Value("${spring.activemq.topic}")
    private String topic;

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topic);
    }
}
