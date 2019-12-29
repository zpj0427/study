package com.self.netty.netty.dispackage.deal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TCP拆包粘包问题展示_服务端启动类
 * @author pj_zhang
 * @create 2019-12-28 12:39
 **/
public class DispackageServer {

    public static void main(String[] args) {
        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();
        try {
            // 初始化启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossEventLoopGroup, workerEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            // 添加解码器
                            channelPipeline.addLast(new ProtocolDecoder());
                            // 添加编码器, 用于返回数据
                            channelPipeline.addLast(new ProtocolEncoder());
                            // 添加自定义处理器
                            channelPipeline.addLast(new DispackageServerHandler());
                        }
                    });
            // 启动
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            System.out.println("服务端启动成功...");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }
    }

}
