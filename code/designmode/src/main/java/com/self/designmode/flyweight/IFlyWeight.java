package com.self.designmode.flyweight;

/**
 * 享元模式顶层接口
 * @author PJ_ZHANG
 * @create 2020-08-22 21:16
 **/
public interface IFlyWeight {

    void use();

    void setUnsharedFlyWeight(UnsharedFlyWeight unsharedFlyWeight);

}
