package com.self.designmode.proxy.statics;

/**
 * 代理方法
 * @author PJ_ZHANG
 * @create 2020-12-03 13:54
 **/
public class StaticProxy implements IProxy {

    private IProxy proxy;

    public StaticProxy(IProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void realMethod(String name) {
        System.out.println("静态代码执行...");
        proxy.realMethod(name);
        System.out.println("静态代理执行完成...");
    }
}
