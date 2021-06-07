package com.self.designmode.proxy.jdk;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author PJ_ZHANG
 * @create 2021-05-13 14:41
 **/
public class MyInvocationHandler implements InvocationHandler {

    private Object targetObject;

    public MyInvocationHandler(Object targetObject) {
        this.targetObject = targetObject;
    }

    // Porxy: 表示这个代理对象, 注意此处是代理对象, 不是源对象
    // Method: 表示执行的方法, 你调的是哪个方法, 这里就是方法对应的对象
    // args: 方法参数, 没有就没有, 有就有
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象: " + proxy.getClass().getName());
        System.out.println("执行方法: " + method.getName());
        System.out.println("参数: " + JSON.toJSON(args));
        method.invoke(targetObject, args);
        return null;
    }
}
