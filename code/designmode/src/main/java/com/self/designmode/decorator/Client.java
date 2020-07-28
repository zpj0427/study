package com.self.designmode.decorator;

/**
 * 装饰者模式客户端
 * @author PJ_ZHANG
 * @create 2020-07-28 18:18
 **/
public class Client {
    public static void main(String[] args) {
        int cost = new Chocolate(new Milk(new LongBlack())).cost();
        System.out.println(cost);
    }
}
