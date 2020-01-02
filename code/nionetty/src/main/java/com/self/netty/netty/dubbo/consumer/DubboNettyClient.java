package com.self.netty.netty.dubbo.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pj_zhang
 * @create 2019-12-31 0:25
 **/
public class DubboNettyClient {

    //创建线程池
    private ExecutorService executor = Executors.newFixedThreadPool(20);

    private DubboNettyHandler handler;

    public Object getBean(Class<?> clazz) {

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> {
                    if (handler == null) {
                        initClient();
                    }
                    //设置要发给服务器端的信息
                    handler.initParam(clazz.getTypeName(), method.getName(), (String) args[0]);
                    return executor.submit(handler).get();
               });
    }

    //初始化客户端
    private void initClient() {
        handler = new DubboNettyHandler();
        //创建EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(handler);
                        }
                    }
                );

        try {
            bootstrap.connect("127.0.0.1", 8080).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 两个小时的排错提示, 此处不关流, 因为上面没有阻塞`
        // 现在时间: 2019年12月31日01:34:14
        // 给本人的蠢流一个记号...
    }

}
