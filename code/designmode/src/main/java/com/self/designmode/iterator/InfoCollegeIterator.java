package com.self.designmode.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * 信息工程学院迭代器
 * @author PJ_ZHANG
 * @create 2020-12-11 17:25
 **/
public class InfoCollegeIterator implements Iterator {

    private List<Department> lstDepartment;

    private int index = 0;

    public InfoCollegeIterator(List<Department> lstDepartment) {
        this.lstDepartment = lstDepartment;
    }

    @Override
    public boolean hasNext() {
        // 如果当前索引以后到最后了, 则条件不成立,返回false
        // 如果数组中有一个元素, 则index = 0 == 1 - 1 = 0, 成立, 可以取第0个
        if (index <= lstDepartment.size() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        // 取当前元素数据, 并推进索引位置
        return lstDepartment.get(index++);
    }
}
