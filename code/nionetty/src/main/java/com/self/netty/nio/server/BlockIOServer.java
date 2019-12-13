package com.self.netty.nio.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * NIO网络通信服务_阻塞式
 * 
 * @author Administrator
 *
 */
public class BlockIOServer {

	public void send() throws Exception {
		// 初始化服务端通道连接
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 初始化文件连接, 进行文件写
		FileChannel fileChannel = FileChannel.open(Paths.get("F://test_1.txt"), StandardOpenOption.WRITE,
				StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		// 服务端通道绑定端口启动
		serverSocketChannel.bind(new InetSocketAddress(8080));
		System.out.println("NIO SERVER INIT SUCCESS...");
		// 初始化缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 服务端接收到客户端链接, 并初始化为SocketChannel
		SocketChannel socketChannel = serverSocketChannel.accept();
		// 读取到数据后并写出
		for (; socketChannel.read(byteBuffer) != -1;) {
			byteBuffer.flip();
			fileChannel.write(byteBuffer);
			byteBuffer.clear();
		}
		System.out.println("NIO SERVER RECEIVE MESSAGE SUCCESS...");
		serverSocketChannel.close();
	}

	public void sendAndReceive() throws Exception {
		// 初始化服务端通道连接
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 初始化文件连接, 进行文件写
		FileChannel fileChannel = FileChannel.open(Paths.get("F://test_1.txt"), StandardOpenOption.WRITE,
				StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		// 服务端通道绑定端口启动
		serverSocketChannel.bind(new InetSocketAddress(8080));
		System.out.println("NIO SERVER INIT SUCCESS...");
		// 初始化缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 服务端接收到客户端链接, 并初始化为SocketChannel
		SocketChannel socketChannel = serverSocketChannel.accept();
		// 读取到数据后并写出
		for (; socketChannel.read(byteBuffer) != -1;) {
			byteBuffer.flip();
			fileChannel.write(byteBuffer);
			byteBuffer.clear();
		}
		System.out.println("NIO SERVER RECEIVE MESSAGE SUCCESS...");
		// 写数据到客户端
		byteBuffer.put("server send response data".getBytes("UTF-8"));
		byteBuffer.flip();
		socketChannel.write(byteBuffer);
		socketChannel.shutdownOutput();
		System.out.println("NIO SERVER SEND MESSAGE SUCCESS...");
		fileChannel.close();
		socketChannel.close();
		System.out.println("NIO SERVER SUCCESS...");
	}

	public static void main(String[] args) throws Exception {
		new BlockIOServer().sendAndReceive();
	}

}
