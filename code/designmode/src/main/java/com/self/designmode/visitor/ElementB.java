package com.self.designmode.visitor;

/**
 * 具体元素类, 用于访问者访问
 * @author PJ_ZHANG
 * @create 2020-12-10 17:20
 **/
public class ElementB implements IElement {

    private String name;

    public ElementB(String name) {
        this.name = name;
    }

    /**
     * 接受访问者访问自己
     * @param visitor
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.viewElementB(this);
    }

    @Override
    public String getName() {
        return name;
    }

}
