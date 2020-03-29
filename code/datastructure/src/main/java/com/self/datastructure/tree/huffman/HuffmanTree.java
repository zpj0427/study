package com.self.datastructure.tree.huffman;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树
 *
 * @author PJ_ZHANG
 * @create 2020-03-26 10:36
 **/
public class HuffmanTree {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        Node root = huffmanTree(array);
        preShowDetails(root);
    }

    /**
     * 生成赫夫曼树基本规则:
     * * 将数组的每个元素封装为Node, 并添加到集合中
     * * 对集合元素进行排序, 注意此处需要实现Comparable类, 并重写compareTo()
     * * 取前两个元素作为一个最简单的二叉树(不带子节点的)
     * * 以这两个元素作为一节点的左右两个子元素, 且该节点的权为这两个节点带权路径长度之和
     * * 将该父节点添加到集合中, 并对集合重新排序, 继续循环执行, 依次类推
     *
     * @param array
     */
    public static Node huffmanTree(int[] array) {
        // 转换为一个list
        List<Node> lstData = new ArrayList<>(10);
        for (int data : array) {
            lstData.add(new Node(data));
        }
        // 循环处理
        for (;lstData.size() > 1;) {
            // 对数组进行排序
            Collections.sort(lstData);
            // 获取前两个节点
            Node leftNode = lstData.get(0);
            Node rightNode = lstData.get(1);
            // 根据前两个节点带权路径构建第三个节点
            Node parentNode = new Node(leftNode.getData() + rightNode.getData());
            // 构建左右节点
            parentNode.setLeftNode(leftNode);
            parentNode.setRightNode(rightNode);
            // 移除前两个节点
            lstData.remove(leftNode);
            lstData.remove(rightNode);
            // 添加新构建的节点到集合中
            lstData.add(parentNode);
        }
        return lstData.get(0);
    }

    public static void preShowDetails(Node node) {
        if (null == node) {
            return;
        }
        System.out.print(node.getData() + "  ");
        if (null != node.getLeftNode()) {
            preShowDetails(node.getLeftNode());
        }
        if (null != node.getRightNode()) {
            preShowDetails(node.getRightNode());
        }
    }

    @Data
    static class Node implements Comparable<Node> {

        private int data;

        private Node leftNode;

        private Node rightNode;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node: [data = " + data;
        }

        /**
         * 进行数据比较, 满足数据升序排序
         * @param node
         * @return
         */
        @Override
        public int compareTo(Node node) {
            return this.data - node.getData();
        }
    }

}
