package com.self.designmode.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布者具体实现类, 进行观察者注册和通知
 * @author PJ_ZHANG
 * @create 2020-12-16 16:15
 **/
public class SubjectImpl implements Subject {

    // 温度
    private double temperature;

    // 湿度
    private double humidity;

    // 气压
    private double pressure;

    // 观察者集合
    private List<Observer> lstObserver = new ArrayList<>(10);

    // 添加观察者
    @Override
    public void addObserver(Observer observer) {
        lstObserver.add(observer);
    }

    // 移除观察者
    @Override
    public void removeObserver(Observer observer) {
        lstObserver.remove(observer);
    }

    @Override
    public void notifyObserver() {
        // 遍历每一个观察者, 进行数据通知
        for (Observer observer : lstObserver) {
            observer.update(temperature, humidity, pressure);
        }
    }

    // 发生数据变更
    public void changeData(double temperature, double humidity, double pressure) {
        // 进行数据变更
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        // 变更完成, 进行观察者通知
        notifyObserver();
    }

}
