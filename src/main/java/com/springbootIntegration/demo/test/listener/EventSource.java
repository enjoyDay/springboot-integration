package com.springbootIntegration.demo.test.listener;

/**
 * @author liukun
 * @description 事件源，事件发生的地点
 * @date 2019/11/12
 */
public class EventSource implements Event {
    private EventListener eventListener;

    private boolean button;
    private boolean mouse;

    @Override
    public void setEventListener(EventListener arg) {
        this.eventListener = arg;
    }

    @Override
    public boolean buttonClickEvent() {
        return button;
    }

    public void buttonClick(){
        button = true;
        System.out.println("事件源处理点击事件");
        if (eventListener != null) {
            // 将事件源传进去
            eventListener.doHandle(this);
        }
        button = false;
    }

    @Override
    public boolean mouseMoveEvent() {
        return mouse;
    }

    public void mouseMove(){
        mouse = true;
        System.out.println("事件源处理鼠标移动事件");
        if (eventListener != null) {
            // 将事件源传进去
            eventListener.doHandle(this);
        }
        mouse = false;
    }
}
