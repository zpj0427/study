package com.self.designmode.iterator;

import java.util.Iterator;

/**
 * 计算机学院
 * @author PJ_ZHANG
 * @create 2020-12-11 17:31
 **/
public class ComputerCollege implements College {

    // 构造学院下专业集合
    private Department[] departmentArr;

    // 推进索引
    private int index = 0;

    public ComputerCollege() {
        departmentArr = new Department[5];
    }

    /**
     * 添加专业
     * @param department
     */
    @Override
    public void addDepartment(Department department) {
        departmentArr[index++] = department;
    }

    /**
     * 生成计算机学院遍历的迭代器,准备进行遍历
     * @return
     */
    @Override
    public Iterator iterator() {
        return new ComputerCollegeIterator(departmentArr);
    }
}
