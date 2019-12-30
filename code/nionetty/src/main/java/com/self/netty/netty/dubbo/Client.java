package com.self.netty.netty.dubbo;

import com.self.netty.netty.dubbo.common.IHelloService;
import com.self.netty.netty.dubbo.common.HelloService;
import com.self.netty.netty.dubbo.consumer.DubboNettyClient;

/**
 * @author pj_zhang
 * @create 2019-12-30 23:03
 **/
public class Client {

    public static void main(String[] args) {
        DubboNettyClient nettyClient = new DubboNettyClient();
        IHelloService helloService = (IHelloService) nettyClient.getBean(HelloService.class);
        String response = helloService.hello("123");
        System.out.println(response);
    }

}
