package com.self.designmode.decorator;

/**
 * 装饰者: 装饰者类,加豆浆
 * @author PJ_ZHANG
 * @create 2020-07-28 18:26
 **/
public class Soy extends Decorator {
    public Soy(IDrink drink) {
        super(drink);
        setDes("加豆浆...");
        setPrice(3);
    }
}
