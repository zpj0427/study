package com.self.datastructure.tree;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树
 *
 * @author pj_zhang
 * @create 2020-03-21 22:02
 **/
public class BinaryTree {

    public static void main(String[] args) {
        MyBinaryTree binaryTree = new MyBinaryTree();
        binaryTree.addNode(5);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(6);
        binaryTree.addNode(3);
        binaryTree.addNode(2);
        binaryTree.addNode(7);
        binaryTree.addNode(8);
        System.out.println(binaryTree.preFindNode(50));
    }

    static class MyBinaryTree {

        private Node node;

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

        // 前序查找
        public Integer preFindNode(Integer targetData) {
            return doPreFindNode(targetData, node);
        }

        public Integer doPreFindNode(Integer targetData, Node node) {
            if (null == node) {
                return null;
            }
            if (targetData == node.getData()) {
                return node.getData();
            } else if (targetData < node.getData()) {
                return doPreFindNode(targetData, node.getLeftNode());
            } else if (targetData > node.getData()) {
                return doPreFindNode(targetData, node.getRightNode());
            }
            return null;
        }

        // 中序查找
        public Integer middleFindNode(Integer targetData) {
            return doMiddleFindNode(targetData, node);
        }

        public Integer doMiddleFindNode(Integer targetData, Node node) {
            if (null == node) {
                return null;
            }
            if (targetData < node.getData()) {
                return doMiddleFindNode(targetData, node.getLeftNode());
            } else if (targetData == node.getData()) {
                return node.getData();
            } else if (targetData > node.getData()) {
                return doMiddleFindNode(targetData, node.getRightNode());
            }
            return null;
        }

        // 后序查找
        public Integer postFindNode(Integer targetData) {
            return doPostFindNode(targetData, node);
        }

        public Integer doPostFindNode(Integer targetData, Node node) {
            if (null == node) {
                return null;
            }
            if (targetData < node.getData()) {
                return doPostFindNode(targetData, node.getLeftNode());
            } else if (targetData > node.getData()) {
                return doPostFindNode(targetData, node.getRightNode());
            } else if (targetData == node.getData()) {
                return node.getData();
            }
            return null;
        }

        // 前序遍历,
        // 先输出当前节点值
        // 再输出左侧节点值
        // 最后输出右侧节点值
        public void preShowDetails() {
            doPreShowDetails(node);
        }

        public void doPreShowDetails(Node node) {
            if (null == node) {
                return;
            }
            System.out.println("Node: " + node.getData());
            if (null != node.getLeftNode()) {
                doPreShowDetails(node.getLeftNode());
            }
            if (null != node.getRightNode()) {
                doPreShowDetails(node.getRightNode());
            }
        }

        // 中序输入
        // 先输出左侧节点值
        // 再输出当前节点值
        // 最后输出中间节点值
        // 中序输出结果为有序数组
        public void middleShowDetails() {
            doMiddleShowDetails(node);
        }

        public void doMiddleShowDetails(Node node) {
            if (null == node) {
                return;
            }
            if (null != node.getLeftNode()) {
                doMiddleShowDetails(node.getLeftNode());
            }
            System.out.println("Node: " + node.getData());
            if (null != node.getRightNode()) {
                doMiddleShowDetails(node.getRightNode());
            }
        }

        // 后续输出
        // 先输出左侧数据
        // 再输出右侧数据
        // 最后输出当前数据
        public void postShowDetails() {
            doPostShowDetails(node);
        }

        public void doPostShowDetails(Node node) {
            if (null == node) {
                return;
            }
            if (null != node.getLeftNode()) {
                doPostShowDetails(node.getLeftNode());
            }
            if (null != node.getRightNode()) {
                doPostShowDetails(node.getRightNode());
            }
            System.out.println("Node: " + node.getData());
        }

    }

    @Data
    @ToString
    static class Node {

        private Integer data;

        private Node leftNode;

        private Node rightNode;

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
