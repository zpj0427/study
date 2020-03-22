package com.self.datastructure.tree;

/**
 * 顺序存储二叉树
 * 只对完全二叉树有效
 *
 * @author pj_zhang
 * @create 2020-03-22 18:40
 **/
public class ArrayBinaryTree {

    private static int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * 讲一个二叉树的节点, 以数组的顺序按层依次存放, 则该二叉树肯定是一个完全二叉树
     * 在生成的完全二叉树中:
     * 第n个元素的左子节点 index = 2 * n + 1
     * 第n个元素的右子节点 index = 2 * n + 2
     * 第n个元素的父节点为 index = (n - 1) / 2
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("前序输出");
        preShowDetails(0);
        System.out.println("中序输出");
        middleShowDetails(0);
        System.out.println("后序输出");
        postShowDetails(0);
    }

    /**
     * 前序遍历
     * 输出结果: 0, 1, 3, 7, 8, 5, 9, 2, 5, 6
     *
     * @param index 开始索引
     */
    public static void preShowDetails(int index) {
        // 先输出当前节点
        System.out.println("前序输出: " + array[index]);
        // 再输出左侧节点
        if ((index * 2 + 1) < array.length) {
            preShowDetails(index * 2 + 1);
        }
        // 再输出右侧节点
        if ((index * 2 + 2) < array.length) {
            preShowDetails(index * 2 + 2);
        }
    }

    /**
     * 中序遍历
     * 输出结果: 7, 3, 8, 1, 9, 4, 0, 5, 2, 6
     *
     * @param index 开始索引
     */
    public static void middleShowDetails(int index) {
        // 先输出左侧节点
        if ((index * 2 + 1) < array.length) {
            middleShowDetails(index * 2 + 1);
        }
        // 再输出当前节点
        System.out.println("中序输出: " + array[index]);
        // 最后输出右侧节点
        if ((index * 2 + 2) < array.length) {
            middleShowDetails(index * 2 + 2);
        }
    }

    /**
     * 后序遍历
     * 输出结构: 7, 8, 3, 9, 4, 1, 5, 6, 2, 0
     *
     * @param index 开始索引
     */
    public static void postShowDetails(int index) {
        // 先输出左侧节点
        if ((index * 2 + 1) < array.length) {
            postShowDetails(index * 2 + 1);
        }
        // 再输出右侧节点
        if ((index * 2 + 2) < array.length) {
            postShowDetails(index * 2 + 2);
        }
        // 最后输出当前节点
        System.out.println("后序输出: " + array[index]);
    }

}
