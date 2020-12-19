package com.self.designmode.strategy;

/**
 * 策略模式: 客户端
 * @author PJ_ZHANG
 * @create 2020-12-17 23:18
 **/
public class Client {

    public static void main(String[] args) {
        // 定义一个野鸭子
        Duck duck = new WildDuck();
        duck.fly();
        // 定义一个玩具鸭子
        Duck toyDuck = new ToyDuck();
        toyDuck.fly();
        // 动态变更野鸭子的策略
        duck.setFlyBehaviour(new NoFlyBehaviour());
        duck.fly();
    }

}
