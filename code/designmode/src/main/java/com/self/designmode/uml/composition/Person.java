package com.self.designmode.uml.composition;

/**
 * @author PJ_ZHANG
 * @create 2020-07-22 12:20
 **/
public class Person {
    // 人和头部是强相关, 必须存在
    // 组合关系
    private Head head = new Head();
    // 和电脑可以分开, 聚合关系
    private Computer computer;
    public void setComputer(Computer computer) { this.computer = computer; }
}

class Computer {}

class Head {}
