package com.self.netty.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * Buffer缓冲区: 在Java NIO中负责进行数据存储, 本质就是一个数组
 *
 * @author Administrator
 *
 */
public class SelfBuffer {

	/**
	 * 直接缓冲区
	 */
	@Test
	public void directBuffer() {
		// 通过 allocateDirect() 创建直接缓冲区
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		// 判断是否直接缓冲区
		System.out.println(buffer.isDirect());
	}

	/**
	 * 简单的Buffer操作, 非直接缓冲区
	 */
	@Test
	public void simpleBuffer() {
		// 初始化
		ByteBuffer buffer = ByteBuffer.allocate(5);
		showDetails("allocate()", buffer);
		// 写数据到Buffer中
		buffer.put("12".getBytes());
		showDetails("put()", buffer);
		// 进行读写状态转换, 准备读数据
		buffer.flip();
		showDetails("flip()", buffer);
		// 读取三个字符数据
		byte[] bytes = new byte[1024];
		buffer.get(bytes, 0, 1);
		System.out.println("读取到数据 : " + new String(bytes));
		showDetails("get()", buffer);
		// 进行读取为标记,
		buffer.mark();
		showDetails("mark()", buffer);
		// 标记操作位置完成后, 继续调用get(), 操作位置后移
		System.out.println(new String(new byte[] { buffer.get() }));
		showDetails("mark() + get()", buffer);
		// 调用reset(), 操作位置回到初始标记位置
		buffer.reset();
		showDetails("reset()", buffer);
		// 调用get(), 操作位置继续后移, 此处等于把mark和reset阶段的数据重复读取
		System.out.println(new String(new byte[] { buffer.get() }));
		showDetails("reset() + get()", buffer);
		// 判断缓冲区中是否还有数据
		if (buffer.hasRemaining()) {
			System.out.println("当前还存在数据: " + buffer.remaining());
			bytes = new byte[1024];
			buffer.get(bytes, 0, buffer.remaining());
			System.out.println("一次性全读取: " + new String(bytes));
		}
		// 归零操作, 初始化position值为0, 从起点开始重新处理
		// 注意此时limit值不变
		buffer.rewind();
		showDetails("rewind()", buffer);
		// 清空数据, 此处假清空, 只修改三个标志位数据, 没有真正清数据
		buffer.clear();
		showDetails("clear()", buffer);
	}

	private void showDetails(String operate, ByteBuffer buffer) {
		System.out.println(operate + " : capacity = " + buffer.capacity());
		System.out.println(operate + " : position = " + buffer.position());
		System.out.println(operate + " : limit = " + buffer.limit());
	}

	@Test
	public void readOnly() {
		// 初始化缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(5);
		// 存储数据到缓冲区
		buffer.put("a".getBytes());
		// 设置缓冲区为只读
		buffer = buffer.asReadOnlyBuffer();
		// 进行读写转换
		buffer.flip();
		// 读取数据, 读取数据正常
		System.out.println(new String(new byte[] {buffer.get()}));
		// 写数据, 因为已经设置只读, 写数据报ReadOnlyBufferException异常
		buffer.put("123".getBytes());
	}

	@Test
	public void cast() {
		// 初始化缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(5);
		// 存储一个 short 数据
		buffer.putShort((short) 1);
		buffer.flip();
		// 通过 long 类型获取, 会报BufferUnderflowException异常
		System.out.println(buffer.getLong());
	}

}
