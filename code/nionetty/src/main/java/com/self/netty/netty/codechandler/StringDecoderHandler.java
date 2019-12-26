package com.self.netty.netty.codechandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author LiYanBin
 * @create 2019-12-26 11:10
 **/
public class StringDecoderHandler extends ByteToMessageDecoder {

    /**
     * 如果Socket传递过来的编码数据长度超过该解码类的读取长度
     * 该解码类会被调用多次进行数据读取;
     *
     * 如果 List out 不为空, 数据内容也会被传递给下一个InboundHandler处理,
     * 同样该Handler会被调用多次,
     *
     * 如例子, 解码器通过每8个字节读取的方式读取Long型对象, 如果Socket中获取的数据长度超过8,
     * 则会多次读取, 并多次传递
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() > 0) {
            byte[] bytes = new byte[1];
            in.readBytes(1).readBytes(bytes);
            out.add(new String(bytes));
        }
        System.out.println("解码数据..." + out);
    }
}
