package com.self.designmode.decorator;

/**
 * 装饰者: 装饰者类,加牛奶
 * @author PJ_ZHANG
 * @create 2020-07-28 18:25
 **/
public class Milk extends Decorator {
    public Milk(IDrink drink) {
        super(drink);
        setDes("加牛奶...");
        setPrice(5);
    }
}
