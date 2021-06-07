package com.self.designmode.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 14:48
 **/
public class ProxyFactory {

    private Object proxy;

    // 通过构造函数接收一个目标类对象
    public ProxyFactory(Object proxy) {
        this.proxy = proxy;
    }

    public Object getInstance() {
        // 第一次参数:目标对象的类加载器
        // 第二个参数:目标对象的接口集合, 这也是JDK动态代理必须需要是基于接口的原因
        // 第三个参数:处理器对象,真正去进行方法代理执行部分,在该接口的的实现方法中需要定义实际方法执行和功能扩展
        return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                proxy.getClass().getInterfaces(),
                new MyInvocationHandler(proxy));
    }

}
