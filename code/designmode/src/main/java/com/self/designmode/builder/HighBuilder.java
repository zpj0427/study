package com.self.designmode.builder;

/**
 * 具体建造类: 高楼大厦建造
 * @author pj_zhang
 * @create 2020-07-25 16:37
 **/
public class HighBuilder extends AbstractBuilder {
    @Override
    protected void buildHeight(int height) {
        house.setHeight(height);
    }

    @Override
    protected void buildSize(int size) {
        house.setSize(size);
    }
}
