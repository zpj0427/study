package com.self.datastructure.linked;

import lombok.Data;

import java.util.Scanner;

/**
 * 约瑟夫问题解决代码
 * @author LiYanBin
 * @create 2020-01-09 15:45
 **/
public class JosephuQuestion {

    public static void main(String[] args) {
        // 初始化游戏玩家
        JosephuLinkedList linkedList = new JosephuLinkedList();
        for (int i = 0; i < 10; i++) {
            linkedList.add(new Node(i));
        }
        linkedList.startGame();
    }

}

class JosephuLinkedList {

    // 该头节点表示有效节点
    private Node head = null;

    // 统计总数
    private int totalCount = 0;

    // 开始游戏
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        // 默认从头号玩家开始
        Node startNode = head;
        for(;;) {
            if (totalCount == 0) {
                System.out.println("编号全部移除, 游戏结束...");
                return;
            }
            System.out.println("当前游戏人数: " + totalCount);
            String input = scanner.nextLine();
            switch (input) {
                case "s": // show
                    showDetails();
                    break;
                case "r": // run
                    System.out.println("输入跳过的人数: M");
                    int runCount = scanner.nextInt();
                    // 移除数据
                    // 从1号开始数两位, 则移除3号, 下一次从4号开始玩
                    // 此处获取要移除数据的前一位
                    Node targetPreNode = startNode;
                    for (int i = 0; i < runCount - 1; i++, targetPreNode = targetPreNode.getNext());
                    // 记录, 获取移除数据的下一个数据, 此处需要两次next
                    startNode = targetPreNode.getNext().getNext();
                    System.out.println("移除完成..., 移除编号: " + targetPreNode.getNext().getNo() + ", 下次开始编号: " + startNode.getNo());
                    // 直接移除, 将该节点的next 指向目标节点的next, 将目标节点挂空
                    targetPreNode.setNext(targetPreNode.getNext().getNext());
                    totalCount--;
                    // 移除, 此处不需要调移除, 可以直接处理调
                    // remove(targetNode.getNo());
                    break;
                case "e": //exit
                    return;
                default:
                    break;
            }
        }
    }

    // 添加元素
    public void add(Node node) {
        // 设置为头结点
        if (null == head) {
            totalCount++;
            head = node;
            head.setNext(head);
            return;
        }
        // 与头节点进行比较
        Node temp = head;
        for (;null != temp;) {
            // 从头节点开始, 每次获取到下一个节点进行判断
            Node next = temp.getNext();
            if (head == next) { // 与头节点相等, 说明已经到有效链表末尾
                totalCount++;
                temp.setNext(node);
                node.setNext(head);
                return;
            }
            temp = temp.getNext();
        }
    }

    public void remove(int no) {
        if (null == head) return;
        // 头结点处理
        if (head.getNo() == no) {
            // 链表只有当前数据, 直接清空
            if (head == head.getNext()) {
                head = null;

            } else {
                // 链表存在其他数据, 移除头节点后遍历
                Node preHead = head;
                head = head.getNext();
                Node temp = head;
                for (; null != temp; ) {
                    // 再遇头节点
                    if (preHead == temp.getNext()) {
                        temp.setNext(head);
                        break;
                    }
                    temp = temp.getNext();
                }
            }
        } else { // 非头节点处理
            Node temp = head;
            for (;null != temp;) {
                Node next = temp.getNext();
                // 无论是否存在, 再遇头结点直接移除
                if (next == head) {
                    return;
                }
                // 存在, 直接移除
                if (next.getNo() == no) {
                    temp.setNext(next.getNext());
                    break;
                }
                temp = temp.getNext();
            }
        }
        totalCount--;
    }

    public void showDetails() {
        if (null == head) {
            System.out.println("数据为空...");
            return;
        }
        Node temp = head;
        boolean flag = false;
        for (;null != temp;) {
            if (flag && temp == head) {
                System.out.println("遍历完成...");
                return;
            }
            flag = true;
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    public Node getHead() {
        return head;
    }

}

@Data
class Node {

    // 编号
    private int no;

    // 下一个节点
    private Node next;

    public Node(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "[Node: no = " + no + "]";
    }

}
