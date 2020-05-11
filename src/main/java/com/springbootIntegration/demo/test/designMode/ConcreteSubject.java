package com.springbootIntegration.demo.test.designMode;

import java.util.ArrayList;

/**
 * @author liukun
 * @description
 * @date 2019/12/25
 */
public class ConcreteSubject implements Subject {
    // 用于记录注册的观察者
    private ArrayList observers = new ArrayList();
    // 用于在各个观察者之间传递数据
    private float temp;

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i > 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (Object obs : observers) {
            Observer observer = (Observer) obs;
            observer.update(temp);
        }
    }

    // 用于通知数据的更新
    public void measurementChanged() {
        notifyObservers();
    }

    // 用于设置传递的数据temp
    public void setTemp(float temp) {
        this.temp = temp;
        // 设置值后，通知给所有的注册者
        measurementChanged();
    }
}
