package com.self.netty.netty.demo;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty客户端处理类
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午11:13:50
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 通道就绪触发
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		ctx.writeAndFlush(Unpooled.copiedBuffer("HELLO SERVER", Charset.forName("UTF-8")));
	}

	/**
	 * 读取服务端返回的数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		System.out.println("Remote Address: " + ctx.channel().remoteAddress());
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("reveive msg: " + buf.toString(Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}

}
