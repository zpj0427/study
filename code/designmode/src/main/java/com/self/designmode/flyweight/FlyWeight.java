package com.self.designmode.flyweight;

/**
 * 享元模式: 具体角色
 * @author PJ_ZHANG
 * @create 2020-08-22 21:20
 **/
public class FlyWeight implements IFlyWeight {

    private String type = "";

    private UnsharedFlyWeight unsharedFlyWeight = null;

    public FlyWeight(String type) {
        this.type = type;
    }

    @Override
    public void use() {
        System.out.println("享元具体角色: " + type);
        if (null != unsharedFlyWeight) {
            unsharedFlyWeight.use();
        }
    }

    @Override
    public void setUnsharedFlyWeight(UnsharedFlyWeight unsharedFlyWeight) {
        this.unsharedFlyWeight = unsharedFlyWeight;
    }

}
