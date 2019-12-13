package com.self.netty.nio.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

public class NIOClient {

	private Selector selector;

	// 初始化客户端
	private void init() throws Exception {
		// 初始化客户端通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
		// 并设置为非阻塞
		socketChannel.configureBlocking(false);
		// 初始化选择器
		selector = Selector.open();
		// 注册通道到选择器上, 并初始化状态为可读和可写
		socketChannel.register(selector, SelectionKey.OP_WRITE);
		System.out.println("客户端初始化完成...");
		start();
	}

	private void start() throws Exception {
		while (selector.select() > 0) {
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				process(selectionKey);
				iterator.remove();
			}
		}
	}

	private void process(SelectionKey selectionKey) throws Exception {
		// 处理客户端写数据
		if (selectionKey.isWritable()) {
			System.out.println("客户端发送数据到服务端");
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			ByteBuffer buffer = ByteBuffer.allocate(256);
			buffer.put(LocalDateTime.now().toString().getBytes());
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
			// 数据写到服务端后, 修改客户端状态为可读
			socketChannel.register(selector, SelectionKey.OP_READ);
		// 处理客户端读数据
		} else if (selectionKey.isReadable()) {
			System.out.println("客户端接收服务端响应数据");
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			ByteBuffer buffer = ByteBuffer.allocate(256);
			int read = socketChannel.read(buffer);
			buffer.flip();
			System.out.println("服务端响应数据: " + new String(buffer.array(), 0, read));
			// 数据读完后, 修改客户端状态为可写
			socketChannel.register(selector, SelectionKey.OP_WRITE);
		}
	}

	public static void main(String[] args) throws Exception {
		new NIOClient().init();
	}

}
