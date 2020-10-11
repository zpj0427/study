package com.self.designmode.flyweight;

/**
 * 享元模式: 特殊角色
 * @author PJ_ZHANG
 * @create 2020-08-22 21:26
 **/
public class UnsharedFlyWeight implements IFlyWeight {

    private String type = "";

    private UnsharedFlyWeight unsharedFlyWeight = null;

    public UnsharedFlyWeight(String type) {
        this.type = type;
    }

    @Override
    public void use() {
        System.out.println("享元特殊角色: " + type);
        if (null != unsharedFlyWeight) {
            unsharedFlyWeight.use();
        }
    }

    @Override
    public void setUnsharedFlyWeight(UnsharedFlyWeight unsharedFlyWeight) {
        this.unsharedFlyWeight = unsharedFlyWeight;
    }

}
