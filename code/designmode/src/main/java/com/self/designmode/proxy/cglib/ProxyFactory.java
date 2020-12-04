package com.self.designmode.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 14:48
 **/
public class ProxyFactory implements MethodInterceptor {

    private Object targetProxy;

    public ProxyFactory(Object targetProxy) {
        this.targetProxy = targetProxy;
    }

    public Object getInstance() {
        // 创建工具列
        Enhancer enhancer = new Enhancer();
        // 传递父类
        enhancer.setSuperclass(targetProxy.getClass());
        // 设置回调, 即MethodInterceptor的实现类
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB 执行前...");
        Object result = method.invoke(targetProxy, objects);
        System.out.println("CGLIB 执行后...");
        return result;
    }
}
