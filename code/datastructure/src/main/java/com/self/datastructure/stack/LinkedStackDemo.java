package com.self.datastructure.stack;

import lombok.Data;

/**
 * 通过单向链表模拟栈数据结构
 * @author LiYanBin
 * @create 2020-01-10 15:15
 **/
public class LinkedStackDemo {

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack();
        for (int i = 0; i < 10; i++, linkedStack.push(i));
        for (int i = 0; i < 10; i++)
            System.out.println(linkedStack.pop().getData());
    }


    private static class LinkedStack {

        // 头结点, 该几点不虚拟
        private Node head;

        // 添加链表节点到栈中
        public void push(int data) {
            Node newNode = new Node(data);
            if (null == head) {
                head = newNode;
            } else {
                Node temp = head;
                // 获取到最后一个有效节点
                for (;null != temp.getNext(); temp = temp.getNext());
                temp.setNext(newNode);
            }
        }

        public Node pop() {
            // 节点为空处理
            if (isEmpty()) {
                throw new IndexOutOfBoundsException("链表为空...");
            }
            // 只存在头节点处理
            Node temp = head;
            if (null == head.getNext()) {
                head = null;
                return temp;
            } else {
                // 获取到尾节点的上一个节点
                for (temp = head; temp.getNext().getNext() != null; temp = temp.getNext());
                // 获取的temp表示要获取节点的前置节点
                // 返回目标节点, 并将前置节点的next为空置空
                Node resultData = temp.getNext();
                temp.setNext(null);
                return resultData;
            }
        }

        public boolean isEmpty() {
            return head == null;
        }


    }

    /**
     * 自定义链表
     */
    @Data
    private static class Node {
        private int data;

        private Node next;

        public Node(int data) {
            this(data, null);
        }

        public Node(Node node) {
            this(node.getData(), null);
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

}
