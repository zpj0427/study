package com.self.designmode.singleton;

/**
 * 静态内部类
 * @author PJ_ZHANG
 * @create 2020-07-23 15:18
 **/
public class StaticInner {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(OuterClass.getInstance())).start();
        }
    }
}

class OuterClass {
    private OuterClass() {}

    public static OuterClass getInstance() {
        return InnerClass.OUTER_CLASS;
    }

    static class InnerClass {
        private static final OuterClass OUTER_CLASS = new OuterClass();
    }
}
