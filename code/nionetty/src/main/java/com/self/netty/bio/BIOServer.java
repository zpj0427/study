package com.self.netty.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO服务端代码
 * 
 * @author pj_zhang
 * @date 2019年12月13日 上午11:39:17
 */
public class BIOServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// 创建线程池, 进行连接处理
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		// 创建 ServerSocket, 并启动8080端口
		ServerSocket serverSocket = new ServerSocket(8080);
		System.out.println("服务端启动成功, 准备接收消息...");
		for (;;) {
			// 此处阻塞接收客户端链接, 接收到后形成一个Socket套接字
			Socket socket = serverSocket.accept();
			// 直接通过线程池分配线程进行处理
			executorService.execute(() -> {
				try {
					StringBuilder sb = new StringBuilder();
					// 通过输入流读取客户端传递数据
					byte[] bytes = new byte[1024];
					InputStream inputStream = socket.getInputStream();
					int len = 0;
					// 此处如果用循环, 会阻塞一直获取, 需要通过明显的结束字符来控制
					// 方便起见, if玩吧
					if ((len = inputStream.read(bytes)) != -1) {
						sb.append(new String(bytes, 0, len));
					}
					String reveiveMsg = "reveive message: " + sb.toString();
					System.out.println(reveiveMsg);
					// 接收到数据后, 组装返回结果进行返回
					OutputStream outputStream = socket.getOutputStream();
					outputStream.write(reveiveMsg.getBytes());
					outputStream.flush();
					System.out.println("REVEIVE MESSAGE SUCCESS");
					// 关闭资源
					outputStream.close();
					inputStream.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

}
