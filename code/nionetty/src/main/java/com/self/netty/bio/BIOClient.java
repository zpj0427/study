package com.self.netty.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * BIO客户端
 * 
 * @author pj_zhang
 * @date 2019年12月13日 下午12:21:21
 */
public class BIOClient {

	public static void main(String[] args) throws Exception {
		// 创建客户端连接, 连接服务端
		Socket socket = new Socket("127.0.0.1", 8080);
		// 获取输出流, 输出客户端数据到服务端
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(LocalDateTime.now().toString().getBytes());
		outputStream.flush();
		// 获取输入流, 读取服务端传递数据
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		if ((len = inputStream.read(bytes)) != -1) {
			System.out.println(new String(bytes, 0, len));
		}
		// 关闭资源
		socket.close();
		outputStream.close();
		inputStream.close();
	}

}
