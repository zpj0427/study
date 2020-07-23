package com.self.designmode.factory.method;

import com.self.designmode.factory.simple.Chicken;
import com.self.designmode.factory.simple.Cola;
import com.self.designmode.factory.simple.Product;

/**
 * 具体工厂:炸鸡工厂
 * @author pj_zhang
 * @create 2020-07-23 23:13
 **/
public class ChickenFactory implements IProductFactory {
    @Override
    public Product createProduct() {
        // 如果炸鸡类型过多, 可继续套用简单工厂模式
        return new Chicken();
    }
}
