package com.self.designmode.template;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 18:04
 **/
public class Client {

    public static void main(String[] args) {
        CommonTemplate concreteA = new Concrete_1();
        concreteA.common();

        CommonTemplate concreteB = new Concrete_2();
        concreteB.common();
    }

}
