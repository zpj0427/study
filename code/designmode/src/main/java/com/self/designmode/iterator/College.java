package com.self.designmode.iterator;

import java.util.Iterator;

/**
 * 学校顶层接口
 * @author PJ_ZHANG
 * @create 2020-12-11 17:30
 **/
public interface College {

    void addDepartment(Department department);

    Iterator iterator();

}
