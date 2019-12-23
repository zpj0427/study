package com.self.netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * Netty群聊系统_客户端
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午5:14:00
 */
public class GroupChatClient {

	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new GroupChatClientHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
			// 获取Channel
			Channel channel = channelFuture.channel();
			// 通过 Channel 写数据
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String inputMessage = scanner.nextLine();
				channel.writeAndFlush(Unpooled.copiedBuffer(inputMessage.getBytes(CharsetUtil.UTF_8)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}
