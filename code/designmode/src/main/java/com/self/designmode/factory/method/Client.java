package com.self.designmode.factory.method;

/**
 * @author pj_zhang
 * @create 2020-07-23 23:14
 **/
public class Client {

    public static void main(String[] args) {
        // 来一个炸鸡
        IProductFactory productFactory = new ChickenFactory();
        productFactory.createProduct().show();
        // 来一个汉堡
        productFactory = new HamburgerFactory();
        productFactory.createProduct().show();
        // 来一杯可乐
        productFactory = new ColaFactory();
        productFactory.createProduct().show();
    }

}
