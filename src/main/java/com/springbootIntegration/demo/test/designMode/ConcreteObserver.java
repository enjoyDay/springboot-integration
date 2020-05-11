package com.springbootIntegration.demo.test.designMode;

/**
 * @author liukun
 * @description
 * @date 2019/12/25
 */
public class ConcreteObserver implements Observer, DisplayElement {
    private Subject subject;
    private float temp;

    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        subject.registerObserver(this);
    }

    // 接收到主题的温度更新
    @Override
    public void update(float temp) {
        this.temp = temp;
        display();
    }

    @Override
    public void display() {
        System.out.println("被通知，当前温度为：" + temp);
    }
}
