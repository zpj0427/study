package com.self.netty.netty.dispackage.deal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author pj_zhang
 * @create 2019-12-28 13:43
 **/
public class ProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        // 读取长度
        int length = byteBuf.readInt();
        // 读取内容
        String content = (String) byteBuf.readCharSequence(length, CharsetUtil.UTF_8);
        // 封装数据
        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setLength(length);
        myProtocol.setContent(content);
        list.add(myProtocol);
    }

}
