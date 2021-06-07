package com.self.designmode.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 14:46
 **/
public class Client {

    public static void main(String[] args) throws Exception {
        // 先创建一个目标类对象
        TargetProxy targetProxy = new TargetProxy();
        // 构建自定义的代理工厂, 并且把目标对象传递到工厂中
        ProxyFactory proxyFactory = new ProxyFactory(targetProxy);
        // 在工厂中进行代理对象构建
        IProxy instance = (IProxy) proxyFactory.getInstance();
        instance.realMethod("JDK动态代理");
        // com.sun.proxy.$Proxy0
        System.out.println(instance.getClass().getName());




        // 打印出Proxy文件
//        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", TargetProxy.class.getInterfaces());
//        FileOutputStream fileOutputStream = new FileOutputStream("F:\\$Proxy0.class");
//        fileOutputStream.write(bytes);
//        fileOutputStream.flush();
//        fileOutputStream.close();
    }

}
