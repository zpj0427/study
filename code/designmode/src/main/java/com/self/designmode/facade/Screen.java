package com.self.designmode.facade;

/**
 * 外观模式: 幕布类
 * @author PJ_ZHANG
 * @create 2020-08-03 17:03
 **/
public class Screen {
    private static Screen screen = new Screen();
    public static Screen instance() {
        return screen;
    }
    public void up() {
        System.out.println("收起屏幕...");
    }
    public void down() {
        System.out.println("放下屏幕...");
    }
}
