package com.self.designmode.bridge;

/**
 * 具体实现类: 华为手机
 * @author PJ_ZHANG
 * @create 2020-07-27 17:55
 **/
public class Huawei implements IBrand {
    @Override
    public void open() {
        System.out.println("华为手机开机...");
    }

    @Override
    public void call() {
        System.out.println("华为手机打电话...");
    }

    @Override
    public void close() {
        System.out.println("华为手机关机");
    }
}
