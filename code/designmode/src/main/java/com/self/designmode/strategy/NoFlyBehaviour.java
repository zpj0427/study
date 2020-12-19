package com.self.designmode.strategy;

/**
 * 策略模式: 具体飞行算法, 不会飞
 * @author PJ_ZHANG
 * @create 2020-12-17 23:17
 **/
public class NoFlyBehaviour implements FlyBehaviour {

    @Override
    public void fly() {
        System.out.println("不会飞...");
    }

}
