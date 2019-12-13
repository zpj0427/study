package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class UDPServer {

	public static void main(String[] args) throws IOException {
		new UDPServer().receive();
	}

	public void receive() throws IOException {
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		datagramChannel.bind(new InetSocketAddress(8080));
		Selector selector = Selector.open();
		datagramChannel.register(selector, SelectionKey.OP_READ);
		while (selector.select() > 0) {
			System.out.println("接收到消息");
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				if (selectionKey.isReadable()) {
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					datagramChannel.receive(byteBuffer);
					byteBuffer.flip();
					System.out.println(new String(byteBuffer.array(), "UTF-8"));
				}
				iterator.remove();
			}
		}
	}

}
