package com.self.netty.netty.codechandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author LiYanBin
 * @create 2019-12-26 11:17
 **/
public class StringEncoderHandler extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        System.out.println("编码数据..., msg: " + msg);
        // 写数据到服务端
        out.writeBytes(msg.getBytes(CharsetUtil.UTF_8));
    }
}
