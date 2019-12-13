package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDateTime;

public class UDPClient {

	public static void main(String[] args) throws IOException {
		new UDPClient().send();
	}

	public void send() throws IOException {
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(LocalDateTime.now().toString().getBytes());
		buffer.flip();
		datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 8080));
		buffer.clear();
		datagramChannel.close();
	}

}
