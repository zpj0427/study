package com.self.designmode.chain;

/**
 * 职责链模式: 顶层抽象类, 定义责任链及请求处理方式的抽象类
 * @author PJ_ZHANG
 * @create 2020-12-18 11:33
 **/
public abstract class Approver {

    /**
     * 下一个处理器
     */
    private Approver nextApprover;

    public Approver getNextApprover() {
        return nextApprover;
    }

    public void setNextApprover(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    /**
     * 事件处理的抽象类
     * @param request
     */
    public abstract void processRequest(Request request);
}
