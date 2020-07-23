package com.self.designmode.singleton;

/**
 * 饿汉式加载, 包括两种方式:
 * * 静态常量加载
 * * 静态代码块加载
 * @author PJ_ZHANG
 * @create 2020-07-23 14:23
 **/
public class DirectLoading {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(StaticCodeBlock.getInstance())).start();
        }
    }

}

class StaticCodeBlock {
    private StaticCodeBlock() {}

    private static StaticCodeBlock singletion;

    // 静态代码块加载
    static {
        singletion = new StaticCodeBlock();
    }

    public static StaticCodeBlock getInstance() {
        return singletion;
    }
}

class StaticField {

    private StaticField() {}

    // 常量加载
    private static final StaticField SINGLETON = new StaticField();

    public static StaticField getInstance() {
        return SINGLETON;
    }

}
