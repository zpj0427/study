package com.self.datastructure.z_nowcoder.easy;

/**
 * NC4: 判断链表中是否有环
 * 判断给定的链表中是否有环。如果有环则返回true，否则返回false。
 * 你能给出 O(1) 空间复杂度的解法么？
 *
 * @author PJ_ZHANG
 * @create 2021-04-27 16:30
 **/
public class NC4_CyclicChain {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node4;
        System.out.println(hasCycle_1(node1));
    }

    /**
     * 节点自删方式
     * * 链表成环, 说明链表为O型链表或者为6型链表, 肯定存在尾结点指向前置某一节点
     * * 从头节点开始遍历, 没遍历过一个节点, 将节点的next指向自身
     * * 在后续持续遍历中, 如果存在节点的next为自身, 说明存在环
     * * 如果遇到null, 则肯定不成环退出
     *
     * @param head
     * @return
     */
    public static boolean hasCycle_1(ListNode head) {
        for (;;) {
            if (null == head || null == head.next) {
                return false;
            }
            if (head == head.next) {
                return true;
            }
            // 取出下一个节点备用
            ListNode nextNode = head.next;
            // 将下一个节点指向指向自己, 说明已经处理过
            head.next = head;
            // 继续从下一个节点开始处理
            head = nextNode;
        }
    }

    /**
     * 通过快慢指针进行处理
     * * 定义两个指针, 一个指针一次跳一格, 一个指针一次跳两格
     * * 如果跳格为null, 说明不成环
     * * 如果两个指针相遇, 说明为环
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        if (null == head || null == head.next) {
            return false;
        }
        ListNode oneNode = head;
        ListNode twoNode = head.next.next;
        for (; ; ) {
            if (null == oneNode || null == twoNode || null == twoNode.next) {
                return false;
            }
            if (oneNode == twoNode) {
                return true;
            }
            // oneNode跳一格
            oneNode = oneNode.next;
            // twoNode跳两格
            twoNode = twoNode.next.next;
        }
    }

}
