package com.self.netty.netty.buffer;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuffer {

	@Test
	public void byteBuffer() {
		// 初始化一个 ByteBuf, 长度为10，即 capacity 为 10
		// Netty 中的 Buf 中不需要使用 flip() 进行转换
		// ByteBuf 内部维护了 writeIndex 和 readIndex 两个参数， 用于进行数据读写
		// 写数据：writeIndex递增；读数据：readIndex递增
		// 0 <= readIndex <= writeIndex <= capacity
		// 三个区间数据分别对应：已读数据，可读数据，已写数据
		// ByteBuf内部会自行扩容, 扩容默认最大到Integer.MAX_VALUE
		// 触发扩容后, 会直接扩容长度到64, 如果已经超过64, 则继续左移一位处理
		ByteBuf byteBuf = Unpooled.buffer(10);
		System.out.println(byteBuf.getClass());
		// 写数据
		for (int i = 0; i < 10; i++) {
			byteBuf.writeByte(i);
		}
		// 读数据
		byteBuf.readByte();
		// 继续写数据
		byteBuf.writeByte(0);
		System.out.println(byteBuf.capacity());
	}

}
