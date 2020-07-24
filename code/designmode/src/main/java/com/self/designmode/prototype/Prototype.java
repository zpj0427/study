package com.self.designmode.prototype;

/**
 * 原型模式,
 * * 分为深拷贝和浅拷贝两种
 * @author PJ_ZHANG
 * @create 2020-07-24 17:11
 **/
public class Prototype {

    public static void main(String[] args) throws CloneNotSupportedException {
        ShallowCopy shallowCopy = new ShallowCopy();
        ShallowCopy inner = new ShallowCopy();
        inner.setName("inner张三");
        shallowCopy.setName("张三");
        shallowCopy.setInner(inner);
        // 浅拷贝
        // ShallowCopy copy = (ShallowCopy) shallowCopy.clone();
        // 深拷贝
        ShallowCopy copy = shallowCopy.deepCopy();
        System.out.println("原对象    : " + shallowCopy.getName() + ",      地址: " + shallowCopy);
        System.out.println("拷贝对象  : " + copy.getName() + ",      地址: " + copy);
        ShallowCopy copyIn = copy.getInner();
        System.out.println("原内对象  : " + inner.getName() + ", 地址: " + inner);
        System.out.println("拷贝内对象: " + copyIn.getName() + ", 地址: " + copyIn);
        // 修改名称
        copyIn.setName("Update");
        System.out.println(inner.getName());
        System.out.println(copyIn.getName());
    }
}
