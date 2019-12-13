package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockIOClient {

	public void reveive() throws IOException {
		// 初始化通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
		FileChannel fileChannel = FileChannel.open(Paths.get("F:\\test.txt"), StandardOpenOption.READ);
		System.out.println("NIO CLIENT INIT SUCCESS...");
		// 初始化缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 读取本地文件, 发送数据
		for (; fileChannel.read(byteBuffer) != -1;) {
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			byteBuffer.clear();
		}
		System.out.println("NIO CLIENT SEND MESSAGE SUCCESS..");
		fileChannel.close();
		socketChannel.close();
	}

	public void reveiveAndSend() throws IOException {
		// 初始化通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
		FileChannel fileChannel = FileChannel.open(Paths.get("F:\\test.txt"), StandardOpenOption.READ);
		System.out.println("NIO CLIENT INIT SUCCESS...");
		// 初始化缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 读取本地文件, 发送数据
		for (; fileChannel.read(byteBuffer) != -1;) {
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			byteBuffer.clear();
		}
		// 非阻塞模式下, 使用shutDown标识客户端发送完毕
		socketChannel.shutdownOutput();
		System.out.println("NIO CLIENT SEND MESSAGE SUCCESS..");
		// 读取从服务端响应回来的数据
		for (; socketChannel.read(byteBuffer) != -1;) {
			byteBuffer.flip();
			System.out.println(new String(byteBuffer.array(), "UTF-8"));
			byteBuffer.clear();
		}
		fileChannel.close();
		socketChannel.close();
		System.out.println("NIO CLIENT SUCCESS...");
	}

	public static void main(String[] args) throws IOException {
		new BlockIOClient().reveiveAndSend();
	}

}
