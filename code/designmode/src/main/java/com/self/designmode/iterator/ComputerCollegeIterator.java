package com.self.designmode.iterator;

import java.util.Iterator;

/**
 * 计算机学院迭代器
 * @author PJ_ZHANG
 * @create 2020-12-11 17:25
 **/
public class ComputerCollegeIterator implements Iterator {

    private Department[] departmentArr;

    private int index = 0;

    public ComputerCollegeIterator(Department[] departmentArr) {
        this.departmentArr = departmentArr;
    }

    @Override
    public boolean hasNext() {
        // 如果当前索引以后到最后了, 则条件不成立,返回false
        // 如果数组中有一个元素, 则index = 0 == 1 - 1 = 0, 成立, 可以取第0个
        // 此处if判断不严谨, 没有加数组长度, 说明问题即可
        if (index <= departmentArr.length - 1 && null != departmentArr[index]) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        // 取当前元素数据, 并推进索引位置
        return departmentArr[index++];
    }
}
