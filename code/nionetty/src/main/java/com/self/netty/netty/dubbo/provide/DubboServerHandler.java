package com.self.netty.netty.dubbo.provide;

import com.alibaba.fastjson.JSON;
import com.self.netty.netty.dubbo.common.HelloService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author pj_zhang
 * @create 2019-12-31 0:32
 **/
public class DubboServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        Map<String, String> map = JSON.parseObject(msg, Map.class);
        String beanName = map.get("beanName");
        String methodName = map.get("methodName");
        String message = map.get("message");
        HelloService helloService = (HelloService) Class.forName(beanName).newInstance();
        Method method = helloService.getClass().getMethod(methodName, String.class);
        String responseStr = (String) method.invoke(helloService, message);
        ctx.writeAndFlush(responseStr);
    }

}
