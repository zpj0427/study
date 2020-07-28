package com.self.designmode.decorator;

/**
 * @author PJ_ZHANG
 * @create 2020-07-28 18:20
 **/
public class Espresso extends IDrink {
    public Espresso() {
        setDes("意氏咖啡...");
        setPrice(30);
    }
    @Override
    int cost() {
        System.out.println(getDes() + " : " + getPrice());
        return getPrice();
    }
}
