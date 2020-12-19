package com.self.designmode.chain;

/**
 * 职责链模式: 院长处理器
 * @author PJ_ZHANG
 * @create 2020-12-18 11:36
 **/
public class SchoolMasterApprover extends Approver {

    private String name;

    public SchoolMasterApprover(String name) {
        this.name = name;
    }

    @Override
    public void processRequest(Request request) {
        if (request.getPrice() > 10000) {
            System.out.println("校长处理完成: " + request.getPrice());
        } else {
            getNextApprover().processRequest(request);
        }
    }
}
