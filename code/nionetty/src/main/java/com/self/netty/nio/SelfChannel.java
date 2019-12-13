package com.self.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 通道, 负责数据传输, 但是通道本身并不能进行数据传输, 需要通过缓冲区传递
 * 
 * @author Administrator
 *
 */
public class SelfChannel {

	/**
	 * 编解码处理
	 * 
	 * @throws Exception
	 */
	@Test
	public void charset() throws Exception {
		Charset charset = Charset.forName("UTF-8");
		// 编码器
		CharsetEncoder encoder = charset.newEncoder();
		// 解码器
		CharsetDecoder decoder = charset.newDecoder();
		// 初始化CharBuffer缓冲区
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		charBuffer.put("测试编解码");
		charBuffer.flip();
		// 编码为ByteBuffer
		ByteBuffer byteBuffer = encoder.encode(charBuffer);
		for (; byteBuffer.hasRemaining();) {
			System.out.print(byteBuffer.get() + "  ");
		}
		System.out.println();
		// 解码
		byteBuffer.flip();
		charBuffer = decoder.decode(byteBuffer);
		System.out.println(charBuffer.toString());
	}

	/**
	 * 分散读取, 聚集写入
	 */
	@Test
	public void scatterAndAggregated() throws Exception {
		/* 分散读取 */
		// 创建文件并授权
		RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\test.txt", "rw");
		// 获取通道
		FileChannel inChannel = randomAccessFile.getChannel();
		// 构造缓冲区, 构造分散缓冲区
		ByteBuffer bufferFirst = ByteBuffer.allocate(128);
		ByteBuffer bufferSecond = ByteBuffer.allocate(1024);
		ByteBuffer[] lstBuffers = { bufferFirst, bufferSecond };
		// 进行分散读取
		inChannel.read(lstBuffers);
		// 解析数据
		for (ByteBuffer buffer : lstBuffers) {
			// 从读状态转为写状态, 并输出
			buffer.flip();
			System.out.println(
					"初始化长度: " + buffer.capacity() + ", 结果数据: " + new String(buffer.array(), 0, buffer.limit()));
		}
		/*******************************************************************/
		/* 聚集写入 */
		RandomAccessFile accessFile = new RandomAccessFile("F://2.txt", "rw");
		FileChannel outChannel = accessFile.getChannel();
		outChannel.write(lstBuffers);
		// 关闭资源
		inChannel.close();
		outChannel.close();
		randomAccessFile.close();
		accessFile.close();
	}

	/**
	 * 通道搭载内存映射缓冲区直接操作文件
	 */
	@Test
	public void txtFileOperate() throws Exception {
		// 创建文件并授权
		RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\test.txt", "rw");
		// 打开通道
		FileChannel fileChannel = randomAccessFile.getChannel();
		// 获取内存映射缓冲区
		MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 1024);
		mappedByteBuffer.clear();
		// 对缓冲区操作, 会直接同步到文件
		mappedByteBuffer.put(0, (byte) 97);
		mappedByteBuffer.put(1023, (byte) 122);
		randomAccessFile.close();
		fileChannel.close();
	}

	/**
	 * 利用通道直接进行数据传输
	 */
	@Test
	public void channelFileCopy() throws Exception {
		// 获取读通道
		FileChannel inChannel = FileChannel.open(Paths.get("F:\\1.jpg"), StandardOpenOption.READ);
		// 获取写通道
		FileChannel outChannel = FileChannel.open(Paths.get("F:\\2.jpg"), StandardOpenOption.WRITE,
				StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		// 直接进行通道传输
//		outChannel.transferFrom(inChannel, 0, inChannel.size());
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inChannel.close();
		outChannel.close();
	}

	/**
	 * 利用通道完成文件复制_直接缓冲区
	 */
	@Test
	public void directFileCopy() throws Exception {
		// 获取读通道
		FileChannel inChannel = FileChannel.open(Paths.get("F:\\1.jpg"), StandardOpenOption.READ);
		// 获取写通道
		FileChannel outChannel = FileChannel.open(Paths.get("F:\\2.jpg"), StandardOpenOption.WRITE,
				StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		// 获取内存映射对应的缓冲区
		// MappedByteBuffer 存储在物理内存中
		MappedByteBuffer inMappedByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedByteBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		// 直接通过缓冲区进行读写
		byte[] bytes = new byte[inMappedByteBuffer.limit()];
		inMappedByteBuffer.get(bytes);
		outMappedByteBuffer.put(bytes);
		inChannel.close();
		outChannel.close();
	}

	/**
	 * 利用通道完成文件复制_非直接缓冲区
	 */
	@Test
	public void fileCopy() throws Exception {
		// 初始化流
		FileInputStream inputStream = new FileInputStream("F:\\1.jpg");
		FileOutputStream outputStream = new FileOutputStream("F:\\2.jpg");
		// 从流中获取通道
		FileChannel inChannel = inputStream.getChannel();
		FileChannel outChannel = outputStream.getChannel();
		// 初始化化缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		// 通过通道, 从流中读数据到缓冲区
		while (inChannel.read(buffer) != -1) {
			// 切换为写状态
			buffer.flip();
			// 将缓冲区中的数据写出去
			outChannel.write(buffer);
			// 初始化状态, 进行重新读取
			buffer.clear();
		}
		// 关资源
		outputStream.flush();
		inChannel.close();
		outChannel.close();
		outputStream.close();
		inputStream.close();
		System.out.println("执行完成...");
	}

}
