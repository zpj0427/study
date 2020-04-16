package com.self.datastructure.tree.multiwaytree;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * B树
 *
 * @author PJ_ZHANG
 * @create 2020-04-16 9:57
 **/
public class BTree {

    public static void main(String[] args) {
        SelfBTree selfBTree = new SelfBTree(2);
        // 插入数据
    }


    static class SelfBTree {

        // 定义根节点
        private Node root = null;

        // 定义树类型, 默认用2-3树
        private int maxElementCount = 2;

        SelfBTree(int maxElementCount) {
            this.maxElementCount = maxElementCount;
        }

        // 添加节点
        void add(int value) {
            // 树为空, 直接构造树
            if (null == root) {
                root = new Node(maxElementCount);
                root.add(value);
                return;
            }
            // 开始寻找有效的节点添加该数据
            add(root, null, value);
        }

        /**
         * 添加数据
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         * @param value 要添加的数据
         */
        private void add(Node currNode, Node parentNode, int value) {
            if (null == currNode) {
                return;
            }
            // 先添加节点
            // 如果当前节点不存在子节点, 说明已经到了叶子节点层, 直接添加
            if (CollectionUtils.isEmpty(currNode.getLstChildrenNode())) {
                currNode.add(value);
            } else { // 在中间节点层时, 比较大小, 挑选合适的路径进行添加
                List<Integer> lstNodeData = currNode.getLstNodeData();
                Node childNode = null;
                for (int i = 0; i < lstNodeData.size(); i++) {
                    // 根据值比较, 获取到对应的子节点列表索引
                    if (value < lstNodeData.get(i)) {
                        childNode = currNode.getLstChildrenNode().get(i);
                        break;
                    }
                    // 已经到最后一个元素了
                    if (i == lstNodeData.size() - 1) {
                        childNode = currNode.getLstChildrenNode().get(i + 1);
                    }
                }
                add(childNode, currNode, value);
            }
            // 节点元素数量等于最大允许元素数量, 则需要向上抽取, 保证叶子节点永远在同一层, 且为满叉树
            if (currNode.getLstNodeData().size() > currNode.getMaxElementCount()) {
                // 获取需要向上抽取的元素
                Integer upData = currNode.getLstNodeData().get(1);

                // 先向上处理父节点
                // 将向上抽取的元素, 添加到父节点中, 并返回添加到父节点的索引
                if (null == parentNode) {
                    parentNode = new Node(maxElementCount);
                    root = parentNode;
                }
                int index = parentNode.add(upData);
                // 遍历当前节点, 将当前节点按索引1进行所有拆分为左节点和右节点,
                List<Integer> lstLeftData = new ArrayList<>(10);
                List<Integer> lstRightData = new ArrayList<>(10);
                for (int i = 0; i < currNode.getLstNodeData().size(); i++) {
                    if (i < 1) {
                        lstLeftData.add(currNode.getLstNodeData().get(i));
                    } else if (i > 1) {
                        lstRightData.add(currNode.getLstNodeData().get(i));
                    }
                }
                Node leftNode = new Node(maxElementCount);
                leftNode.setLstNodeData(lstLeftData);
                Node rightNode = new Node(maxElementCount);
                rightNode.setLstNodeData(lstRightData);
                // 比如对于2-3树, 如果当前父节点有2个数据, 同时有三个子节点
                // 此时中间的子节点(index=1), 添加了一个数据, 节点数据数量为3, 需要提升一个数据到父节点
                // 则父节点的节点数据数量此时为3(暂不提升), 提升上来的数据索引为1(中间节点), 并且对应的子节点应该有四个,
                // 此时对应的子节点数量是3个, 需要对中间子节点进行拆分, 索引0和索引2的子节点不变
                // 索引1的子节点提升了内部索引为1的数据到父节点, 则对该子节点内部按索引1进行拆分为两个节点
                // 将这两个节点放在父节点的子节点索引1和索引2的位置, 原索引0位置不变, 索引2位置后移一位
                List<Node> lstParentChildrenNode = parentNode.getLstChildrenNode();
                // 循环之后, 会直接将currNode挂空, 等待GC回收
                if (CollectionUtils.isEmpty(lstParentChildrenNode)) {
                    lstParentChildrenNode.add(leftNode);
                    lstParentChildrenNode.add(rightNode);
                } else {
                    List<Node> lstNewChildrenNode = new ArrayList<>(10);
                    for (int i = 0; i < lstParentChildrenNode.size(); i++) {
                        if (i == index) {
                            lstNewChildrenNode.add(leftNode);
                            lstNewChildrenNode.add(rightNode);
                            continue;
                        }
                        lstNewChildrenNode.add(lstParentChildrenNode.get(i));
                    }
                    parentNode.setLstChildrenNode(lstNewChildrenNode);
                }

                // 再向下处理子节点
                // 此时当前节点已经被拆分为leftNode和rightNode两个节点,
                // 其中leftNode只包含原索引为1的元素, rightNode包含索引2以及之后的所有元素
                // 此时子节点数量为maxElementCount + 2, 即2-3树此时会有4个子节点
                // 将索引0和索引1的子节点分给左侧节点, 其他子节点分给右侧节点
                List<Node> lstChildChildrenNode = currNode.getLstChildrenNode();
                for (int i = 0; i < lstChildChildrenNode.size(); i++) {
                    if (i <= 1) {
                        leftNode.getLstChildrenNode().add(lstChildChildrenNode.get(i));
                    } else {
                        rightNode.getLstChildrenNode().add(lstChildChildrenNode.get(i));
                    }
                }
            }
        }

    }

    @Data
    static class Node {

        // 节点最大元素数量, 默认表示2-3树
        private int maxElementCount = 2;

        // 节点元素列表
        private List<Integer> lstNodeData;

        // 子节点列表
        private List<Node> lstChildrenNode;

        Node(int maxElementCount) {
            this(maxElementCount, null);
        }

        Node(int maxElementCount, Integer value) {
            this.maxElementCount = maxElementCount;
            lstNodeData = new ArrayList<>(10);
            lstChildrenNode = new ArrayList<>(10);
            if (null != value) {
                add(value);
            }
        }

        int add(Integer value) {
            int index = 0;
            if (CollectionUtils.isEmpty(lstNodeData)) {
                lstNodeData.add(value);
            } else {
                for (index = 0; index < lstNodeData.size(); index++) {
                    if (value < lstNodeData.get(index)) {
                        break;
                    }
                }
                lstNodeData.add(index, value);
            }
            return index;
        }

    }
}
