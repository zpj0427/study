package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

/**
 * NIO网络通信客户端_非阻塞式
 * 1, 通道
 * 2, 缓冲区
 * 3, 选择器
 * 
 * @author Administrator
 *
 */
public class NonBlockIOClient {

	public void send() throws IOException {
		// 打开通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
		// 切换非阻塞模式
		socketChannel.configureBlocking(false);
		// 分配缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 发送数据给服务端
		byteBuffer.put(LocalDateTime.now().toString().getBytes());
		byteBuffer.flip();
		socketChannel.write(byteBuffer);
		byteBuffer.clear();
		// 释放资源
		socketChannel.close();
	}

	public static void main(String[] args) throws IOException {
		new NonBlockIOClient().send();
	}

}
