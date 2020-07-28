package com.self.designmode.decorator;

/**
 * @author PJ_ZHANG
 * @create 2020-07-28 18:21
 **/
public class Decaf extends IDrink {
    public Decaf() {
        setDes("无因咖啡...");
        setPrice(30);
    }
    @Override
    int cost() {
        System.out.println(getDes() + " : " + getPrice());
        return getPrice();
    }
}
