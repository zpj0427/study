package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NIOClientSync {

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
		socketChannel.register(selector, SelectionKey.OP_READ);
		System.out.println("客户端初始化完成...");
		// 异步读数据
		read();
		write(socketChannel);
	}

	private void write(SocketChannel socketChannel) throws IOException {
		Scanner scanner = new Scanner(System.in);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		while (scanner.hasNextLine()) {
			String msg = scanner.nextLine();
			if (null == msg) {
				continue;
			}
			if ("EXIT".equalsIgnoreCase(msg)) {
				break;
			}
			byteBuffer.clear();
			byteBuffer.put(msg.getBytes());
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
		}
		scanner.close();
	}

	public void read() {
		new Thread(() -> {
			try {
				while (selector.select() > 0) {
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						SelectionKey selectionKey = iterator.next();
						process(selectionKey);
						iterator.remove();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void process(SelectionKey selectionKey) throws Exception {
		if (selectionKey.isReadable()) {
			System.out.println("客户端接收服务端响应数据");
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			ByteBuffer buffer = ByteBuffer.allocate(256);
			int read = socketChannel.read(buffer);
			buffer.flip();
			System.out.println("服务端响应数据: " + new String(buffer.array(), 0, read));
		}
	}

	public static void main(String[] args) throws Exception {
		new NIOClientSync().init();
	}

}
