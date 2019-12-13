package com.self.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.time.LocalDateTime;

/**
 * NIO 管道
 * 
 * @author Administrator
 *
 */
public class SelfPipe {

	public static void main(String[] args) throws IOException {
		Pipe pipe = Pipe.open();

		// 通过sink发送数据
		SinkChannel sinkChannel = pipe.sink();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(LocalDateTime.now().toString().getBytes());
		buffer.flip();
		sinkChannel.write(buffer);
		buffer.clear();

		// 通过source读数据
		SourceChannel sourceChannel = pipe.source();
		sourceChannel.read(buffer);
		buffer.flip();
		System.out.println(new String(buffer.array(), "UTF-8"));
	}

}
