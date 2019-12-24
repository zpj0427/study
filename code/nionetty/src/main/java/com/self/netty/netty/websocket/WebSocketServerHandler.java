package com.self.netty.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * WebSocket自定义处理器
 * @author LiYanBin
 * @create 2019-12-24 10:40
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 打印消息
        System.out.println("接收到客户端消息: " + msg.text());
        // 输出消息到客户端
        ctx.writeAndFlush(new TextWebSocketFrame("服务端响应: " + msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接, 长ID: " + ctx.channel().id().asLongText());
        System.out.println("客户端连接, 端ID: " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端退出, 长ID: " + ctx.channel().id().asLongText());
        System.out.println("客户端退出, 端ID: " + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常: " + cause.getMessage());
    }
}
