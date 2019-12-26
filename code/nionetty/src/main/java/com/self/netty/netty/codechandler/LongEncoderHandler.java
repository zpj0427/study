package com.self.netty.netty.codechandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author LiYanBin
 * @create 2019-12-26 11:17
 **/
public class LongEncoderHandler extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("编码数据..., msg: " + msg);
        // 写数据到服务端
        out.writeLong(msg);
    }
}
