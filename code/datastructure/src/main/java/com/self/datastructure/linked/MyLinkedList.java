package com.self.datastructure.linked;

import lombok.Data;

/**
 * @author LiYanBin
 * @create 2020-01-08 14:06
 **/
public class MyLinkedList {

    public static void main(String[] args) {
        SimpleLinkedList linkedList = new SimpleLinkedList();
        linkedList.addByOrder(new LinkedNode(2, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(1, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(4, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(3, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(5, "名称", "昵称"));
        LinkedNode linkedNode = reverseLinked(linkedList.getHead(), false);
        System.out.println("123");
    }

    /**
     * 获取单向链表中有效节点的数量
     * @param head 头节点
     * @param isNeedHead 头节点是否是有效节点
     * @return
     */
    public static int getNodeLength(LinkedNode head, boolean isNeedHead) {
        if (null == head) {
            return 0;
        }
        int count = isNeedHead ? 1 : 0;
        LinkedNode temp = head;
        for (;null != temp.getNext();) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    /**
     * 获取单链表中的倒数第K个节点
     * 索引从0开始算, 即倒是第K个节点就是正数第(length - k)
     * @param head 单向链表头结点
     * @param isNeedHead 头结点是否是有效节点
     * @param descCount 倒数位置
     * @return
     */
    public static LinkedNode getIndexDataDesc(LinkedNode head, boolean isNeedHead, int descCount) {
        // 获取数量
        int count = getNodeLength(head, isNeedHead);
        if (count < descCount) {
            throw new IndexOutOfBoundsException("索引越界");
        }
        int index = count - descCount;
        LinkedNode temp = isNeedHead ? head : head.getNext();
        for (int i = 0; i < index; i++, temp = temp.getNext());
        return temp;
    }

    /**
     * 单向链表反转
     * @param head 单向链表
     * @param isNeedHead 头节点是否是有效节点
     * @return
     */
    public static LinkedNode reverseLinked(LinkedNode head, boolean isNeedHead) {
        // 获取有效数据
        LinkedNode realData = isNeedHead ? head : head.getNext();
        // 初始化返回数据
        LinkedNode reverseLinked = isNeedHead ? null : new LinkedNode(head);
        // 反转
        for (; null != realData ;) {
            if (null == reverseLinked) {
                reverseLinked = new LinkedNode(realData);
            } else {
                // 构造新节点
                LinkedNode newNode = new LinkedNode(realData);
                // 获取下一个节点
                // 非虚拟头节点
                if (isNeedHead) {
                    newNode.setNext(reverseLinked);
                    reverseLinked = newNode;
                } else { // 虚拟头节点
                    // 获取虚拟头节点的下一个节点
                    LinkedNode nextNode = reverseLinked.getNext();
                    // 把原节点挂到该节点下
                    newNode.setNext(nextNode);
                    // 把当前节点设为下一个节点
                    reverseLinked.setNext(newNode);
                }
            }
            realData = realData.getNext();
        }
        return reverseLinked;
    }

}

class SimpleLinkedList {

    // 初始化头结点
    private LinkedNode head = new LinkedNode(-1, "", "");

    // 添加数据, 直接添加不带过滤
    public void add(LinkedNode linkedNode) {
        // 赋值头节点到临时节点
        LinkedNode temp = head;
        while (null != temp.getNext()) {
            temp = temp.getNext();
        }
        // 将尾节点的下一个节点指向当前节点
        temp.setNext(linkedNode);
    }

    // 添加数据, 按照主键排序
    public void addByOrder(LinkedNode linkedNode) {
        // 赋值头几点到临时节点
        LinkedNode temp = head;
        while (null != temp.getNext()) {
            LinkedNode nextNode = temp.getNext();
            //
            if (nextNode.getNo() > linkedNode.getNo()) {
                temp.setNext(linkedNode);
                linkedNode.setNext(nextNode);
                return;
                // 相同直接进行修改
            } else if (nextNode.getNo() == linkedNode.getNo()) {
                temp.setNext(linkedNode);
                linkedNode.setNext(nextNode.getNext());
                nextNode.setNext(null); // 辅助GC
                return;
            }
            temp = nextNode;
        }
        // 将尾节点的下一个节点指向当前节点
        temp.setNext(linkedNode);
    }

    // 修改数据
    public void update(LinkedNode linkedNode) {
        LinkedNode temp = head;
        while (null != temp) {
            LinkedNode next = temp.getNext();
            if (null != next && next.getNo() == linkedNode.getNo()) {
                temp.setNext(linkedNode);
                linkedNode.setNext(next.getNext());
                next.setNext(null); // 辅助GC
                return;
            }
            temp = next;
        }
    }

    // 删除节点, 根据编号删除
    public void delete(LinkedNode linkedNode) {
        LinkedNode temp = head;
        while (null != temp.getNext()) {
            LinkedNode next = temp.getNext();
            // 找到该节点, 并把该节点的next节点指向上一个节点的next, 把该节点挂空
            if (next.getNo() == linkedNode.getNo()) {
                temp.setNext(next.getNext());
                next.setNext(null); // 辅助GC
                return;
            }
            temp = next;
        }
    }

    // 展示详情信息
    public void showDetails() {
        LinkedNode next = head.getNext();
        while (next != null) {
            System.out.println(next);
            next = next.getNext();
        }
    }

    public LinkedNode getHead() {
        return head;
    }

}

@Data
class LinkedNode {

    // 编号, 编号为主键, 不允许重复, 并需要顺序处理
    private int no;

    // 名称
    private String name;

    // 昵称
    private String nickName;

    // 单项链表
    private LinkedNode next;

    public LinkedNode(LinkedNode linkedNode) {
        this(linkedNode.getNo(), linkedNode.getName(), linkedNode.getNickName());
    }

    public LinkedNode(int no, String name, String nickName) {
        this(no, name, nickName, null);
    }

    public LinkedNode(int no, String name, String nickName, LinkedNode next) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
        this.next = next;
    }

    @Override
    public String toString() {
        return "[LinkedNode: no = " + no + ", name = " + name + ", nickName = " + nickName + "]";
    }

}
