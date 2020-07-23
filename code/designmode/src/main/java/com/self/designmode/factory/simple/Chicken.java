package com.self.designmode.factory.simple;

/**
 * 具体商品类:炸鸡
 * @author pj_zhang
 * @create 2020-07-23 22:37
 **/
public class Chicken extends Product {
    @Override
    public void show() {
        System.out.println("这是一只炸鸡...");
    }
}
