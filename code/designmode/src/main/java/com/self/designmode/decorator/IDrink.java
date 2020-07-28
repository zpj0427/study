package com.self.designmode.decorator;

import lombok.Getter;
import lombok.Setter;

/**
 * 装饰者模式: 顶层抽象类, 确保强一致
 * @author PJ_ZHANG
 * @create 2020-07-28 18:11
 **/
@Getter
@Setter
public abstract class IDrink {
    // 价格
    private int price;
    // 描述
    private String des;
    // 花费
    abstract int cost();
}
