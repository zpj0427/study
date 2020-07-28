package com.self.designmode.decorator;

/**
 * 装饰者:被装饰者类
 * @author PJ_ZHANG
 * @create 2020-07-28 18:18
 **/
public class LongBlack extends IDrink {
    public LongBlack() {
        setDes("美氏咖啡...");
        setPrice(20);
    }
    @Override
    int cost() {
        System.out.println(getDes() + " : " + getPrice());
        return getPrice();
    }
}
