package com.self.designmode.flyweight;

/**
 * 享元模式
 * @author PJ_ZHANG
 * @create 2020-08-22 21:02
 **/
public class Client {

    public static void main(String[] args) {
        FlyWeight flyWeight = FlyWeightFactory.getFlyWeight("新闻");
        UnsharedFlyWeight unsharedFlyWeight = FlyWeightFactory.getUnsharedFlyWeight("特殊");
        flyWeight.setUnsharedFlyWeight(unsharedFlyWeight);
        flyWeight.use();
        System.out.println("----------------------------");
        flyWeight = FlyWeightFactory.getFlyWeight("博客");
        unsharedFlyWeight = FlyWeightFactory.getUnsharedFlyWeight("特殊");
        flyWeight.setUnsharedFlyWeight(unsharedFlyWeight);
        flyWeight.use();
        System.out.println("----------------------------");
        flyWeight = FlyWeightFactory.getFlyWeight("小程序");
        unsharedFlyWeight = FlyWeightFactory.getUnsharedFlyWeight("特殊");
        flyWeight.setUnsharedFlyWeight(unsharedFlyWeight);
        flyWeight.use();
        System.out.println("----------------------------");
    }

}
