package com.self.designmode.facade;

/**
 * 外观模式: 灯光
 * @author PJ_ZHANG
 * @create 2020-08-03 17:05
 **/
public class Lamplight {
    private static Lamplight lamplight = new Lamplight();
    public static Lamplight instance() {
        return lamplight;
    }
    public void lightUp() {
        System.out.println("调亮灯光...");
    }
    public void lightDown() {
        System.out.println("调暗灯光...");
    }
}
