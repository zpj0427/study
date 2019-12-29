package com.self.netty.netty.dispackage.deal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author pj_zhang
 * @create 2019-12-28 13:46
 **/
public class ProtocolEncoder extends MessageToByteEncoder<MyProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol, ByteBuf byteBuf) throws Exception {
        // 封装byteBuf数据
        byteBuf.writeInt(myProtocol.getLength());
        byteBuf.writeCharSequence(myProtocol.getContent(), CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(byteBuf);
    }

}
