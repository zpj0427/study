package com.self.designmode.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 14:46
 **/
public class Client {

    public static void main(String[] args) throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory(new TargetProxy());
        IProxy instance = (IProxy) proxyFactory.getInstance();
        instance.realMethod("JDK动态代理");
        // com.sun.proxy.$Proxy0
        System.out.println(instance.getClass().getName());
        // 打印出Proxy文件
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", TargetProxy.class.getInterfaces());
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\$Proxy0.class");
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}
