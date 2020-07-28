package com.self.designmode.decorator;

/**
 * 装饰者: 顶层装饰者类
 * @author PJ_ZHANG
 * @create 2020-07-28 18:22
 **/
public class Decorator extends IDrink {
    private IDrink drink;
    public Decorator(IDrink drink) {
        this.drink = drink;
    }
    @Override
    int cost() {
        System.out.println(this.getDes() + " : " + this.getPrice());
        return drink.cost() + this.getPrice();
    }
}
