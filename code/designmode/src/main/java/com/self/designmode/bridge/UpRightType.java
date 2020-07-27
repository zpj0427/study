package com.self.designmode.bridge;

/**
 * 抽象子类: 直立类型手机
 * @author PJ_ZHANG
 * @create 2020-07-27 17:57
 **/
public class UpRightType extends PhoneType{
    public UpRightType(IBrand brand) {
        super(brand);
    }
    public void open() {
        super.open();
        System.out.println("直立类型手机开机...");
    }
    public void call() {
        super.call();
        System.out.println("直立类型手机打电话...");
    }

    public void close() {
        super.close();
        System.out.println("直立类型手机关机...");
    }
}
