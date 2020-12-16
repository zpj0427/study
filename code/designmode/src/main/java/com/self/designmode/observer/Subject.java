package com.self.designmode.observer;

/**
 * 观察者模式_信息发布者顶层接口
 * @author PJ_ZHANG
 * @create 2020-12-16 16:09
 **/
public interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();

}
