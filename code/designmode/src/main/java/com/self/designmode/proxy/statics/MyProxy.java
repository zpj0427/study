package com.self.designmode.proxy.statics;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author PJ_ZHANG
 * @create 2021-05-13 16:17
 **/
public class MyProxy {

    public static void main(String[] args) {
        // 1.实际对象
        IProxy targetProxy = new TargetProxy();
        // 2.代理对象
        IProxy proxy = (IProxy) Proxy.newProxyInstance(targetProxy.getClass().getClassLoader(),
                targetProxy.getClass().getInterfaces(),
                new InvocationHandler() {
            // 3.通过匿名内部类实现了 InvocationHandler 的接口方法
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 5.代理对象执行方法后, 不会直接进入调用的方法, 会先进入该方法中
                // 该方法参数的 method 表示代理对象调用的方法 realMethod
                // 参数 args 表示代理对象调用方法传递的参数 执行方法...
                // 参数 proxy 就是代理对象本身

                // 6.进入该方法后, 就需要实际执行方法了, 实际执行方法是执行实际对象的方法
                // 此时方法是 method, 实际对象是第一步的targetProxy
                // 通过反射直接执行即可
                return method.invoke(targetProxy, args);
            }
        });
        // 4. 代理对象执行方法
        proxy.realMethod("执行方法...");
    }

}
