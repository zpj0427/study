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
        ctx.writeAndFlush("123");
        // 如果发送数据数据长度, 超出解码的数据长度, 解码端会被多次调用
        // 发送类型必须与指定编码器要编码的类型一致, 不然编码器不会执行
        // 具体参考编码器父类.write()方法
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String response = (String) msg;
        System.out.println("客户端响应数据: " + response);
    }
}
