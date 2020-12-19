package com.self.designmode.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 备忘录模式:守护者
 * @author PJ_ZHANG
 * @create 2020-12-17 10:44
 **/
public class Caretaker {

    private List<Memento> lstMemento = new ArrayList<>(10);

    public void addMemento(Memento memento) {
        lstMemento.add(memento);
    }

    public Memento getMemento(int index) {
        if (lstMemento.size() <= index) {
            throw new IndexOutOfBoundsException();
        }
        return lstMemento.get(index);
    }

}
