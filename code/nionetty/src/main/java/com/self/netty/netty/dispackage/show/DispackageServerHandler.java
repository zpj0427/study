package com.self.netty.netty.dispackage.show;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author pj_zhang
 * @create 2019-12-28 12:43
 **/
public class DispackageServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        // 接收客户端数据
        // 此处接收到的客户端信息可能是已经被打包过的客户端信息
        // 所以客户端循环发送了10次, 服务端可能会在少于10的次数内读完
        // 在某几次的读取中, 会读取到客户端多次发送的数据
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println(new String(bytes, CharsetUtil.UTF_8));
        System.out.println("接收次数: " + (++count));

        // 发送数据到客户端
        ByteBuf responseByteBuf = Unpooled.copiedBuffer("server response: " + count + "\r\n", CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(responseByteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
