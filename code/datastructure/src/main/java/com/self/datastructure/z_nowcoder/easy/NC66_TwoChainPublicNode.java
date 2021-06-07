package com.self.datastructure.z_nowcoder.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * NC66: 两个链表的第一个公共节点
 * 输入两个无环的单链表，找出它们的第一个公共结点。
 * （注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）
 * @author PJ_ZHANG
 * @create 2021-04-27 17:17
 **/
public class NC66_TwoChainPublicNode {

    public static void main(String[] args) {
        ListNode oneNode1 = new ListNode(1);
        ListNode oneNode2 = new ListNode(1);
        ListNode oneNode3 = new ListNode(1);
        ListNode twoNode1 = new ListNode(1);
        ListNode twoNode2 = new ListNode(1);
        ListNode publicNode = new ListNode(1);
        oneNode1.next = oneNode2;
        oneNode2.next = oneNode3;
        oneNode3.next = publicNode;
        twoNode1.next = twoNode2;
        System.out.println(FindFirstCommonNode_1(oneNode1, twoNode1).val);
    }

    /**
     * 方式2: 双指针
     * * 分别用两个指针指向两个链表的头节点, 依次向后遍历, 如果相等, 则为第一个公共节点
     * * 这里面存在一个问题
     * ** 如果在第一个公共节点前, 链表1 有3个元素, 链表2 有5个元素
     * ** 此时第一个公共节点肯定找不到
     * * 这种情况下可以下可以考虑将 链表1 拼在 链表2 后面, 链表2 拼在链表1 后面
     * * 这种情况下, 遍历完链表1 后继续遍历 链表2, 遍历完链表2 后继续遍历链表1
     * * 这种极端情况下, 遍历完两个链表的长度和即可全部遍历完成, 找到目标节点
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public static ListNode FindFirstCommonNode_1(ListNode pHead1, ListNode pHead2) {
        if (null == pHead1 || null == pHead2) return null;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        for (;p1 != p2;) {
            p1 = p1.next;
            p2 = p2.next;
            if (p1 != p2) {
                if (null == p1)
                    p1 = pHead2;
                if (null == p2)
                    p2 = pHead1;
            }
        }
        return p1;
    }

    /**
     * 方式1: 添加Map集合
     * * 首先遍历第一个链表, 并通过Hash进行存储
     * * 其次遍历第二个链表, 判断Hash中是否存在
     * * Hash中存在, 即为第一个节点
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public static ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        // 定义Hash
        Map<ListNode, Object> map = new HashMap<>();
        // 遍历第一个链表, 存储到 Hash 中
        for (;null != pHead1;) {
            map.put(pHead1, null);
            pHead1 = pHead1.next;
        }
        // 遍历第二个 链表, 判断Hash中是否存在
        for (;null != pHead2;) {
            if (map.containsKey(pHead2)) {
                return pHead2;
            }
            pHead2 = pHead2.next;
        }
        return null;
    }

}
