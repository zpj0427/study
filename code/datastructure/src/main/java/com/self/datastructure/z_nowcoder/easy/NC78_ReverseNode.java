package com.self.datastructure.z_nowcoder.easy;

/**
 * NC78: 反转链表
 * 输入一个链表，反转链表后，输出新链表的表头。
 * 输入: {1,2,3}
 * 输出: {3,2,1}
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 15:52
 **/
public class NC78_ReverseNode {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        reverseList(listNode);
        for (;null != listNode;) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode listNode = head;
        if (null == listNode) {
            return listNode;
        }
        // 取第一个作为最后一个节点
        ListNode rootNode = new ListNode(listNode.val);
        ListNode nextNode = listNode.next;
        while (null != nextNode) {
            ListNode preNode = new ListNode(nextNode.val);
            preNode.next = rootNode;
            rootNode = preNode;
            nextNode = nextNode.next;
        }
        return rootNode;
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }

}
