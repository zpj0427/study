package com.self.netty.netty.groupchat;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty群聊系统_客户端处理器
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午5:16:52
 */
public class GroupChatClientHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 初始化
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("当前客户连接: " + ctx.channel().remoteAddress());
		String inputMessage = ctx.channel().localAddress().toString();
		ctx.channel().writeAndFlush(Unpooled.copiedBuffer(inputMessage.getBytes(Charset.forName("UTF-8"))));
	}

	/**
	 * 读取数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("接收到读取的数据");
		System.out.println("接收读取数据: " + getMessage((ByteBuf) msg));
	}

	public String getMessage(ByteBuf byteBuf) {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		return new String(bytes, CharsetUtil.UTF_8);
	}

}
