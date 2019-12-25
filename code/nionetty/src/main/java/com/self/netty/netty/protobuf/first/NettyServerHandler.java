package com.self.netty.netty.protobuf.first;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * NETTY_服务器对应Handler代码 定义Handler, 需要继承Netty定义好的适配器
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		StudentPOJO.Student student = (StudentPOJO.Student) msg;
		System.out.println("服务端接收到消息: " + student.getId() + ", " + student.getName());
	}

	/**
	 * 数据读取处理完成后, 返回响应结果到客户端
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将数据写入缓冲区
		ctx.writeAndFlush(Unpooled.copiedBuffer("has received message...", Charset.forName("UTF-8")));
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
