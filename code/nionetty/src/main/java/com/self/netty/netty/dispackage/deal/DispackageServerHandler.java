package com.self.netty.netty.dispackage.deal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author pj_zhang
 * @create 2019-12-28 12:43
 **/
public class DispackageServerHandler extends SimpleChannelInboundHandler<MyProtocol> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {
        // 接收客户端数据
        System.out.println("接收次数: " + (++count));
        System.out.println("长度: " + myProtocol.getLength());
        System.out.println("内容: " + myProtocol.getContent());
        // 响应数据回去
        MyProtocol responseProtocol = new MyProtocol();
        String responseMessage = "receive message";
        responseProtocol.setLength(responseMessage.length());
        responseProtocol.setContent(responseMessage);
        channelHandlerContext.writeAndFlush(responseProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
