package com.self.designmode.strategy;

/**
 * 策略模式: 算法使用客户, 具体实现类_野鸭子
 * @author PJ_ZHANG
 * @create 2020-12-17 23:16
 **/
public class WildDuck extends Duck {

    public WildDuck() {
        super.setFlyBehaviour(new GoodFlyBehaviour());
    }

}
