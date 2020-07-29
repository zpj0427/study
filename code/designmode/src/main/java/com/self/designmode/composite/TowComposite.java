package com.self.designmode.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 中间层级: 具体类
 * @author PJ_ZHANG
 * @create 2020-07-29 12:45
 **/
public class TowComposite extends OrgComponent {
    List<OrgComponent> lstChildComponent;
    public TowComposite(String name, String des) {
        setName(name);
        setDes(des);
        lstChildComponent = new ArrayList<>(10);
    }
    @Override
    public void add(OrgComponent component) {
        lstChildComponent.add(component);
    }
    @Override
    public void delete(OrgComponent component) {
        lstChildComponent.remove(component);
    }
    @Override
    void print() {
        System.out.println("---------------------");
        System.out.println("name: " + getName() + ", des: " + getDes());
        for (OrgComponent component : lstChildComponent) {
            System.out.println("name: " + component.getName() + ", des: " + component.getDes());
        }
        System.out.println("---------------------");
    }
}
