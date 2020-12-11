package com.self.designmode.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 元素对象结构集合
 * @author PJ_ZHANG
 * @create 2020-12-10 17:27
 **/
public class ElementStructure {

    List<IElement> lstElement = new ArrayList<>(10);

    public void addElement(IElement element) {
        lstElement.add(element);
    }

    public void show(IVisitor visitor) {
        for (IElement element : lstElement) {
            element.accept(visitor);
        }
    }

}
