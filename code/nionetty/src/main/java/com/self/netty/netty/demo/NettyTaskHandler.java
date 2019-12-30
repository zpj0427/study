package com.self.netty.netty.demo;

import java.nio.charset.Charset;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * NETTY_任务队列处理
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyTaskHandler extends ChannelInboundHandlerAdapter {

	static EventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(16);

	/**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 对于业务执行较长的任务, 可以自定义任务队列进行处理
		// 接收任务后直接发起任务队列, 即异步执行
		// 之后channelRead()执行完成, 会继续执行channelReadComplete()
		// 等业务代码真正执行完成后, 再次提示客户端
		System.out.println("主线程: " + Thread.currentThread().getName());
		// 发起执行任务, 实际是将任务添加到 NioEventLoop 的 taskQueue 属性中,
		// taskQueue 中的线程对象会顺序执行, 也就是说当前定义的两个异步任务, 会依次执行, 共6S执行完成
		// 而不是并行执行3S完成
		ctx.channel().eventLoop().execute(() -> {
			try {
				Thread.sleep(3 * 1000);
				// 此处主线程与异步线程其实是同一个线程
				// execute会添加该线程任务到任务队列
				// 主线程执行完逻辑后, 会继续执行任务队列中任务
				// 执行任务队列时,直接调用run()方法, 不会重新启动线程
				System.out.println("异步线程: " + Thread.currentThread().getName());
				ctx.channel().writeAndFlush(Unpooled.copiedBuffer("channelRead_1...", Charset.forName("UTF-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		for (int i = 0; i < 40; i++) {
			eventExecutors.execute(() -> {
				try {
					Thread.sleep(3 * 1000);
					System.out.println("Event Executor Group: " + Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
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
