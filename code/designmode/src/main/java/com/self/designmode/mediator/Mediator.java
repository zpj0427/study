package com.self.designmode.mediator;

/**
 * 中介者顶层抽象类
 * @author PJ_ZHANG
 * @create 2020-12-16 22:46
 **/
public abstract class Mediator {

    /**
     * 同事类注册
     * @param colleague 注册的同事对象
     */
    abstract void registry(Colleague colleague);

    /**
     * 同事类消息传递
     * @param senderType 消息发送者
     * @param receiverType 消息接受者
     * @param msg 消息内容
     */
    abstract void relay(String senderType, String receiverType, String msg);

}
