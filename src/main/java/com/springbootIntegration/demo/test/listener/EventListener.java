package com.springbootIntegration.demo.test.listener;

/**
 * @author liukun
 * @description 事件监听器，需要用于自己去实现里面的方法，并注册
 * @date 2019/11/12
 */
public interface EventListener {
    // 用于处理事件,之所以带事件参数，主要是把事件源带进来
    void doHandle(Event event);
}
