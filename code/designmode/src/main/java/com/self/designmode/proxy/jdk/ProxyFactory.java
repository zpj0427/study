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

    public ProxyFactory(Object proxy) {
        this.proxy = proxy;
    }

    public Object getInstance() {
        // 第一次参数:目标对象的类加载器
        // 第二个参数:目标对象的接口集合, 这也是JDK动态代理必须需要是基于接口的原因
        // 第三个参数:处理器对象,真正去进行方法代理执行部分,在该接口的的实现方法中需要定义实际方法执行和功能扩展
        return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("method: " + method.getName() + "执行前...");
            // 基于Java反射的方法执行, 第一个对象参数需要的是目标类对象
            // 该对象如果给代理类对象, 则会构成死循环, 一直触发方法执行,
            // 因为代理对象的方法执行会走到这部分,然后再触发一次代理对象的方法执行,依次循环
            Object result = method.invoke(this.proxy, args);
            System.out.println("method: " + method.getName() + "执行后...");
            return result;
        });
    }

}
