package com.self.netty.netty.demo;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * NETTY_定时任务队列处理
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyScheduleTaskHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 倒计时执行, 与execute()任务串行执行,
		ctx.channel().eventLoop().schedule(() -> {
			System.out.println("定时任务执行...");
		}, 3, TimeUnit.SECONDS);
		// 发起异步后, 主线程不会阻塞, 会直接执行
		System.out.println("channelRead() 执行完成");
	}

	/**
	 * 数据读取处理完成后, 返回响应结果到客户端
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 打印接收完成数据
		ctx.writeAndFlush(Unpooled.copiedBuffer("channelReadComplete...", Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.channel().close();
		// 打印异常
		cause.printStackTrace();
	}

}
