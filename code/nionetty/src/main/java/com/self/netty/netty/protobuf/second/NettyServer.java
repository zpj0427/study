package com.self.netty.netty.protobuf.second;

import com.self.netty.netty.protobuf.first.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

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
						// 服务单添加 ProtoBuf 解码器
						// 解码器需要指定解码对象
						// 此处指定管理对象的对象
						socketChannel.pipeline().addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
						// 添加自定义处理器
						socketChannel.pipeline().addLast(new NettyServerHandler());
					}
				});
			// 启动Netty服务, 并绑定端口
			ChannelFuture cf = serverBootstrap.bind(8080).sync();
			System.out.println("NETTY SERVER START SUCCESS...");
			// 添加监听
			cf.addListener((ChannelFutureListener) channelFuture -> {
				// 状态为成功
                if (channelFuture.isSuccess()) {
                    System.out.println("启动成功...");
                } else {
                    System.out.println("启动失败...");
                }
            });
			// 对关闭通道进行监听
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
