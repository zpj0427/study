package com.self.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳检测机制
 * @author pj_zhang
 * @create 2019-12-23 21:28
 **/
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 重写该方法, 进行心跳检测回调处理
     * @param ctx 上下文内容
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = null;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "读空闲...";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲...";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲...";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + ": " + eventType);
//            ctx.channel().close();
        }
    }
}
