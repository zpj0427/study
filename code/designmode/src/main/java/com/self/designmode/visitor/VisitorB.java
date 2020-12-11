package com.self.designmode.visitor;

/**
 * 具体访问者类, 用于访问元素
 * @author PJ_ZHANG
 * @create 2020-12-10 17:22
 **/
public class VisitorB implements  IVisitor{

    @Override
    public void viewElementA(ElementA elementA) {
        System.out.println("B访问者 访问 A元素: " + elementA.getName());
    }

    @Override
    public void viewElementB(ElementB elementB) {
        System.out.println("B访问者 访问 B元素: " + elementB.getName());
    }

}
