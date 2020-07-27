package com.self.designmode.bridge;

/**
 * 具体实现类: 小米手机
 * @author PJ_ZHANG
 * @create 2020-07-27 17:56
 **/
public class Xiaomi implements IBrand {
    @Override
    public void open() {
        System.out.println("小米手机开机...");
    }

    @Override
    public void call() {
        System.out.println("小米手机打电话...");
    }

    @Override
    public void close() {
        System.out.println("小米手机关机");
    }
}
