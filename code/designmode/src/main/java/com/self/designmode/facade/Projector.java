package com.self.designmode.facade;

/**
 * 外观模式: 投影仪
 * @author PJ_ZHANG
 * @create 2020-08-03 17:04
 **/
public class Projector {
    private static Projector projector = new Projector();
    public static Projector instance() {
        return projector;
    }
    public void open() {
        System.out.println("打开投影仪...");
    }
    public void close() {
        System.out.println("关闭投影仪...");
    }
}
