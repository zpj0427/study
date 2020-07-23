package com.self.designmode.factory.abstaract;

import com.self.designmode.factory.method.ChickenFactory;
import com.self.designmode.factory.method.ColaFactory;
import com.self.designmode.factory.method.HamburgerFactory;
import com.self.designmode.factory.simple.Product;

/**
 * 产品族具体工厂类: 中国工厂
 * @author pj_zhang
 * @create 2020-07-23 23:37
 **/
public class ChinaFactory implements IFactory {
    @Override
    public Product createChicken() {
        return new ChickenFactory().createProduct();
    }

    @Override
    public Product createHamburger() {
        return new HamburgerFactory().createProduct();
    }

    @Override
    public Product createCola() {
        return new ColaFactory().createProduct();
    }
}
