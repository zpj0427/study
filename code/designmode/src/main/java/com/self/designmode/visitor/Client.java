package com.self.designmode.visitor;

/**
 * 访问者模式:客户端
 * @author PJ_ZHANG
 * @create 2020-12-10 17:29
 **/
public class Client {

    public static void main(String[] args) {
        // 创建元素集合类
        ElementStructure elementStructure = new ElementStructure();
        // 添加元素
        elementStructure.addElement(new ElementA("AAAAA"));
        elementStructure.addElement(new ElementB("BBBBB"));

        // 创建两个访问者
        IVisitor visitorA = new VisitorA();
        IVisitor visitorB = new VisitorB();

        // 访问元素
        elementStructure.show(visitorA);
        elementStructure.show(visitorB);
    }

}
