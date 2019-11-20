package com.springbootIntegration.demo.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author liukun
 * @description 消费者
 * @date 2019/11/20
 */
@Component
public class Consumer {
    @JmsListener(destination = "${spring.activemq.topic}")
    public void receiveTopic(TextMessage textMessage, Session session) throws JMSException {
        String text = textMessage.getText();
        System.out.println("消费者读取到的消息为：" + text);
    }
}
