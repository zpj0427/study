package com.self.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * NIO群聊系统_客户端
 * 1, 连接服务端
 * 2, 发送消息到服务端
 * 3, 接口服务端转发的消息
 *
 * @author pj_zhang
 * @create 2019-12-15 22:28
 **/
public class GroupChatClient {

    // 服务端IP
    private final String HOST = "127.0.0.1";

    // 服务端端口
    private final int PORT = 8080;

    private SocketChannel socketChannel;

    private Selector selector;

    public GroupChatClient() throws IOException {
        // 初始化客户端SocketChannel
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        // 初始化选择器
        selector = Selector.open();
        // 绑定事件, 绑定为读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("客户端已经准备完成, NickName: " + socketChannel.getLocalAddress());
    }

    public void start() throws IOException {
        // 接收服务端消息, 此处开线程接收, 保证读写不冲突,不会造成互相影响
        // 先开读, 再去写, 防止写造成的读执行不到
        receiveMessage();
        // 发送消息到服务端
        sendMessage();
    }

    private void receiveMessage() throws IOException {
        // 启动一个线程进行服务端数据接收
        new Thread(() -> {
            try {
                // selector.select() 会阻塞, 直到有连接进入
                while (selector.select() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    for (;iterator.hasNext();) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isReadable()) {
                            // 接收服务端消息并处理
                            SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                            readChannel.configureBlocking(false);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int readCount = readChannel.read(buffer);
                            buffer.flip();
                            System.out.println("接收服务端消息: " + new String(buffer.array(), 0, readCount));
                        }
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("客户端接收消息完成");
        }).start();
    }

    private void sendMessage() throws IOException {
        // 从控制台输入消息
        Scanner scanner = new Scanner(System.in);
        System.out.println("等待客户端输入消息: ");
        // 发送到服务端
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
			if ("EXIT".equalsIgnoreCase(message)) {
				break;
			}
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        }
		scanner.close();
    }

    public static void main(String[] args) throws IOException {
        new GroupChatClient().start();
    }

}
