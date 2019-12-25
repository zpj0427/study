package com.self.netty.netty.protobuf.second;

import com.self.netty.netty.protobuf.first.StudentPOJO;
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
		MyDataInfo.MyMessage myMessage = (MyDataInfo.MyMessage) msg;
		// 获取传递过来的DataType, 判断填充的对象
		MyDataInfo.MyMessage.DataType dataType = myMessage.getDataType();
		// 填充对象为Student, 进行Student对象处理
		if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
			System.out.println("Student: " + myMessage.getStudent().getName());
		// 传递对象为Worker, 进行Worker处理
		} else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
			System.out.println("Worker: " + myMessage.getWorker().getName());
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
