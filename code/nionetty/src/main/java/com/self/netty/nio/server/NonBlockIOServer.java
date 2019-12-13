package com.self.netty.nio.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO网络通信服务端_非阻塞式
 * 1, 通道
 * 2, 缓冲区
 * 3, 选择器
 * 
 * @author Administrator
 *
 */
public class NonBlockIOServer {

	public void receive() throws Exception {
		// 获取通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 设置非阻塞
		serverSocketChannel.configureBlocking(false);
		// 绑定端口号
		serverSocketChannel.bind(new InetSocketAddress(8080));
		// 获取选择器
		Selector selector = Selector.open();
		// 注册通道到选择器上, 监听客户端连接状态
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		// 轮询选择器进行处理
		for (; selector.select() > 0;) {
			// 获取选择器上所有已经注册的已就绪的监听时间
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			for (; iterator.hasNext();) {
				// 遍历获取准备就绪的事件
				SelectionKey selectionKey = iterator.next();
				// 对事件就绪类型进行判断
				// 事件注册状态为连接,
				if (selectionKey.isAcceptable()) {
					// 获取客户端连接的SocketChannel
					SocketChannel socketChannel = serverSocketChannel.accept();
					// 切换非阻塞模式
					socketChannel.configureBlocking(false);
					// 将通道注册到选择器上, 并注册为读就绪
					socketChannel.register(selector, SelectionKey.OP_READ);
					System.out.println("注册完成");
				// 事件状态为读就绪
				} else if (selectionKey.isReadable()) {
					// 获取当前选择器上的读就绪通道
					SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
					// 读取数据
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					for (; socketChannel.read(buffer) != -1;) {
						buffer.flip();
						System.out.println(new String(buffer.array(), "UTF-8"));
						buffer.clear();
					}
				}
				// 时间处理完成后, 直接移除
				iterator.remove();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new NonBlockIOServer().receive();
	}

}
