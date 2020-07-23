package com.self.designmode.factory.simple;

/**
 * 商品创建工厂类
 * @author pj_zhang
 * @create 2020-07-23 22:39
 **/
public class ProductFactory {

    public static Product createProduct(String type) {
        if ("chicken".equals(type)) {
            return new Chicken();
        } else if ("cola".equals(type)) {
            return new Cola();
        } else if ("hamburger".equals(type)) {
            return new Hamburger();
        }
        return null;
    }

}
