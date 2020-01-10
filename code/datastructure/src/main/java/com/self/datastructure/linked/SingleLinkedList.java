package com.self.datastructure.linked;

import lombok.Data;

import java.util.Stack;

/**
 * 单向链表
 * @author LiYanBin
 * @create 2020-01-08 14:06
 **/
public class SingleLinkedList {

    public static void main(String[] args) {
        SLinkedList linkedList = new SLinkedList();
        linkedList.addByOrder(new LinkedNode(1, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(3, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(7, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(9, "名称", "昵称"));
        linkedList.addByOrder(new LinkedNode(10, "名称", "昵称"));

        SLinkedList secondList = new SLinkedList();
        secondList.addByOrder(new LinkedNode(2, "名称", "昵称"));
        secondList.addByOrder(new LinkedNode(4, "名称", "昵称"));
        secondList.addByOrder(new LinkedNode(5, "名称", "昵称"));
        secondList.addByOrder(new LinkedNode(6, "名称", "昵称"));
        secondList.addByOrder(new LinkedNode(8, "名称", "昵称"));
        // 反转
        // LinkedNode linkedNode = reverseLinked(linkedList.getHead(), false);
        // 逆序打印
        // showDetailsReverse(linkedList.getHead(), false);
        // 合并
        showDetails(mergeOrderNode(secondList.getHead(), linkedList.getHead()));
    }

    // 展示详情信息
    public static void showDetails(LinkedNode linkedNode) {
        LinkedNode next = linkedNode.getNext();
        while (next != null) {
            System.out.println(next);
            next = next.getNext();
        }
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
     * 每次获取下一个节点, 并重新构造该节点为头节点
     * 把之前存储的头节点置位该节点的next节点, 即一步步遍历, 一步步后推
     * @param head 单向链表
     * @param isNeedHead 头节点是否是有效节点
     * @return
     */
    public static LinkedNode reverseLinked(LinkedNode head, boolean isNeedHead) {
        // 获取有效数据
        LinkedNode realData = isNeedHead ? head : head.getNext();
        // 初始化返回数据, 不需要头节点的直接初始化虚拟节点
        LinkedNode reverseLinked = isNeedHead ? null : new LinkedNode(head);
        // 反转
        for (; null != realData ;) {
            // 构造新节点
            LinkedNode newNode = new LinkedNode(realData);
            if (null == reverseLinked) {
                reverseLinked = newNode;
            } else {
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

    /**
     * 从尾节点开始打印链表
     * 方式1:
     *     先将单链表进行翻转操作, 然后进行遍历即可
     *     该方式可能会改变原链表结构, 不建议
     *     该方式可以直接调用反转方法, 并打印
     * 方式2:
     *     利用栈数据结构, 将各个节点压入栈中
     *     利用栈先进后出的特点, 完成逆序打印
     * @param linkedNode 节点
     * @param isNeedHead 头结点是否是有效节点
     */
    public static void showDetailsReverse(LinkedNode linkedNode, boolean isNeedHead) {
        Stack<LinkedNode> stack = new Stack<>();
        LinkedNode realNode = isNeedHead ? linkedNode : linkedNode.getNext();
        if (null == realNode) {
            return;
        }
        // 遍历节点, 添加到栈中
        for (; null != realNode; ) {
            stack.push(realNode);
            realNode = realNode.getNext();
        }
        // 打印栈对象
        LinkedNode currNode = null;
        for (;stack.size() > 0; ) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 合并两个有序(顺序)链表, 默认不需要头结点
     * @param firstNode
     * @param secondNode
     */
    public static LinkedNode mergeOrderNode(LinkedNode firstNode, LinkedNode secondNode) {
        // 获取有效节点
        firstNode = firstNode.getNext();
        secondNode = secondNode.getNext();
        // 存在为空, 直接返回
        if (null == firstNode || null == secondNode) {
            return null == firstNode ? secondNode : firstNode;
        }
        // 比较节点数据
        // 用首节点编号较小的链表进行遍历,
        // 较大编号的链表进行填充, 最终返回有效节点
        return firstNode.getNo() > secondNode.getNo()
                ? doMergeOrderNode(secondNode, firstNode)
                : doMergeOrderNode(firstNode, secondNode);
    }

    public static LinkedNode doMergeOrderNode(LinkedNode firstNode, LinkedNode secondNode) {
        // 初始化头节点
        SLinkedList myLinkedList = new SLinkedList();
        // 遍历节点进行填充
        for (;null != firstNode;) {
            // first节点数据大于second节点数据, 将second节点数据置于之前
            for (;secondNode != null && firstNode.getNo() > secondNode.getNo();) {
                myLinkedList.add(new LinkedNode(secondNode));
                // 当前second已经被比较过, 向前推动一位
                secondNode = secondNode.getNext();
            }
            // 处理完成当前区间的seconde数据后, 添加first数据
            myLinkedList.add(new LinkedNode(firstNode));
            firstNode = firstNode.getNext();
        }
        // first节点遍历完成后, 如果second节点还存在数据, 全部添加到最后
        for (;null != secondNode;) {
            myLinkedList.add(new LinkedNode(secondNode));
            secondNode = secondNode.getNext();
        }
        return myLinkedList.getHead();
    }

}

class SLinkedList {

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
