package com.self.designmode.adapter.classadapter;

/**
 * 使用者
 * @author PJ_ZHANG
 * @create 2020-07-27 14:20
 **/
public class Client {
    public static void main(String[] args) {
        // 构造手机
        Phone phone = new Phone();
        // 构造手机充电的适配器
        IVoltageAdapter voltageAdapter = new PhoneVoltageAdapter();
        // 手机充电
        phone.charge(voltageAdapter);
    }
}
