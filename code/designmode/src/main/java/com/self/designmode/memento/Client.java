package com.self.designmode.memento;

/**
 * 备忘录模式:
 * @author PJ_ZHANG
 * @create 2020-12-17 10:51
 **/
public class Client {

    public static void main(String[] args) {
        // 创建原对象
        Origination origination = new Origination(100, 100);
        // 生成备忘对象并保存
        Caretaker caretaker = new Caretaker();
        caretaker.addMemento(new Memento(origination));
        System.out.println("初始状态...");
        origination.show();
        // 战斗
        origination.setDef(80);
        origination.setVit(80);
        System.out.println("战斗完成状态...");
        origination.show();
        // 恢复
        origination.recoverMemento(caretaker.getMemento(0));
        System.out.println("恢复初始状态...");
        origination.show();
    }

}
