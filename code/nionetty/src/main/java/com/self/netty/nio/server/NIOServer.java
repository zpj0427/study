package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 完整NIO服务端
 * 
 * @author Administrator
 *
 */
public class NIOServer {

	private Selector selector;

	public void init() throws Exception {
		// 初始化服务端通道, 并绑定端口
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 设置非阻塞
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress(8080));
		// 初始化选择器
		selector = Selector.open();
		// 绑定通道到选择器上, 并初始化为可接收链接
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("初始化服务端成功, 监听端口: " + 8080);
		// 开始进行处理
		start();
	}

	private void start() throws Exception {
		// 存在已经注册就绪的事件
		while (selector.select() > 0) {
			// 获取就绪的所有事件
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			// 遍历事件进行处理
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				// 处理数据
				process(selectionKey);
				iterator.remove();
			}
		}
	}

	private void process(SelectionKey selectionKey) throws IOException {
		if (selectionKey.isAcceptable()) {
			// 从服务链接中获取到客户端连接通道, 并注册为可读
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			System.out.println("服务端接收到一个客户端链接请求, 并注册为读事件, 准备读取客户端数据数据...");
		} else if (selectionKey.isReadable()) {
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			// 初始化缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocate(128);
			int read = socketChannel.read(byteBuffer);
			byteBuffer.flip();
			String content = new String(byteBuffer.array(), 0, read);
			System.out.println("服务端接收到客户端消息, 消息内容为: " + content);
			// 携带一个attach, 准备进行返回
			SelectionKey key = socketChannel.register(selector, SelectionKey.OP_WRITE);
			key.attach("服务端返回数据: " + content);
		} else if (selectionKey.isWritable()) {
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			String content = (String) selectionKey.attachment();
			System.out.println("服务端返回数据为: " + content);
			selectionKey.attach(null);
			if (null == content) {
				socketChannel.register(selector, SelectionKey.OP_READ);
				return;
			}
			// 初始化缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocate(128);
			// 写数据到客户端
			byteBuffer.put(content.getBytes());
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			byteBuffer.clear();
			System.out.println("服务端响应客户端数据完成...");
			socketChannel.register(selector, SelectionKey.OP_READ);
		}
	}

	public static void main(String[] args) throws Exception {
		new NIOServer().init();
	}

}
