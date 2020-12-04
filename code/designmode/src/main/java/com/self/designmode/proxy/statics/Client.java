package com.self.designmode.proxy.statics;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 13:56
 **/
public class Client {

    public static void main(String[] args) {
        // 创建实际对象
        TargetProxy targetProxy = new TargetProxy();
        // 创建代理对象
        StaticProxy staticProxy = new StaticProxy(targetProxy);
        // 方法执行
        staticProxy.realMethod("执行...");
    }

}
