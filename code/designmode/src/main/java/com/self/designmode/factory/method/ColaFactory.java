package com.self.designmode.factory.method;

import com.self.designmode.factory.simple.Cola;
import com.self.designmode.factory.simple.Hamburger;
import com.self.designmode.factory.simple.Product;

/**
 * 具体工厂:可乐工厂
 * @author pj_zhang
 * @create 2020-07-23 23:13
 **/
public class ColaFactory implements IProductFactory {
    @Override
    public Product createProduct() {
        // 如果可乐类型过多, 可继续套用简单工厂模式
        return new Cola();
    }
}
