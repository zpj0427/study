package com.self.designmode.iterator;

/**
 * 基础部门对象, 迭代器模式的基础对象
 * @author PJ_ZHANG
 * @create 2020-12-11 17:24
 **/
public class Department {

    private String name;

    private String des;

    public Department(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}
