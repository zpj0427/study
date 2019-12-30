package com.self.netty.netty.dubbo.consumer;

import com.alibaba.fastjson.JSON;
import com.self.netty.netty.dubbo.common.HelloService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author pj_zhang
 * @create 2019-12-31 0:26
 **/
public class DubboNettyHandler extends SimpleChannelInboundHandler<String> implements Callable {

    private Map<String, Object> param = new HashMap<>();

    private ChannelHandlerContext ctx;

    private String result;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(" channelRead 被调用  ");
        result = msg.toString();
        notify(); //唤醒等待的线程
    }

    public void initParam(String typeName, String name, String arg) {
        param.put("beanName", typeName);
        param.put("methodName", name);
        param.put("message", arg);
    }

    @Override
    public synchronized Object call() throws Exception {
        System.out.println(" call1 被调用  ");
        ctx.writeAndFlush(JSON.toJSONString(param));
        //进行wait
        wait(); //等待channelRead 方法获取到服务器的结果后，唤醒
        System.out.println(" call2 被调用  ");
        return  result; //服务方返回的结果
    }
}
