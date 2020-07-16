package com.self.designmode.discipline.dependreverse;

/**
 * 依赖倒转原则三种传递方式
 * @author pj_zhang
 * @create 2020-07-16 21:48
 **/
public class TransmitMode {

    public static void main(String[] args) {
        Person_2 person = new Person_2(new Alipay());
        person.receive();
    }

    interface ICommunication {
        void readMessage();
    }

    static class Alipay implements ICommunication {
        @Override
        public void readMessage() {
            System.out.println("支付宝...");
        }
    }

    // 方式三: setter传递, 在需要调用时, 通过setter方法进行传递
    static class Person_3 {
        private ICommunication communication;

        public void receive() {
            communication.readMessage();
        }

        public void setCommunication(ICommunication communication) {
            this.communication = communication;
        }
    }

    // 方式二: 构造方法传递, 通过构造细节类时, 传递调用类为抽象引用
    static class Person_2 {
        private ICommunication communication;
        public Person_2(ICommunication communication) {
            this.communication = communication;
        }
        public void receive() {
            communication.readMessage();
        }
    }

    // 方式一: 接口传递, 通过形参传递为抽象引用进行调用
    static class Person_1 {
        public void receive(ICommunication communication) {
            communication.readMessage();
        }
    }

}
