package com.self.netty.netty.dubbo.common;

/**
 * 业务类
 * @author pj_zhang
 * @create 2019-12-30 22:04
 **/
public class HelloService implements IHelloService {

    @Override
    public String hello(String msg) {
        System.out.println("====receive client message: " + msg);
        return "receive: " + msg;
    }

}
