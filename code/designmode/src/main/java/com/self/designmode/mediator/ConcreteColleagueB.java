package com.self.designmode.mediator;

/**
 * 具体同事类
 * @author PJ_ZHANG
 * @create 2020-12-16 22:54
 **/
public class ConcreteColleagueB extends Colleague {

    public ConcreteColleagueB(String name) {
        super(name);
    }

    @Override
    void sendMsg(String receiverType, String msg) {
        System.out.println(getName() + " 发送消息 {" + msg + "} 到 " + receiverType);
        getMediator().relay(getColleagueType(), receiverType, msg);
    }

    @Override
    void receiverMsg(String senderType, String msg) {
        System.out.println(getName() + " 接收到" + senderType + " 发送的消息: " + msg);
    }

    @Override
    String getColleagueType() {
        return "B";
    }

}
