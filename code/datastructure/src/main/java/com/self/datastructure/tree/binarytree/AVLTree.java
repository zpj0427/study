package com.self.datastructure.tree.binarytree;

import lombok.Data;

/**
 * 平衡二叉树
 *
 * @author PJ_ZHANG
 * @create 2020-04-09 9:11
 **/
public class AVLTree {

    public static void main(String[] args) {
        MyAVLTree myAVLTree = new MyAVLTree();
        myAVLTree.addNode(100);
        myAVLTree.addNode(110);
        myAVLTree.addNode(70);
        myAVLTree.addNode(60);
        myAVLTree.addNode(80);
        myAVLTree.addNode(90);
        myAVLTree.addNode(50);
        myAVLTree.addNode(65);
        myAVLTree.addNode(45);
        myAVLTree.addNode(55);
        myAVLTree.delNode(60);
    }

    static class MyAVLTree {
        // 根节点
        private Node root;

        /**
         * 获取根节点
         *
         * @return 根节点
         */
        public Node getRoot() {
            return root;
        }

        /**
         * 删除节点
         * 删除节点是在
         *
         * @param value
         */
        public void delNode(int value) {
            if (null == root) {
                return;
            }
            doDelNode(null, root, value);
        }

        private void doDelNode(Node parentNode, Node node, int value) {
            // 删除节点
            if (root == node && node.getValue() == value && null == node.getLeftNode() && null == node.getRightNode()) {
                root = null;
                return;
            }
            delelteNode(parentNode, node, value);
            // 重整AVL树
        }

        private void delelteNode(Node parentNode, Node node, int value) {
            if (node.getValue() < value) {
                delelteNode(node, node.getRightNode(), value);
            } else if (node.getValue() > value) {
                delelteNode(node, node.getLeftNode(), value);
            } else {
                // 找到节点, 进行节点删除
                // 对当前节点的权值进行替换, 用左侧节点的最右侧节点进行替换
                if (null == node.getLeftNode() && null == node.getRightNode()) {
                    // 当前节点为叶子节点
                    if (parentNode.getRightNode() == node) {
                        parentNode.setRightNode(null);
                    } else if (parentNode.getLeftNode() == node) {
                        parentNode.setLeftNode(null);
                    }
                } else if (null == node.getLeftNode() || null == node.getRightNode()) {
                    // 当前节点为父节点, 单只有单子节点
                    // 因为AVL树的平衡属性, 节点如果只有单子节点, 则该子节点下不可能再有子节点, 如果往这部分加节点, 则会触发旋转
                    // 如果左侧节点为空, 则将右侧节点的权值赋给该节点, 并将该节点的右侧节点断开
                    // 如果右侧节点为空, 则将左侧节点的权值赋给该节点, 并将该节点的左侧节点断开
                    if (null == node.getLeftNode()) {
                        node.setValue(node.getRightNode().getValue());
                        node.setRightNode(null);
                    } else if (null == node.getRightNode()) {
                        node.setValue(node.getLeftNode().getValue());
                        node.setLeftNode(null);
                    }
                } else {
                    // 当前节点为父节点, 并且直接子节点完整
                    // 取左子节点的最右侧节点替换该节点
                    // 取左侧节点为临时节点
                    // 取当前节点为父节点
                    Node tmpNode = node.getLeftNode();
                    parentNode = node;
                    // 一直取左侧节点的右侧节点, 知道右侧节点为空, 说明已经获取到左树的最大值
                    for (;null != tmpNode.getRightNode();) {
                        parentNode = tmpNode;
                        tmpNode = tmpNode.getRightNode();
                    }
                    // 将Node的值设置为左树最大节点值
                    node.setValue(tmpNode.getValue());
                    // 接着需要将该节点断开
                    // 该节点可能是父节点的左侧节点, 也可能是右侧节点, 需要分支处理
                    // 同样, 该节点可能存在左侧节点, 需要重新连接
                    // 如果节点是左侧节点, 并且存在左侧节点, 则直接将该节点的左侧节点设置为父节点的左侧节点
                    // 如果节点是右侧节点, 并且存在左侧节点, 则直接将该节点的左侧节点设置为父节点的右侧节点
                    if (tmpNode == parentNode.getLeftNode()) {
                        parentNode.setLeftNode(tmpNode.getLeftNode());
                    } else if (tmpNode == parentNode.getRightNode()) {
                        parentNode.setRightNode(tmpNode.getLeftNode());
                    }
                }
            }
        }

        /**
         * 添加节点
         *
         * @param addNode 要添加的节点
         */
        public void addNode(int value) {
            if (null == root) {
                root = new Node(value);
                return;
            }
            doAddNode(root, value);
        }

        private void doAddNode(Node parentNode, int value) {
            if (null == parentNode) {
                return;
            }
            // 添加节点
            if (parentNode.getValue() < value) {
                if (null == parentNode.getRightNode()) {
                    parentNode.setRightNode(new Node(value));
                } else {
                    doAddNode(parentNode.getRightNode(), value);
                }
            } else if (parentNode.getValue() > value) {
                if (null == parentNode.getLeftNode()) {
                    parentNode.setLeftNode(new Node(value));
                } else {
                    doAddNode(parentNode.getLeftNode(), value);
                }
            } // 等于不添加

            // 节点添加完成后, 进行左旋右旋处理
            // 因为添加节点是递归加的, 所以对于添加节点路径上的每一个节点都会进行该步操作
            // 如果左侧树比右侧树的高度差大于1, 则右旋
            if (getLeftHeight(parentNode) - getRightHeight(parentNode) > 1) {
                // 如果左侧树的右侧节点层数比左侧树的左侧节点层数高, 则先进行一次左旋
                if (null != parentNode.getLeftNode() && getLeftHeight(parentNode.getLeftNode()) < getRightHeight(parentNode.getLeftNode())) {
                    leftRotate(parentNode.getLeftNode());
                }
                rightRotate(parentNode);
            }
            // 如果右侧树比左侧树的高度小于1, 则左旋
            else if (getRightHeight(parentNode) - getLeftHeight(parentNode) > 1) {
                // 如果右侧数的左侧子节点层数比右侧子节点层数大, 则先进行一次右旋
                if (null != parentNode.getRightNode() && getRightHeight(parentNode.getRightNode()) < getLeftHeight(parentNode.getRightNode())) {
                    rightRotate(parentNode.getLeftNode());
                }
                leftRotate(parentNode);
            }
        }

        /**
         * 右旋
         * 重新构造当前节点为新节点
         * 将新节点的右侧节点设置为当前节点的右侧节点
         * 将新节点的左侧节点设置为当前节点的左侧节点的右侧节点
         * 将当前节点的权值设置为左侧节点的权值
         * 将当前节点的左侧节点设置为左侧节点的左侧节点
         * 将当前节点的右侧节点设置为新节点
         *
         * @param node
         */
        public void rightRotate(Node node) {
            Node newNode = new Node(node.getValue());
            newNode.setRightNode(node.getRightNode());
            newNode.setLeftNode(node.getLeftNode().getRightNode());
            node.setValue(node.getLeftNode().getValue());
            node.setLeftNode(node.getLeftNode().getLeftNode());
            node.setRightNode(newNode);
        }

        /**
         * 左旋
         * 重新构造当前节点为新节点
         * 将新节点的左侧节点设置为当前节点的左侧节点
         * 将新节点的右侧节点设置为当前节点的右侧节点的左侧节点
         * 将当前节点的权值设置为右侧节点的权值
         * 将当前节点的右侧节点设置为右侧节点的右侧节点
         * 将当前节点的左侧节点设置为新节点
         * @param node 需要处理的子树根节点
         */
        public void leftRotate(Node node) {
            Node newNode = new Node(node.getValue());
            newNode.setLeftNode(node.getLeftNode());
            newNode.setRightNode(node.getRightNode().getLeftNode());
            node.setValue(node.getRightNode().getValue());
            node.setRightNode(node.getRightNode().getRightNode());
            node.setLeftNode(newNode);
        }

        /**
         * 获取左侧树高度
         * @param node
         * @return
         */
        public int getRightHeight(Node node) {
            if (null == node.getRightNode()) {
                return 0;
            }
            return getHeight(node.getRightNode());
        }

        /**
         * 获取右侧树高度
         * @param node
         * @return
         */
        private int getLeftHeight(Node node) {
            if (null == node.getLeftNode()) {
                return 0;
            }
            return getHeight(node.getLeftNode());
        }

        /**
         * 获取树高度
         * @param node
         * @return
         */
        public int getHeight(Node node) {
            int height = 0;
            int leftHeight = 0;
            int rightHeight = 0;
            if (null != node.getLeftNode()) {
                leftHeight += getHeight(node.getLeftNode());
            }
            if (null != node.getRightNode()) {
                rightHeight = getHeight(node.getRightNode());
            }
            height = Math.max(leftHeight, rightHeight);
            return height + 1;
        }

        /**
         * 中序遍历
         */
        public void middleShowDetails() {
            doMiddleShowDetails(root);
        }

        private void doMiddleShowDetails(Node node) {
            if (null == node) {
                return;
            }
            doMiddleShowDetails(node.getLeftNode());
            System.out.println(node);
            doMiddleShowDetails(node.getRightNode());
        }

    }

    @Data
    static class Node {

        // 节点权值
        private int value;

        // 左节点
        private Node leftNode;

        // 右节点
        private Node rightNode;

        public Node() {}

        public Node(int value) {
            this.value = value;
        }

        public String toString() {
            return "Node: [value = " + value + "]";
        }

    }

}
