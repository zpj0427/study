package com.self.designmode.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 学院遍历类
 * @author PJ_ZHANG
 * @create 2020-12-11 17:35
 **/
public class PrintCollege {

    private List<College> lstCollege = new ArrayList<>(10);

    /**
     * 添加学院对象
     * @param college
     */
    public void addCollege(College college) {
        lstCollege.add(college);
    }

    /**
     * 进行遍历
     */
    public void showCollege() {
        for (College college : lstCollege) {
            Iterator iterator = college.iterator();
            showIterator(iterator);
        }
    }

    public void showIterator(Iterator iterator) {
        for (;iterator.hasNext();) {
            Department department = (Department) iterator.next();
            System.out.println(department.getName());
        }
    }

}
