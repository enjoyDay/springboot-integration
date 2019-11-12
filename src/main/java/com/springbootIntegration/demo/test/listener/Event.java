package com.springbootIntegration.demo.test.listener;

/**
 * @author liukun
 * @description 事件，主要用于获取事件状态
 * 实现的接口需要去实现它
 * @date 2019/11/12
 */
public interface Event {
    void setEventListener(EventListener arg);
    boolean buttonClickEvent();
    boolean mouseMoveEvent();
}
