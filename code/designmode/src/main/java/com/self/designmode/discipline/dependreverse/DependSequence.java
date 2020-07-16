package com.self.designmode.discipline.dependreverse;

/**
 * 顺序方式实现功能
 * @author pj_zhang
 * @create 2020-07-16 21:32
 **/
public class DependSequence {

    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
        // 增加微信方式
        person.receive(new WeChat());
    }

    static class Email {
        public void readMessage() {
            System.out.println("邮件方式发送消息...");
        }
    }

    static class WeChat {
        public void readMessage() {
            System.out.println("微信方式发送消息...");
        }
    }

    static class Person {

        // 通过通过邮件方式接收消息
        public void receive(Email email) {
            email.readMessage();
        }

        // 此时如果用户增加了接收方式, 如微信, 则需要进行重载
        public void receive(WeChat wechat) {
            wechat.readMessage();
        }
    }

}
