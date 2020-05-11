package com.springbootIntegration.demo.test.designMode;

/**
 * @author liukun
 * @description
 * @date 2019/12/25
 */
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
