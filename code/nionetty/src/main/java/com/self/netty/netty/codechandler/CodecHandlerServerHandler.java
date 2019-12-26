package com.self.netty.netty.codechandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author LiYanBin
 * @create 2019-12-26 11:12
 **/
public class CodecHandlerServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("读取来自客户端: " + ctx.channel().remoteAddress() + "的数据: " + msg);
        // 向客户端传输响应数据
        ctx.writeAndFlush(msg);
    }
}
