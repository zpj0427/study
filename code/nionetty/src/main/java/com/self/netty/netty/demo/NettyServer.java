package com.self.netty.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * NETTY_服务端代码
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午9:36:18
 */
public class NettyServer {

	public static void main(String[] args) throws Exception {
		// Group子线程数不填默认为 (CPU核数 * 2)
		// 初始化 Boss Group
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		// 初始化 Worker Group
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 初始化并配置 Netty 服务
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 设置线程组, 包括Boss线程组和Worker线程组
			serverBootstrap.group(bossGroup, workerGroup)
				// 设置Boss线程处理通道
				.channel(NioServerSocketChannel.class)
				// 设置Boss线程处理参数
				.option(ChannelOption.SO_BACKLOG, 128)
				// 设置Worker线程处理参数
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				// 设置Worker线程处理器
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						// 获取pipeline进行业务处理
						// 管道主要进行数据处理
							socketChannel.pipeline().addLast(new NettyScheduleTaskHandler());
					}
				});
			// 启动Netty服务, 并绑定端口
			ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
			System.out.println("NETTY SERVER START SUCCESS...");
			// 对关闭通道进行监听
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
