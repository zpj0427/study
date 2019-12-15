package com.self.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊服务端
 * 1, 接收客户端连接
 * 2, 对客户端上线和下线进行检测
 * 3, 客户端发送消息后, 转发显示到其他客户端
 *
 * @author pj_zhang
 * @create 2019-12-15 21:59
 **/
public class GroupChatServer {

    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 服务端通道
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * 服务端监听端口
     */
    private final int PORT = 8080;

    public GroupChatServer() throws Exception {
        // 初始化非阻塞服务端, 并绑定端口
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        // 初始化选择器
        selector = Selector.open();
        // 注册通道到选择器上, 并初始化为监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功, 监听端口: " + PORT);
    }

    public void start() throws Exception {
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 处理事件
                process(selectionKey);
                iterator.remove();
            }
        }
    }

    private void process(SelectionKey selectionKey) throws Exception {
        // 初始化链接
        if (selectionKey.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            // 获取到客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            // 注册到选择器, 并注册为可读
            socketChannel.register(selector, SelectionKey.OP_READ);
            // 服务端提示上线
            String message = socketChannel.getRemoteAddress() + " 上线了...";
            System.out.println(message);
            publishMessage(message, socketChannel);
        } else if (selectionKey.isReadable()) {
            SocketChannel socketChannel = null;
            try {
                // 读取当前客户端发送消息
                socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readCount = socketChannel.read(buffer);
                String showMessage = socketChannel.getRemoteAddress() + " 说: " + new String(buffer.array(), 0, readCount);
                System.out.println(showMessage);
                // 向其他客户端广播消息
                publishMessage(showMessage, socketChannel);
            } catch (IOException e) {
                if (null != socketChannel) {
                    // 读取消息失败, 说明客户端已经下线, 做下线处理
                    System.out.println(socketChannel.getRemoteAddress() + " 下线了...");
                    // 取消注册
                    selectionKey.cancel();
                    // 关闭通道
                    socketChannel.close();
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    private void publishMessage(String showMessage, SocketChannel socketChannel) throws IOException {
        System.out.println("服务端接收到消息, 现在进行转发...");
        // 初始化需要发送的消息为ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(showMessage.getBytes());
        // 遍历每一个注册的客户端进行消息发送
        for (SelectionKey selectionKey : selector.keys()) {
            Channel channel = selectionKey.channel();
            // 跳过自身
            if (channel instanceof SocketChannel && channel != socketChannel) {
                SocketChannel targetChannel = (SocketChannel) channel;
                // 消息发送前进行读写转换, 保证每一次都能发出有效数据
                // 如果出现多个客户端只有一个接收到, 其他没有接受到, 但是服务单正常广播了
                // 优先查看ByteBuffer问题
                byteBuffer.flip();
                targetChannel.write(byteBuffer);
                System.out.println("发送消息成功, Address: " + targetChannel.getRemoteAddress());
            }
        }
        System.out.println("服务端转发消息成功...");
    }

    public static void main(String[] args) throws Exception {
        new GroupChatServer().start();
    }

}
