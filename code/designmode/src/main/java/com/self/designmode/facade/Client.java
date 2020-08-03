package com.self.designmode.facade;

/**
 * 外观模式: 客户端
 * @author PJ_ZHANG
 * @create 2020-08-03 17:10
 **/
public class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.start();
        System.out.println("------------------------");
        facade.end();
    }
}
