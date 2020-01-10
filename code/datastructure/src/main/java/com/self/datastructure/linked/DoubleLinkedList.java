package com.self.datastructure.linked;

import lombok.Data;

/**
 * 双向链表
 * @author LiYanBin
 * @create 2020-01-09 14:21
 **/
public class DoubleLinkedList {

    public static void main(String[] args) {
        DLinkedList dLinkedList = new DLinkedList();
//        dLinkedList.add(new DoubleNode(0, "名称", "昵称"));
//        dLinkedList.add(new DoubleNode(1, "名称", "昵称"));
//        dLinkedList.add(new DoubleNode(2, "名称", "昵称"));
//        dLinkedList.add(new DoubleNode(3, "名称", "昵称"));

        dLinkedList.addByOrder(new DoubleNode(3, "名称", "昵称"));
        dLinkedList.addByOrder(new DoubleNode(2, "名称", "昵称"));
        dLinkedList.addByOrder(new DoubleNode(4, "名称", "昵称"));
        dLinkedList.addByOrder(new DoubleNode(1, "名称", "昵称"));
        dLinkedList.showDetailsFromHead();
    }

}

class DLinkedList {

    private DoubleNode head = new DoubleNode(-1, "", "");

    private DoubleNode tail = new DoubleNode(-2, "", "");

    public DLinkedList() {
        head.setNext(tail);
        tail.setPre(head);
    }

    // 根据序列号添加
    public void addByOrder(DoubleNode doubleNode) {
        // 获取有效数据
        DoubleNode realData = head.getNext();
        for (;null != realData;) {
            // 遍历到尾节点, 直接添加
            if (realData == tail) {
                add(doubleNode);
                return;
            }
            // 编号大于当前编号, 则置于该编号之前
            if (realData.getNo() > doubleNode.getNo()) {
                DoubleNode pre = realData.getPre();
                pre.setNext(doubleNode);
                doubleNode.setPre(pre);
                doubleNode.setNext(realData);
                realData.setPre(doubleNode);
                return;
            }
            realData = realData.getNext();
        }
    }

    // 添加数据到链表中, 默认添加到链表尾部
    public void add(DoubleNode doubleNode) {
        // 获取尾节点的上一个节点
        DoubleNode preNode = tail.getPre();
        // 将上一个节点置为该节点的前置节点,
        preNode.setNext(doubleNode);
        // 添加该节点的前置节点和后置节点
        doubleNode.setPre(preNode);
        doubleNode.setNext(tail);
        // 将tail节点的前置节点改为该节点
        tail.setPre(doubleNode);
    }

    // 删除指定数据
    public void remove(int no) {
        DoubleNode realData = head.getNext();
        for (;null != realData && tail != realData;) {
            // 匹配到数据, 直接移除
            if (realData.getNo() == no) {
                // 获取前置节点
                DoubleNode pre = realData.getPre();
                // 获取后置节点
                DoubleNode next = realData.getNext();
                // 互为前置后置节点, 将当前节点挂空
                pre.setNext(next);
                next.setPre(pre);
                realData = null; // 辅助GC
                return;
            }
            realData = realData.getNext();
        }
    }

    // 打印, 从头打印
    public void showDetailsFromHead() {
        DoubleNode realData = head.getNext();
        for (;null != realData && tail != realData;) {
            System.out.println(realData);
            realData = realData.getNext();
        }
    }

    // 打印, 从尾部打印
    public void showDetailsFromTail() {
        DoubleNode realData = tail.getPre();
        for (;null != realData && realData != head;) {
            System.out.println(realData);
            realData = realData.getPre();
        }
    }

    public DoubleNode getHead() {
        return head;
    }

    public DoubleNode getTail() {
        return tail;
    }

}

@Data
class DoubleNode {

    private DoubleNode pre;

    private DoubleNode next;

    private int no;

    private String name;

    private String nickName;

    public DoubleNode(DoubleNode doubleNode) {
        this(doubleNode.getNo(), doubleNode.getName(), doubleNode.getNickName());
    }

    public DoubleNode(int no, String name, String nickName) {
        this(no, name, nickName, null, null);
    }

    public DoubleNode(int no, String name, String nickName, DoubleNode pre, DoubleNode next) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
        this.pre = pre;
        this.next = next;
    }

    @Override
    public String toString() {
        return "[DoubleNode: no = " + no + ", name = " + name + ", nickName = " + nickName + "]";
    }

}
