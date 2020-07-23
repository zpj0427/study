package com.self.designmode.singleton;

/**
 * 懒加载, 懒加载也可以分为三种方式
 * * 线程不安全, 只在单线程下有效
 * * 同步代码块, 与上一个问题基本一致, 如果把同步放在外边, 又跟下一个问题一致
 * * 同步方法,
 * @author PJ_ZHANG
 * @create 2020-07-23 14:45
 **/
public class LazyLoading {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(SynCodeBlock.getInstance())).start();
        }
    }

}

class SynCodeBlock {
    private SynCodeBlock() {}

    private static SynCodeBlock synCodeBlock;

    public static SynCodeBlock getInstance() {
        // 加载if外面, 基本同步了整个方法, 与同步方法一致
        synchronized (SynCodeBlock.class) {
        if (null == synCodeBlock) {
            // 加在if判断里面, 与线程不安全的方式基本一致
            //synchronized (SynCodeBlock.class) {
                synCodeBlock = new SynCodeBlock();
            }
        }
        return synCodeBlock;
    }

}

class SynMethod {
    private SynMethod() {}

    private static SynMethod synMethod;

    public static synchronized SynMethod getInstance() {
        return null == synMethod ? synMethod = new SynMethod() : synMethod;
    }
}

class NotSafe {

    private NotSafe() {}

    private static NotSafe notSafe;

    public static NotSafe getInstance() {
        return null == notSafe ? notSafe = new NotSafe() : notSafe;
    }
}
