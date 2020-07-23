package com.self.designmode.factory.abstaract;

import com.self.designmode.factory.simple.Product;

/**
 * 产品族: 顶层接口
 * @author pj_zhang
 * @create 2020-07-23 23:35
 **/
public interface IFactory {

    Product createChicken();

    Product createHamburger();

    Product createCola();
}
