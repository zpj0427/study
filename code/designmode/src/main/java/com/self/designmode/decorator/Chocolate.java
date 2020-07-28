package com.self.designmode.decorator;

/**
 * 装饰者: 装饰者类,加巧克力
 * @author PJ_ZHANG
 * @create 2020-07-28 18:26
 **/
public class Chocolate extends Decorator {
    public Chocolate(IDrink drink) {
        super(drink);
        setDes("加巧克力...");
        setPrice(2);
    }
}
