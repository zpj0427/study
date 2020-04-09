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
        binaryTree.addNode(5);
        binaryTree.addNode(2);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        binaryTree.addNode(6);
        binaryTree.addNode(9);
        binaryTree.addNode(10);
        binaryTree.middleShowDetails();
        System.out.println(binaryTree.delNode(1));;
        binaryTree.middleShowDetails();
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
            // 根节点为目标节点, 直接右旋处理
            if (targetData == node.getData()) {
                Node leftNode = node.getLeftNode();
                node = node.getRightNode();
                if (null == node) {
                    node = leftNode;
                    return true;
                }
                fillLeftNode(node, leftNode);
                return true;
            }
            return doDelNode(targetData, node);
        }

        public boolean doDelNode(Integer targetData, Node parentNode) {
            if (null == node) {
                return false;
            }
            if (targetData < parentNode.getData()) {
                Node leftNode = parentNode.getLeftNode();
                // 为空说明没有找到
                if (null == leftNode) {
                    return false;
                }
                // 匹配到, 则删除该节点, 同时旋转子节点
                if (targetData == leftNode.getData()) {
                    leftRevolve(parentNode, leftNode);
                    return true;
                } else {
                    return doDelNode(targetData, leftNode);
                }
            } else if (targetData > parentNode.getData()) {
                Node rightNode = parentNode.getRightNode();
                if (null == rightNode) {
                    return false;
                }
                if (targetData == rightNode.getData()) {
                    leftRevolve(parentNode, rightNode);
                    return true;
                } else {
                    return doDelNode(targetData, rightNode);
                }
            }
            return false;
        }

        /**
         * 左旋
         * 删除当前节点, 则把
         * @param parentNode 根节点表示根节点, 其他节点表示删除节点的父节点
         * @param delNode 要删除的节点
         */
        private void leftRevolve(Node parentNode, Node delNode) {
            if (delNode == parentNode.getLeftNode()) {
                // 删除节点的右节点为空, 直接用左节点代替原来位置
                if (null == delNode.getRightNode()) {
                    parentNode.setLeftNode(delNode.getLeftNode());
                    return;
                }
                parentNode.setLeftNode(delNode.getRightNode());
            } else if (delNode == parentNode.getRightNode()) {
                if (null == delNode.getRightNode()) {
                    parentNode.setRightNode(delNode.getLeftNode());
                    return;
                }
                parentNode.setRightNode(delNode.getRightNode());
            }
            // 重新放置删除节点的左侧节点, 到右侧节点的左侧
            // 如果右侧节点存在左侧节点, 则对右侧节点
            fillLeftNode(delNode.getRightNode(), delNode.getLeftNode());
        }

        /**
         * 填充左侧节点
         * @param node 右旋上来的节点
         * @param leftNode 左子节点
         */
         private void fillLeftNode(Node node, Node leftNode) {
            if (null == leftNode) {
                return;
            }
            // 删除节点右侧节点的左侧节点不为空, 则一直遍历到最后
            // 将删除节点的左侧节点挂到最后
            for (;null != node.getLeftNode();) {
                node = node.getLeftNode();
            }
            node.setLeftNode(leftNode);
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
