package com.self.designmode.template;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 18:03
 **/
public abstract class CommonTemplate {

    public void common() {
        commonSomething();
        doSomething();
    }

    public abstract void doSomething();

    private void commonSomething() {
        System.out.println("公共方法...");
    }

}
