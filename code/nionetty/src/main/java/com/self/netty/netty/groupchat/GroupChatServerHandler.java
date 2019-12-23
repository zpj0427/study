package com.self.netty.netty.groupchat;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Netty群聊系统_服务端处理器
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午4:49:18
 */
public class GroupChatServerHandler extends ChannelInboundHandlerAdapter {

	// 定义Channel组, 管理所有的Channel
	private final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 表示连接建立, 一旦连接, 第一个被执行
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// 获取客户端通道
		Channel channel = ctx.channel();
		// 通知其他客户端用户上线
		CHANNEL_GROUP
				.writeAndFlush(Unpooled.copiedBuffer((channel.remoteAddress() + ": 加入群聊").getBytes(CharsetUtil.UTF_8)));
		// 添加到群组中
		CHANNEL_GROUP.add(ctx.channel());
	}

	/**
	 * 断开连接
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// 获取客户端通道
		Channel channel = ctx.channel();
		// 通知其他客户端用户上线
		CHANNEL_GROUP.writeAndFlush(
				Unpooled.copiedBuffer((channel.remoteAddress() + ": 已经不在了...").getBytes(CharsetUtil.UTF_8)));
	}

	/**
	 * 表示Channel出于活动状态
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + ": 上线");
	}

	/**
	 * 表示Channel出于非活动状态
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + ": 已经下线了~~~~");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 获取当前Channel
		Channel channel = ctx.channel();
		ByteBuf buf = (ByteBuf) msg;
		String inputMessage = getMessage(buf);
		// 遍历Channel组, 根据不同的情况, 传递不同的消息
		CHANNEL_GROUP.forEach(ch -> {
			// 通知到其他客户端
			if (ch != channel) {
				ch.writeAndFlush(Unpooled.copiedBuffer(
						(channel.remoteAddress() + "说: " + inputMessage).getBytes(Charset.forName("UTF-8"))));
			}
		});
		System.out.println(channel.remoteAddress() + "说: " + inputMessage);
	}

	/**
	 * 获取数据成功
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("读取数据成功...");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public String getMessage(ByteBuf byteBuf) {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		return new String(bytes, CharsetUtil.UTF_8);
	}

}
