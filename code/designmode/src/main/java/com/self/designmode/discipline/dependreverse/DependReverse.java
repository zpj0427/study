package com.self.designmode.discipline.dependreverse;

/**
 * 设计模式七大原则_依赖倒转原则
 * @author pj_zhang
 * @create 2020-07-16 21:40
 **/
public class DependReverse {

    public static void main(String[] args) {
        // 客户端的调用方法完全一致
        // 但是对于实现层来讲, 已经进行了代码的整合
        Person person = new Person();
        person.receive(new WeChat());
        person.receive(new Email());
    }

    // 此次我们先创建一个通讯的顶层接口
    interface ICommunication {
        // 在该接口中提供一个抽象的统一方法, 读消息
        // 所有通讯方式都实现该接口并实现该方法
        // 在方法中实现各自的通讯方式
        void readMessage();
    }

    static class Email implements ICommunication {
        @Override
        public void readMessage() {
            System.out.println("邮件收发消息...");
        }
    }

    static class WeChat implements ICommunication {
        @Override
        public void readMessage() {
            System.out.println("微信收发消息...");
        }
    }

    static class Person {
        // 在用户接受消息的时候, 让细节依赖抽象
        // 通过面向对象的多态传递, 我们不用知道该通讯方式到底是什么方式
        // 我们只需要知道, 它一定重写了通讯方法, 直接获取内容即可
        public void receive(ICommunication communication) {
            communication.readMessage();
        }
    }

}
