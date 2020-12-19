package com.self.designmode.chain;

import com.self.designmode.iterator.Department;

/**
 * 职责链模式: 主任处理器
 * @author PJ_ZHANG
 * @create 2020-12-18 11:36
 **/
public class DepartmentApprover extends Approver {

    private String name;

    public DepartmentApprover(String name) {
        this.name = name;
    }

    @Override
    public void processRequest(Request request) {
        if (request.getPrice() <= 3000) {
            System.out.println("主任处理完成: " + request.getPrice());
        } else {
            getNextApprover().processRequest(request);
        }
    }
}
