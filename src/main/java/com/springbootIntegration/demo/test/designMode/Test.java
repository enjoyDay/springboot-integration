package com.springbootIntegration.demo.test.designMode;

/**
 * @author liukun
 * @description
 * @date 2019/12/25
 */
public class Test {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        // 将下面的观察者注册给主题
        Observer observer = new ConcreteObserver(subject);
        // 之后observer可以干其他的事，
        // 再想要进行通知时，必须直接调用subject进行通知
        ((ConcreteSubject) subject).setTemp(12.0f);
    }
}
