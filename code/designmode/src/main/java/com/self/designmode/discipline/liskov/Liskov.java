package com.self.designmode.discipline.liskov;

/**
 * 设计模式七大基础原则_里式替换原则
 * @author pj_zhang
 * @create 2020-07-16 23:20
 **/
public class Liskov {

    public static void main(String[] args) {
        // 按道理是调的同一个方法, 但是有不同的结果
        new A().func_1(1, 2);
        new B().func_2(1, 2);
    }

    static class Base {
        // 向上抽取一个基类, 可能会实现一些公共方法
    }

    static class A extends Base {
        // A类自有方法
        public void func_1(int num1, int num2) {
            System.out.println("result: " + (num1 + num2));
        }
    }

    static class B extends Base {

        private A a = new A();

        // B类自有方法
        public void func_2(int num1, int num2) {
            System.out.println("result: " + (num1 - num2));
        }

        // B类组合使用A类的方法
        public void func_1(int num1, int num2) {
            a.func_1(num1, num2);
        }
    }

}
