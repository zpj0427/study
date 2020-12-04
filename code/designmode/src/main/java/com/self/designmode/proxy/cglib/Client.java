package com.self.designmode.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @author PJ_ZHANG
 * @create 2020-12-03 15:53
 **/
public class Client {

    public static void main(String[] args) throws Exception {
        // 取代理对象对应的.class文件, 注意这句话一定要放前面
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "F://tmp");
        ProxyFactory proxyFactory = new ProxyFactory(new TargetProxy());
        TargetProxy targetProxy = (TargetProxy) proxyFactory.getInstance();
        targetProxy.realMethod("CGLIB...");
        System.out.println(targetProxy.getClass().getName());
    }

}
