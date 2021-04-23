package com.self.datastructure.z_nowcoder.easy;

/**
 * NC33: 合并有序链表
 * 将两个有序的链表合并为一个新链表，要求新的链表是通过拼接两个链表的节点来生成的，且合并后新链表依然有序。
 * 输入: {1},{2}
 * 输出: {1,2}
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 16:23
 **/
public class NC33_MergeOrderNode {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(-24);
        listNode.next = new ListNode(-18);
        listNode.next.next = new ListNode(6);
        listNode.next.next.next = new ListNode(16);
        ListNode listNode2 = new ListNode(-17);
        ListNode result = mergeTwoLists(listNode, listNode2);
        for (; null != result; ) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    /**
     * 合并两个有序链表到一个新的链表
     * * 首先先定义一个头结点为空的链表, 作为目标有序链表
     * * 然后对两个有序链表都不为空情况下进行比较,
     * * 将较小值加到目标链表的next位, 并递进取值链表和目标链表
     * * 将两个有序链表都不为空的情况都遍历完成后, 此时可能存在最多链表为空
     * * 将该链表直接整体添加到目标链表的 next 即可
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (null == l1) {
            return l2;
        }
        if (null == l2) {
            return l1;
        }
        ListNode listNode = new ListNode(0);
        ListNode currNode = listNode;
        for (; null != l1 && null != l2; ) {
            // 都不为空时处理
            if (l1.val > l2.val) {
                currNode.next = l2;
                currNode = currNode.next;
                l2 = l2.next;
            } else {
                currNode.next = l1;
                currNode = currNode.next;
                l1 = l1.next;
            }
        }
        // l1为空时的l2处理
        if (null == l1) {
            currNode.next = l2;
        }
        // l2为空时的l1处理
        if (null == l2) {
            currNode.next = l1;
        }
        return listNode.next;
    }

}

//class ListNode {
//    int val;
//    ListNode next = null;
//    ListNode(int val) { this.val = val; }
//}
