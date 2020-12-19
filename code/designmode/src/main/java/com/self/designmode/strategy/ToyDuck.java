package com.self.designmode.strategy;

/**
 * 策略模式: 算法使用客户, 具体实现类_玩具鸭子
 * @author PJ_ZHANG
 * @create 2020-12-17 23:16
 **/
public class ToyDuck extends Duck {

    public ToyDuck() {
        super.setFlyBehaviour(new NoFlyBehaviour());
    }

}
