package com.self.designmode.factory.abstaract;

/**
 * @author pj_zhang
 * @create 2020-07-23 23:14
 **/
public class Client {

    public static void main(String[] args) {
        IFactory factory = new ChinaFactory();
        factory.createChicken().show();
        factory.createHamburger().show();
        factory.createCola().show();
    }

}
