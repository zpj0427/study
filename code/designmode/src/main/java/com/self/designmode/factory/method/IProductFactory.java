package com.self.designmode.factory.method;

import com.self.designmode.factory.simple.Product;

/**
 * 抽象工厂:顶层接口
 * @author pj_zhang
 * @create 2020-07-23 23:12
 **/
public interface IProductFactory {

    Product createProduct();

}
