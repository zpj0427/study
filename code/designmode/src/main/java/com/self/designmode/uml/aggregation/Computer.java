package com.self.designmode.uml.aggregation;

/**
 * 聚合关系
 * @author PJ_ZHANG
 * @create 2020-07-22 12:17
 **/
public class Computer {
    // 表示鼠标和键盘都可以与电脑分离, 不是强相关
    private Mouse mouse;
    private Moniter moniter;
    public void setMouse(Mouse mouse) { this.mouse = mouse; }
    public void setMoniter(Moniter moniter) { this.moniter = moniter; }
}

class Mouse {}

class Moniter {}
