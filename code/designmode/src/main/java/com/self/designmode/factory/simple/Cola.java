package com.self.designmode.factory.simple;

/**
 * 具体商品类:可乐
 * @author pj_zhang
 * @create 2020-07-23 22:38
 **/
public class Cola extends Product {
    @Override
    public void show() {
        System.out.println("这是一杯可乐...");
    }
}
