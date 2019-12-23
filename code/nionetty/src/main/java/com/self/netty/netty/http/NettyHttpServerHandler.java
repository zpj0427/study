package com.self.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

/**
 * @author pj_zhang
 * @create 2019-12-21 21:46
 **/
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     * @param channelHandlerContext 客户端连接上下文
     * @param msg 客户端传递信息, 与类定义泛型想对应
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        // 判断 HttpObject 是否是 HttpRequest请求
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("当前类: " + this.getClass());
            // 每一次请求, 哈希不一致, 说明Handler不共享
            System.out.println("当前对象哈希: " + this.hashCode());
            System.out.println("请求路径: " + httpRequest.uri());
            // 路径过滤
            if ("/favicon.ico".equalsIgnoreCase(httpRequest.uri())) {
                return;
            }
            System.out.println("MSG 类型: " + msg.getClass());
            System.out.println("客户端远程路径: " + channelHandlerContext.channel().remoteAddress());

            // 构造客户端响应
            ByteBuf byteBuf = Unpooled.copiedBuffer("THIS IS SERVER...", Charset.forName("UTF-8"));
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, byteBuf);
            // 返回类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            // 返回长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            // 返回response
            channelHandlerContext.writeAndFlush(response);
        }
    }

}
