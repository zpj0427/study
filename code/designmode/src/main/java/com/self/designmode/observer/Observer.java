package com.self.designmode.observer;

/**
 * 观察者模式_观察者顶层接口
 * @author PJ_ZHANG
 * @create 2020-12-16 16:10
 **/
public interface Observer {

    /**
     * 修改温度, 湿度, 气压
     * @param temperature
     * @param humidity
     * @param pressure
     */
    void update(double temperature, double humidity, double pressure);

}
