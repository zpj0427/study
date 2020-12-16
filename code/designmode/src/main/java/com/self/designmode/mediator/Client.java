package com.self.designmode.mediator;

/**
 * @author PJ_ZHANG
 * @create 2020-12-16 23:03
 **/
public class Client {

    public static void main(String[] args) {
        // 构建中介者
        Mediator mediator = new ConcreteMediator();
        // 非单例模式可以指定类型, 比如用用户编号制定
        // 构建同事类
        Colleague colleagueA = new ConcreteColleagueA("A");
        Colleague colleagueB = new ConcreteColleagueB("B");
        // 注册同事类到中介者
        mediator.registry(colleagueA);
        mediator.registry(colleagueB);
        // A发送消息给B
        colleagueA.sendMsg(colleagueB.getColleagueType(), "A发送给B");
        // B发送消息给A
        colleagueB.sendMsg(colleagueA.getColleagueType(), "B发送给A");
    }

}
