package com.self.datastructure.tree.binarytree;

import lombok.Data;
import lombok.ToString;

/**
 * 线索化二叉树
 * * 线索化二叉树是在原二叉树的基础上, 将每一个节点抽象为存在上下指针的节点
 * * 在将二叉树转换为线索化二叉树时
 * * 如果节点的左右节点存在, 则该节点不变
 * * 如果节点的左侧节点为空, 则将左侧节点填充为上一个节点,
 *   上一个节点选择根据前序, 中序, 后序不同变化
 * * 如果节点的右侧节点为空, 则将右侧节点填充为下一个节点, 选择同上
 * * 另外, 在Node节点中除过leftNode, rightNode左右节点数据外,
 *   另外维护leftFlag, rightFlag两个标志位, 说明当前左/右侧数据指示的是树数据, 还是下一个节点数据
 * * 对于前序/中序/后序方式生成的线索化二叉树, 必须对应的使用前序/中序/后序方式进行遍历
 * * 遍历结果的第一个元素和最后一个元素, 分别没有前一个元素和后一个元素,
 *   所以在线索化二叉树中对应的指向Node为空, 但Flag指示为上/下一个节点
 *
 * @author pj_zhang
 * @create 2020-03-23 22:12
 **/
public class ClueBinaryTree {

    public static void main(String[] args) {
        MyBinaryTree binaryTree = new MyBinaryTree();
        binaryTree.addNode(5);
        binaryTree.addNode(2);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        binaryTree.addNode(6);
        binaryTree.addNode(9);
        binaryTree.addNode(7);
        // 中序生成线索化二叉树
        System.out.println("中序生成线索化二叉树...");
        binaryTree.middleClueBinaryTree();
        System.out.println("\r\n中序遍历线索化二叉树...");
        binaryTree.middleShowDetails();
    }

    static class MyBinaryTree {

        private Node node;

        /**
         * 指向前一个节点, 用于下一个节点时的上一个节点操作
         * 上一个节点的右节点为空时, 需要指向下一个节点,
         * 此时设置该节点的右节点信息, 需要等操作到下一个节点, 用preNode节点作为该节点设置
         */
        private Node preNode;

        // 中序生成线索二叉树
        // 中序输出规则为先输出左侧节点, 再输出当前节点, 最后输出右侧节点
        // 如果左侧节点为空, 递归左侧进行处理
        // 处理当前节点
        // * 如果左侧节点为空, 填充为上一个节点, 即 preNode
        // * 如果右侧节点为空, 填充为下一个节点
        // * 因为当前节点拿不到下一个节点, 需要遍历到下一个节点后进行处理
        //   等到遍历到下一个节点时, 此时当前节点为 preNode,
        // * 当前节点(preNode)的右侧节点即下一个节点, 也就是当前遍历到的节点
        //   此时设置右侧节点, 即把当前遍历到的节点设置为preNode的右侧节点
        // 如果右侧节点不为空, 递归右侧进行处理
        public void middleClueBinaryTree() {
            doMiddleClueBinaryTree(node);
        }

        public void doMiddleClueBinaryTree(Node node) {
            if (null == node) {
                return;
            }
            // 左侧递归处理
            doMiddleClueBinaryTree(node.getLeftNode());

            // 直接输出当前节点
            System.out.print(node.getData() + "  ");
            // 填充左侧节点
            // 左侧节点为空, 填充为前驱节点
            if (null == node.getLeftNode()) {
                // 中序: 第一个输出的节点的左侧节点必定为空
                node.setLeftNode(preNode);
                node.setLeftFlag(1);
            }
            // 填充右侧节点
            // 右侧节点为空, 填充为后继节点
            // 填充下一个节点是, 需要遍历到下一个节点进行填充
            // 则此时当前节点表示为上一个节点, 即preNode
            if (null != preNode && null == preNode.getRightNode()) {
                preNode.setRightNode(node);
                preNode.setRightFlag(1);
            }
            // 将当前节点设置为上一个节点
            preNode = node;

            // 右侧递归处理
            doMiddleClueBinaryTree(node.getRightNode());
        }

        // 中序遍历线索二叉树
        public void middleShowDetails() {
            doMiddleShowDetails(node);
        }

        public void doMiddleShowDetails(Node node) {
            for (;null != node;) {
                // 首先循环找到leftFlag为1的节点
                // 表示左侧的叶子节点
                for (;node.getLeftFlag() == 0;) {
                    node = node.getLeftNode();
                }
                // 先打印该节点
                System.out.print(node.getData() + "  ");
                // 右侧节点状态为1, 说明表示下一个节点, 直接打印
                for (;node.getRightFlag() == 1;) {
                    node = node.getRightNode();
                    System.out.print(node.getData() + "  ");
                }
                // 走到此处说明找到有效的右侧节点, 替换掉该节点
                node = node.getRightNode();
            }
        }

        // 添加二叉树节点
        public void addNode(Integer data) {
            if (null == node) {
                node = new Node(data);
            } else {
                addNode(data, node);
            }
        }

        private void addNode(Integer data, Node node) {
            if (null == node) {
                throw new RuntimeException("Node 节点为空");
            }
            if (data > node.getData()) {
                Node rightNode = node.getRightNode();
                if (null == rightNode) {
                    node.setRightNode(new Node(data));
                } else {
                    addNode(data, node.getRightNode());
                }
            } else if (data < node.getData()) {
                Node leftNode = node.getLeftNode();
                if (null == leftNode) {
                    node.setLeftNode(new Node(data));
                } else {
                    addNode(data, node.getLeftNode());
                }
            } else {
                System.out.println("数据节点已经存在");
            }
        }

    }

    @Data
    @ToString
    static class Node {

        private Integer data;

        private Node leftNode;

        private Node rightNode;

        /**
         * 左侧节点标志位,
         * 0表示存在左侧节点, 1表示左侧节点为前继节点
         */
        private int leftFlag;

        /**
         * 右侧节点标志位
         * 0表示存在右侧节点, 1表示右侧节点为后续节点
         */
        private int rightFlag;

        public Node() {}

        public Node(Integer data) {
            this(data, null, null);
        }

        public Node(Integer data, Node leftNode, Node rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

    }

}
