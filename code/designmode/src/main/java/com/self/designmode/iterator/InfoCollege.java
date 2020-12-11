package com.self.designmode.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 信息工程学院
 * @author PJ_ZHANG
 * @create 2020-12-11 17:31
 **/
public class InfoCollege implements College {

    // 构造学院下专业集合
    private List<Department> lstDepartment;

    public InfoCollege() {
        lstDepartment = new ArrayList<>(10);
    }

    /**
     * 添加专业
     * @param department
     */
    @Override
    public void addDepartment(Department department) {
        lstDepartment.add(department);
    }

    /**
     * 生成信息工程学院遍历的迭代器,准备进行遍历
     * @return
     */
    @Override
    public Iterator iterator() {
        return new InfoCollegeIterator(lstDepartment);
    }
}
