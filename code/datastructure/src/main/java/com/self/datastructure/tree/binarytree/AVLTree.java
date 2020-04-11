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
        // 增加节点
        // 删除节点
        // 遍历树
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
            // 删除根节点
            if (root.getValue() == value && null == root.getLeftNode() && null == root.getRightNode()) {
                root = null;
                return;
            }
            doDelNode(null, root, value);
        }

        private void doDelNode(Node parentNode, Node node, int value) {
            // 删除节点
            deleteNode(parentNode, node, value);
            // 节点删除完成后, 刷新AVL树
            // 删除不同于添加, 添加肯定添加打叶子节点, 所以可以直接进行树旋转处理
            // 删除可能在中间节点删除, 需要重新构造一次, 从根节点开始构造
            refreshAVLTree(root);
        }

        /**
         * 重构AVL树
         * @param node 当前递归到的节点
         */
        private void refreshAVLTree(Node node) {
            if (null == node) {
                return;
            }
            // 先处理左边
            refreshAVLTree(node.getLeftNode());
            // 再处理右边
            refreshAVLTree(node.getRightNode());
            // 进行旋转
            rotate(node);
        }

        /**
         * 删除节点
         *
         * @param parentNode 父节点
         * @param node 当前递归到的节点
         * @param value 要删除的值
         */
        private void deleteNode(Node parentNode, Node node, int value) {
            if (node.getValue() < value) {
                deleteNode(node, node.getRightNode(), value);
            } else if (node.getValue() > value) {
                deleteNode(node, node.getLeftNode(), value);
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
                    node = parentNode;
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
         * @param value 要添加的节点
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
            // 节点旋转, 构建平衡树
            rotate(parentNode);
        }

        /**
         * 进行左旋右旋处理,
         * 添加和删除节点都涉及该步
         * @param currNode
         */
        private void rotate(Node currNode) {
            // 如果左侧树比右侧树的高度差大于1, 则右旋
            if (getLeftHeight(currNode) - getRightHeight(currNode) > 1) {
                // 如果左侧树的右侧节点层数比左侧树的左侧节点层数高, 则先进行一次左旋
                if (null != currNode.getLeftNode() && getLeftHeight(currNode.getLeftNode()) < getRightHeight(currNode.getLeftNode())) {
                    leftRotate(currNode.getLeftNode());
                }
                rightRotate(currNode);
            }
            // 如果右侧树比左侧树的高度小于1, 则左旋
            else if (getRightHeight(currNode) - getLeftHeight(currNode) > 1) {
                // 如果右侧数的左侧子节点层数比右侧子节点层数大, 则先进行一次右旋
                if (null != currNode.getRightNode() && getRightHeight(currNode.getRightNode()) < getLeftHeight(currNode.getRightNode())) {
                    rightRotate(currNode.getLeftNode());
                }
                leftRotate(currNode);
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
