package com.self.designmode.proxy.jdk;

/**
 * 实际执行类
 * @author PJ_ZHANG
 * @create 2020-12-03 13:54
 **/
public class TargetProxy implements IProxy {

    @Override
    public void realMethod(String name) {
        System.out.println("实际执行方法: " + name);
    }
}
