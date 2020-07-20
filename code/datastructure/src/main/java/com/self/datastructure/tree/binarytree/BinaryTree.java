package com.self.datastructure.tree.binarytree;

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
        binaryTree.addNode(50);
        binaryTree.addNode(20);
        binaryTree.addNode(10);
        System.out.println(binaryTree.delNode(10));;
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
                    Node newNode = new Node(data);
                    node.setRightNode(newNode);
                    newNode.setParentNode(node);
                } else {
                    addNode(data, node.getRightNode());
                }
            } else if (data < node.getData()) {
                Node leftNode = node.getLeftNode();
                if (null == leftNode) {
                    Node newNode = new Node(data);
                    node.setLeftNode(newNode);
                    newNode.setParentNode(node);
                } else {
                    addNode(data, node.getLeftNode());
                }
            } else {
                System.out.println("数据节点已经存在");
            }
        }

        /**
         * 二叉树节点删除
         * * 如果删除节点为叶子节点, 则直接删除
         * * 如果删除节点为非叶子节点, 且只有左节点或者有节点其中一个节点, 将子节点设置为该节点
         * * 如果删除节点为非叶子节点, 则子节点完整, 则让右子节点代替该节点, 左子节点按顺序挂在右子节点的左侧位置
         *
         * @param targetData
         * @return
         */
        public boolean delNode(Integer targetData) {
            if (null == node) {
                return false;
            }
            return doDelNode(targetData, node);
        }

        private boolean doDelNode(Integer targetData, Node node) {
            if (null == node) {
                return false;
            }
            if (targetData > node.getData()) {
                // 删除值大于当前节点值, 向右查找
                return doDelNode(targetData, node.getRightNode());
            } else if (targetData < node.getData()) {
                // 删除值小于当前节点值, 向左查找
                return doDelNode(targetData, node.getLeftNode());
            } else {
                // 找到后, 进行处理
                // 获取右侧节点的最左节点
                Node nextNode = findNextNode(node);
                // 说明存在右侧节点
                if (null == nextNode) {
                    // 直接用左侧节点替换该节点
                    // 如果当前节点是根节点, 直接将根节点替换为其左侧节点
                    if (this.node == node) {
                        this.node = node.getLeftNode();
                        this.node.setParentNode(null);
                    } else {
                        // 删除节点是中间节点, 如果是父节点的左侧节点, 则将父节点的左侧节点替换为其左侧节点
                        if (node == node.getParentNode().getLeftNode()) {
                            node.getParentNode().setLeftNode(node.getLeftNode());
                        } else if (node == node.getParentNode().getRightNode()) {
                            // 删除节点是中间节点, 如果是父节点的右侧节点, 则将父节点的右侧节点替换为其左侧节点
                            node.getParentNode().setRightNode(node.getLeftNode());
                        }
                        if (null != node.getLeftNode()) {
                            node.getLeftNode().setParentNode(node.getParentNode());
                        }
                    }
                } else {
                    // 存在后继节点
                    // 将当前值替换为后继节点的值
                    node.setData(nextNode.getData());
                    // 将后继节点挂空, 后继节点可能存在右侧节点, 用右侧节点进行替换
                    if (nextNode == nextNode.getParentNode().getLeftNode()) {
                        nextNode.getParentNode().setLeftNode(nextNode.getRightNode());
                    } else if (nextNode == nextNode.getParentNode().getRightNode()) {
                        // 删除节点是中间节点, 如果是父节点的右侧节点, 则将父节点的右侧节点替换为其左侧节点
                        nextNode.getParentNode().setRightNode(nextNode.getRightNode());
                    }
                    if (null != nextNode.getRightNode()) {
                        nextNode.getRightNode().setParentNode(nextNode.getParentNode());
                    }
                }
                return true;
            }
        }

        /**
         * 获取右侧节点的最左节点
         * @param node
         * @return
         */
        private Node findNextNode(Node node) {
            Node nextNode = node.getRightNode();
            if (null == nextNode) {
                return nextNode;
            }
            while (null != nextNode.getLeftNode()) {
                nextNode = nextNode.getLeftNode();
            }
            return nextNode;
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

        // 中序输出
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

        private Node parentNode;

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
