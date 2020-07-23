package com.self.designmode.factory.simple;

/**
 * @author pj_zhang
 * @create 2020-07-23 22:42
 **/
public class Client {

    public static void main(String[] args) {
        // 要一杯可乐
        Product product = ProductFactory.createProduct("cola");
        product.show();
        // 要一个汉堡
        product = ProductFactory.createProduct("hamburger");
        product.show();
        // 再来一个炸鸡
        product = ProductFactory.createProduct("chicken");
        product.show();
    }

}
