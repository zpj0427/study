package com.self.netty.netty.codechandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author LiYanBin
 * @create 2019-12-26 11:16
 **/
public class CodecHandlerClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端发送数据");
        // 写出一个Long数据
        ctx.writeAndFlush(123L);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Long response = (Long) msg;
        System.out.println("客户端响应数据: " + response);
    }
}
