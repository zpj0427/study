package com.self.designmode.builder;

/**
 * @author pj_zhang
 * @create 2020-07-25 16:41
 **/
public class Client {
    public static void main(String[] args) {
        BuilderDirector director = new BuilderDirector(new CommonBuilder());
        Product house = director.build(80, 200);
        System.out.println(house);
    }
}
