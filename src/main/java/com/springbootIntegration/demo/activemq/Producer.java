package com.springbootIntegration.demo.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

/**
 * @author liukun
 * @description topic中的生产者
 * @date 2019/11/20
 */
@Component
@EnableScheduling
public class Producer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    @Scheduled(fixedDelay = 1000)
    public void sendTopic() {
        String text = "生产者发送的消息" + System.currentTimeMillis();
        jmsMessagingTemplate.convertAndSend(topic, text);
    }
}
