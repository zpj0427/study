package com.self.designmode.singleton;

/**
 * 枚举方式创建
 * @author PJ_ZHANG
 * @create 2020-07-23 15:21
 **/
public class Enum {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(EnumSingleton.INSTANCE.hashCode())).start();
        }
    }
}

enum EnumSingleton {
    INSTANCE
}
