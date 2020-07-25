package com.self.designmode.builder;

/**
 * 抽象建造类
 * @author pj_zhang
 * @create 2020-07-25 16:35
 **/
public abstract class AbstractBuilder {

    protected Product house = new Product();

    protected abstract void buildHeight(int height);

    protected abstract void buildSize(int size);

    public Product build() {
        return house;
    }

}
