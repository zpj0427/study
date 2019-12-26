package com.self.netty.netty.codechandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author LiYanBin
 * @create 2019-12-26 11:10
 **/
public class LongDecoderHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("解码数据...");
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
