package com.self.designmode.visitor;

/**
 * 访问者模式_元素顶层接口, 用于提供给访问者进行访问
 * @author PJ_ZHANG
 * @create 2020-12-10 17:17
 **/
public interface IElement {

    /**
     * 接受访问者访问
     * @param visitor
     */
    void accept(IVisitor visitor);

    String getName();

}
