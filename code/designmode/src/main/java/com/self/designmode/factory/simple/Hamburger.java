package com.self.designmode.factory.simple;

/**
 * 具体商品类:汉堡包
 * @author pj_zhang
 * @create 2020-07-23 22:39
 **/
public class Hamburger extends Product {
    @Override
    public void show() {
        System.out.println("这是一个汉堡包...");
    }
}
