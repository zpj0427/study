package com.self.netty.netty.dispackage.deal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author pj_zhang
 * @create 2019-12-28 12:48
 **/
public class DispackageClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送数据到服务端
        for (int i = 0; i < 5; i++) {
            MyProtocol myProtocol = new MyProtocol();
            String sendMessage = "send Message: " + i + " \t\n";
            myProtocol.setLength(sendMessage.length());
            myProtocol.setContent(sendMessage);
            ctx.writeAndFlush(myProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {
        // 接收数据
        System.out.println(myProtocol.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
