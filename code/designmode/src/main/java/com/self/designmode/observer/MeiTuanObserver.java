package com.self.designmode.observer;

/**
 * 美团接收天气数据
 * @author PJ_ZHANG
 * @create 2020-12-16 16:21
 **/
public class MeiTuanObserver implements Observer {

    @Override
    public void update(double temperature, double humidity, double pressure) {
        System.out.println("美团接收, 温度: " + temperature);
        System.out.println("美团接收, 湿度: " + humidity);
        System.out.println("美团接收, 气压: " + pressure);
    }

}
