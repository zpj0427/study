package com.self.designmode.command;

/**
 * 请求接收者: 具体工作类
 * @author PJ_ZHANG
 * @create 2020-12-10 13:52
 **/
public class Light {

    public void on() {
        System.out.println("电灯打开了...");
    }

    public void off() {
        System.out.println("电灯关上了...");
    }

}
