package com.self.designmode.composite;

/**
 * 叶子节点: Leaf
 * @author PJ_ZHANG
 * @create 2020-07-29 12:50
 **/
public class Leaf extends OrgComponent {
    public Leaf(String name, String des) {
        setName(name);
        setDes(des);
    }
    @Override
    void print() {
        System.out.println("---------------------");
        System.out.println("name: " + getName() + ", des: " + getDes());
        System.out.println("---------------------");
    }
}
