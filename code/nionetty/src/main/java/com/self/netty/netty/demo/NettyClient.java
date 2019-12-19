package com.self.netty.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty客户端
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:58:42
 */
public class NettyClient {

	public static void main(String[] args) {
		// 创建线程组
		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			// 创建客户端核心处理类
			Bootstrap bootstrap = new Bootstrap();
			// 绑定线程组
			bootstrap.group(eventLoopGroup)
					// 绑定客户端通道实现类
					.channel(NioSocketChannel.class)
					// 绑定业务处理器
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyClientHandler());
						}
					});
			// 启动服务端
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
			System.out.println("CLIENT START SUCCESS...");
			// 监听关闭通道
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}
