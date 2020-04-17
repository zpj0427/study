package com.self.datastructure.tree.redblacktree;

import lombok.Data;

/**
 * 红黑树
 *
 * @author PJ_ZHANG
 * @create 2020-04-17 9:15
 **/
public class RedBlackTree {

    public static void main(String[] args) {
        SelfRedBlackTree selfRedBlackTree = new SelfRedBlackTree();
        // 添加数据
    }

    static class SelfRedBlackTree {
        private Node root = null;

        /**
         * 添加数据
         * @param value 数据值
         */
        public void add(Integer value) {
            // 根节点为空, 初始化根节点, 并设置颜色为黑色
            if (null == root) {
                root = new Node(value);
                root.setRed(false);
                return;
            }
            // 根节点不为空, 添加节点
            doAdd(root, value);
        }

        /**
         * 添加红黑树节点数据
         * @param parentNode 父节点
         * @param value 插入数据
         */
        private void doAdd(Node parentNode, Integer value) {
            if (null == parentNode) {
                return;
            }
            // 先添加节点
            if (parentNode.getValue() > value) {
                if (null != parentNode.getLeftNode()) {
                    doAdd(parentNode.getLeftNode(), value);
                } else {
                    Node newNode = new Node(value);
                    newNode.setParentNode(parentNode);
                    parentNode.setLeftNode(newNode);
                    balanceTree(newNode, parentNode);
                }
            } else if (parentNode.getValue() < value) {
                if (null != parentNode.getRightNode()) {
                    doAdd(parentNode.getRightNode(), value);
                } else {
                    Node newNode = new Node(value);
                    newNode.setParentNode(parentNode);
                    parentNode.setRightNode(newNode);
                    balanceTree(newNode, parentNode);
                }
            }
        }

        /**
         * 平衡红黑树
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         */
        private void balanceTree(Node currNode, Node parentNode) {
            // 当前节点是红节点, 父节点是黑节点
            // 直接插入, 不需要变色和旋转
            if (currNode.isRed() && !parentNode.isRed()) {
                return;
            }
            // 当前节点是红节点, 父节点是红节点
            // 此时一定存在祖父节点是黑节点
            // 需要分情况进行处理
            if (currNode.isRed() && parentNode.isRed()) {
                // 如果存在叔叔节点并且叔叔节点为红色
                // 将祖父节点变红, 父节点和叔叔节点变黑
                Node uncleNode = parentNode == parentNode.getParentNode().getLeftNode()
                        ? parentNode.getParentNode().getRightNode() : parentNode.getParentNode().getLeftNode();
                if (null != uncleNode && uncleNode.isRed()) {
                    parentNode.getParentNode().setRed(true);
                    parentNode.setRed(false);
                    uncleNode.setRed(false);
                    // 如果祖父节点是根节点, 则直接染黑
                    if (root == parentNode.getParentNode()) {
                        parentNode.getParentNode().setRed(false);
                    } else { // 祖父节点不是根节点, 以祖父节点作为当前节点继续往上处理
                        balanceTree(parentNode.getParentNode(), parentNode.getParentNode().getParentNode());
                    }
                } else { // 表示叔叔节点不存在, 或者叔叔节点为黑
                    // 如果插入节点的父节点是祖父节点的左子节点
                    if (parentNode == parentNode.getParentNode().getLeftNode()) {
                        // 如果当前节点是父节点左子节点, 则构成LL双红
                        // LL双红, 直接右旋处理
                        if (currNode == parentNode.getLeftNode()) {
                            rightRotate(parentNode, parentNode.getParentNode());
                        }
                        // 如果当前节点是父节点的右子节点, 则构成LR双红
                        // LR双红, 先左旋, 再右旋
                        else if (currNode == parentNode.getRightNode()) {
                            leftRotateWithoutChange(currNode, parentNode);
                            // 左旋后, 当前节点已经变为父节点, 父节点为当前节点的左子节点
                            rightRotate(currNode, currNode.getParentNode());
                        }
                    }
                    // 如果插入节点的父节点是祖父节点的右子节点
                    else if (parentNode == parentNode.getParentNode().getRightNode()) {
                        // 如果当前节点是父节点的右子节点, 则构成RR双红
                        // RR双红, 直接左旋处理
                        if (currNode == parentNode.getRightNode()) {
                            leftRotate(parentNode, parentNode.getParentNode());
                        }
                        // 如果当前节点是父节点的左子节点, 则构成RL双红
                        // RL双红, 先左旋, 再右旋
                        else if (currNode == parentNode.getLeftNode()) {
                            rightRotateWithoutChange(currNode, parentNode);
                            // 右旋后, 当前节点表示父节点, 父节点为当前节点右子节点
                            leftRotate(currNode, currNode.getParentNode());
                        }
                    }
                }

            }
        }

        /**
         * 变色左旋
         * 对于RR双红结构, 需要先变色再左旋, 保证树的完美黑平衡
         * 变色: 将父节点变为黑色, 祖父节点变为红色(祖父节点必定为黑色)
         * 左旋: 将父节点上浮, 祖父节点下沉
         *
         * @param parentNode 父节点
         * @param grandpaNode 祖父节点
         */
        private void leftRotate(Node parentNode, Node grandpaNode) {
            // 变色, 父节点变为黑色, 祖父节点变为红色
            parentNode.setRed(false);
            grandpaNode.setRed(true);
            // 左旋
            // 构造祖父节点为新节点
            Node newNode = new Node(grandpaNode.getValue());
            // 祖父节点左侧节点不变
            newNode.setLeftNode(grandpaNode.getLeftNode());
            // 祖父节点右侧节点为父节点的左侧节点
            newNode.setRightNode(parentNode.getLeftNode());
            // 父节点的左侧节点为新节点
            parentNode.setLeftNode(newNode);
            // 祖父节点的父节点, 更新为父节点
            newNode.setParentNode(parentNode);
            // 父节点的父节点, 为祖父节点的父节点
            parentNode.setParentNode(grandpaNode.getParentNode());
            // 如果祖父节点为根节点, 则替换根节点为父节点
            if (root == grandpaNode) {
                root = parentNode;
            }
            // 如果祖父节点不为根节点, 则替换祖父父节点的右子节点为父节点
            else {
                grandpaNode.getParentNode().setRightNode(parentNode);
            }
        }

        /**
         * 变色右旋
         * 对于LL双红结构, 需要先变色再右旋, 保证树的完美黑平衡
         * 变色: 将父节点变为黑色, 祖父节点变为红色(此时祖父节点必定为黑色)
         * 右旋: 将父节点上浮, 祖父节点下沉,
         *
         * @param parentNode 父节点
         * @param grandpaNode 祖父节点
         */
        private void rightRotate(Node parentNode, Node grandpaNode) {
            // 变色, 父节点变黑, 祖父节点变红
            parentNode.setRed(false);
            grandpaNode.setRed(true);
            // 右旋
            // 构造祖父为新节点
            Node newNode = new Node(grandpaNode.getValue());
            // 祖父节点右侧节点不变
            newNode.setRightNode(grandpaNode.getRightNode());
            // 祖父节点左侧节点为父节点的右侧节点
            newNode.setLeftNode(parentNode.getRightNode());
            // 父节点的右侧节点为新节点
            parentNode.setRightNode(newNode);
            newNode.setParentNode(parentNode);
            // 父节点的父节点, 为祖父节点的父节点
            parentNode.setParentNode(grandpaNode.getParentNode());
            // 如果祖父节点为根节点, 则替换根节点为父节点
            if (root == grandpaNode) {
                root = parentNode;
            }
            // 如果祖父节点不为根节点, 则替换祖父父节点的左子节点为父节点
            else {
                grandpaNode.getParentNode().setLeftNode(parentNode);
            }
        }

        /**
         * 不变色右旋
         * 对于RL双红, 需要先将树结构转换为RR双红
         * 该部分转换只旋转不变色
         * 将父节点下沉, 变为当前节点的右子节点
         * 将当前节点上浮, 变为祖父节点的右子节点
         * 将当前节点的右子节点变为父节点的左子节点
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         */
        private void rightRotateWithoutChange(Node currNode, Node parentNode) {
            // 获取祖父节点
            Node grandpaNode = parentNode.getParentNode();
            // 构造父节点为节点
            Node newNode = new Node(parentNode.getValue());
            // 父节点的右子节点不变
            newNode.setRightNode(parentNode.getRightNode());
            // 父节点的左子节点为当前节点的右子节点
            newNode.setLeftNode(currNode.getRightNode());
            // 当前节点的右子节点为新节点, 当前节点的左子节点不变
            currNode.setRightNode(newNode);
            newNode.setParentNode(currNode);
            // 当前节点的父节点, 为父节点的父节点
            currNode.setParentNode(parentNode.getParentNode());
            // 祖父节点的右子节点为当前节点
            grandpaNode.setRightNode(currNode);
            // 这样会直接将原来的parentNode挂空, 等待GC回收
        }

        /**
         * 不变色左旋
         * 对于LR双红, 需要先将树结构转换为LL双红
         * 该部分转换只旋转不变色
         * 将父节点下沉, 变为当前节点的左子节点
         * 将当前节点上浮, 变为祖父节点的左子节点
         * 将当前节点的左子节点变为父节点的右子节点
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         */
        private void leftRotateWithoutChange(Node currNode, Node parentNode) {
            // 获取祖父节点
            Node grandpaNode = parentNode.getParentNode();
            // 构造父节点为节点
            Node newNode = new Node(parentNode.getValue());
            // 父节点的左子节点不变
            newNode.setLeftNode(parentNode.getLeftNode());
            // 父节点的右子节点为当前节点的左子节点
            newNode.setRightNode(currNode.getLeftNode());
            // 当前节点的左子节点为新节点, 当前节点的右子节点不变
            currNode.setLeftNode(newNode);
            newNode.setParentNode(currNode);
            // 当前节点的父节点, 为父节点的父节点
            currNode.setParentNode(parentNode.getParentNode());
            // 祖父节点的左子节点为当前节点
            grandpaNode.setLeftNode(currNode);
            // 这样会直接将原来的parentNode挂空, 等待GC回收
        }
    }

    @Data
    static class Node {

        // 数据
        private Integer value;

        // 左子节点
        private Node leftNode;

        // 右子节点
        private Node rightNode;

        // 父节点
        private Node parentNode;

        // 是否红色节点
        private boolean isRed = true;

        Node(Integer value) {
            this.value = value;
        }

    }
}
