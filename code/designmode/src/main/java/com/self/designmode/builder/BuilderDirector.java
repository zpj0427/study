package com.self.designmode.builder;

/**
 * 指挥类: 建造指挥类
 * @author pj_zhang
 * @create 2020-07-25 16:39
 **/
public class BuilderDirector {
    AbstractBuilder abstractBuilder;

    public BuilderDirector(AbstractBuilder builder) {
        this.abstractBuilder = builder;
    }

    public Product build(int height, int size) {
        abstractBuilder.buildHeight(height);
        abstractBuilder.buildSize(size);
        return abstractBuilder.build();
    }
}
