package com.self.netty.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.nio.charset.Charset;

/**
 * NETTY_服务器对应Handler代码 定义Handler, 需要继承Netty定义好的适配器
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyPublishServerHandler extends ChannelInboundHandlerAdapter {

	private NettyPublishServer nettyPublishServer;

    public NettyPublishServerHandler(NettyPublishServer nettyPublishServer) {
    	this.nettyPublishServer = nettyPublishServer;
    }

    /**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		System.out.println("client address: " + ctx.channel().remoteAddress());
		// 将msg转换为ByteBuf
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("msg: " + buf.toString(Charset.forName("UTF-8")));
		System.out.println("curr client connect: " + nettyPublishServer.getLstSocketChannel().size());
		// 给每一个连接发送变更消息
		for (SocketChannel socketChannel : nettyPublishServer.getLstSocketChannel()) {
			if (socketChannel == ctx.channel()) {
				continue;
			}
			System.out.println("server send message to : " + socketChannel.remoteAddress());
			socketChannel.writeAndFlush(Unpooled.copiedBuffer((" server receive message from " + ctx.channel().remoteAddress() + " and publish it").getBytes("UTF-8")));
		}
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
