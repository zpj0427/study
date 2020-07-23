package com.self.designmode.factory.method;

import com.self.designmode.factory.simple.Hamburger;
import com.self.designmode.factory.simple.Product;

/**
 * 具体工厂:汉堡包工厂
 * @author pj_zhang
 * @create 2020-07-23 23:13
 **/
public class HamburgerFactory implements IProductFactory {
    @Override
    public Product createProduct() {
        // 如果汉堡包类型过多, 可继续套用简单工厂模式
        return new Hamburger();
    }
}
