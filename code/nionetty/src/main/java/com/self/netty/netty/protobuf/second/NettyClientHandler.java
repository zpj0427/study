package com.self.netty.netty.protobuf.second;

import com.self.netty.netty.protobuf.first.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Random;

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
		MyDataInfo.MyMessage myMessage = null;
		int random = new Random().nextInt(2);
		// 初始化Student
		if (0 == random) {
			myMessage = MyDataInfo.MyMessage.newBuilder()
					// 此处传递Student对应的dataType
					.setDataType(MyDataInfo.MyMessage.DataType.StudentType)
					// 此处初始化Student
					// Student初始化方式就是单对象初始化方式
					.setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("张三").build())
					.build();
		// 初始化Worker
		} else {
			myMessage = MyDataInfo.MyMessage.newBuilder()
					// 此处传递Worker对应的dataType
					.setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
					// 此处初始化Worker
					.setWorker(MyDataInfo.Worker.newBuilder().setAge(1).setName("老李").build())
					.build();
		}
		ctx.writeAndFlush(myMessage);
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
