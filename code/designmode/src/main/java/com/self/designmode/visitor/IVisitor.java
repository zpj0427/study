package com.self.designmode.visitor;

/**
 * 访问者模式_访问者顶层接口.
 * @author PJ_ZHANG
 * @create 2020-12-10 17:18
 **/
public interface IVisitor {

    // 元素访问可以根据实际情况通过反射进行分发

    /**
     * 访问元素A
     * @param elementA
     */
    void viewElementA(ElementA elementA);

    /**
     * 访问元素B
     * @param elementB
     */
    void viewElementB(ElementB elementB);

}
