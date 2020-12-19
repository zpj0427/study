package com.self.designmode.strategy;

/**
 * 策略模式: 算法使用客户, 顶层抽象类
 * @author PJ_ZHANG
 * @create 2020-12-17 23:13
 **/
public abstract class Duck {

    /**
     * 飞行策略
     */
    private FlyBehaviour flyBehaviour;

    /**
     * 游泳方法, 可以提策略
     */
    public void swiming() {
        System.out.println("鸭子游泳");
    }

    /**
     * 飞行方法
     */
    public void fly() {
        // 通过定义的策略执行相对应行为
        if (null != flyBehaviour) {
            flyBehaviour.fly();
        }
    }

    /**
     * 动态变更策略方式
     * @param flyBehaviour
     */
    public void setFlyBehaviour(FlyBehaviour flyBehaviour) {
        this.flyBehaviour = flyBehaviour;
    }

}
