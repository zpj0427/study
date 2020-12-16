package com.self.designmode.observer;

/**
 * @author PJ_ZHANG
 * @create 2020-12-16 16:22
 **/
public class Client {

    public static void main(String[] args) {
        // 创建发布者
        SubjectImpl subject = new SubjectImpl();
        // 创建观察者
        Observer baiduObserver = new BaiDuObserver();
        Observer meiTuanObserver = new MeiTuanObserver();
        // 注册观察者到发布者
        subject.addObserver(baiduObserver);
        subject.addObserver(meiTuanObserver);
        // 发布者改变数据
        subject.changeData(10, 20, 40);
    }

}
