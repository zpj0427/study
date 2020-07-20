package com.self.datastructure.tree.redblacktree;

import com.sun.org.apache.bcel.internal.generic.RETURN;
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
        // 添加数据层, 这一组数据为了构造全黑树
        selfRedBlackTree.add(200);
        selfRedBlackTree.add(100);
        selfRedBlackTree.add(300);
        selfRedBlackTree.add(50);
        selfRedBlackTree.add(150);
        selfRedBlackTree.add(250);
        selfRedBlackTree.add(4000);
        selfRedBlackTree.add(20);
        selfRedBlackTree.add(70);
        selfRedBlackTree.add(120);
        selfRedBlackTree.add(180);
        selfRedBlackTree.add(220);
        selfRedBlackTree.add(280);
        selfRedBlackTree.add(350);
        selfRedBlackTree.add(4500);
        selfRedBlackTree.add(10);
        selfRedBlackTree.delete(4500);
        selfRedBlackTree.delete(350);
        selfRedBlackTree.delete(280);
        selfRedBlackTree.delete(220);
        selfRedBlackTree.delete(220);
        selfRedBlackTree.delete(180);
        selfRedBlackTree.delete(120);
        selfRedBlackTree.delete(10);
        selfRedBlackTree.delete(20);
        selfRedBlackTree.delete(70);
        selfRedBlackTree.delete(50);
    }

    static class SelfRedBlackTree {
        private Node root = null;

        /**
         * 删除节点
         * @param value
         */
        public boolean delete(int value) {
            // 获取有效的数据, 节点不存在, 返回删除失败
            Node delNode = findNode(this.root, value);
            if (null == delNode) {
                return false;
            }
            return doDeleteNode(delNode);
        }

        private boolean doDeleteNode(Node delNode) {
            // 进行节点删除, 此处需要分三个大场景进行判断
            // 1, 删除节点存在两个子节点
            // 可以转变为删除右侧最左节点 即后继节点
            if (null != delNode.getLeftNode() && null != delNode.getRightNode()) {
                // 取右侧最左节点
                Node nextNode = delNode.getRightNode();
                for (;null != nextNode.getLeftNode(); nextNode = nextNode.getLeftNode());
                // 覆盖值
                delNode.setValue(nextNode.getValue());
                // 再掉一次删除, 此处注意不是递归, 只是再掉一次, 第二次进来不可能是双子节点
                return doDeleteNode(nextNode);
            } else {
                // 2, 删除节点为单子节点
                // 3, 删除节点没有子节点
                // 先对删除节点为根情况进行分析
                // 为根时, 就是删除黑子单子节点或者直接删根,
                if (null == delNode.getParentNode()) {
                    // 子节点可能存在也可能不存在
                    Node childNode = null == delNode.getLeftNode() ? delNode.getRightNode() : delNode.getLeftNode();
                    // 如果存在, 设置子节点为根节点
                    this.root = childNode;
                    // 子节点存在时, 进行失黑修复, 将子节点设置为根节点并且颜色为黑
                    // 子节点不存在时, 树直接就空了
                    if (null != childNode) {
                        childNode.setParentNode(null);
                        childNode.setRed(false);
                    }
                    return true;
                } else {
                    // 父节点, 保存一份, 防止引用传递丢失
                    Node parentNode = null;
                    // 删除节点不是根节点
                    // 将删除节点挂空, 对父节点的子节点和子节点的父节点进行替换
                    // 取子节点, 子节点有一个或者没有, 修改子节点的父节点
                    Node childNode = null == delNode.getLeftNode() ? delNode.getRightNode() : delNode.getLeftNode();
                    if (null != childNode) {
                        childNode.setParentNode(delNode.getParentNode());
                    }
                    // 取父节点, 判断当前节点是父节点的左子还是右子节点
                    parentNode = delNode.getParentNode();
                    if (delNode == parentNode.getLeftNode()) {
                        parentNode.setLeftNode(childNode);
                    } else if (delNode == parentNode.getRightNode()) {
                        parentNode.setRightNode(childNode);
                    }

                    // 删除完成后, 如果删除节点为黑节点, 进行失黑修复
                    // 如果为红节点, 则不用处理
                    // 此处是删除场景1, 删除节点为红色节点
                    // 此时该节点一定是叶子节点
                    // 如果该节点存在单子节点, 因为不能双红, 所以该子节点一定为黑
                    // 如果存在一个黑子节点, 则必须存在两个黑子节点, 否在违反黑高
                    // 所以该节点一定是红色节点
                    // 此时直接删除该节点, 因为删除不包含子节点的红色节点不影响红黑树性质, 可以直接删除, 不用考虑修复
                    if (!delNode.isRed()) {
                        fixedLostBlack(delNode, parentNode);
                    }
                }
            }
            return true;
        }

        /**
         * 黑色节点删除, 进行失黑修复
         * @param delNode
         * @param parentNode 原父节点, 只做保留原始数据和传参用, 获取树中的父节点, 可以直接getParent
         */
        private void fixedLostBlack(Node delNode, Node parentNode) {
            // 场景二: delNode存在单子节点,
            // 因为delNode为黑, 根据红黑树原则, 其单子节点一定是红节点
            if (null != delNode.getLeftNode() || null != delNode.getRightNode()) {
                // 删除节点后会导致黑高少1, 此时将红色变黑, 则黑高
                Node childNode = null == delNode.getLeftNode() ? delNode.getRightNode() : delNode.getLeftNode();
                childNode.setRed(false);
                return;
            }

            // 循环是对全黑情况下的向上递归处理
            // 如果在递归过程中遇到了红色父节点或者兄弟节点会直接平衡完成, 无需继续处理
            // 否则最终递归到根节点
            while (this.root != delNode) {
                Node brotherNode = null;
                // 场景三: delNode不存在单子节点, 此时在它的子树内处理已经不可能, 需要连接其他子树
                // 此处分两种场景进行处理, 分别为当前节点为左节点和右节点
                // 走到此处, 说明删除节点没有子节点, 则父节点的对应位置为null
                if (null == parentNode.getLeftNode() || delNode == parentNode.getLeftNode()) {
                    // 当前节点为左侧, 兄弟节点为右侧
                    brotherNode = parentNode.getRightNode();
                    // 先考虑兄弟节点为红的情况
                    if (brotherNode.isRed()) {
                        // 兄弟节点为红, 则父节点必定为黑
                        // 兄弟节点必定存在两个为黑的子节点
                        // 在删除之前, 该子树上的黑节点一定是平衡的
                        // 此时以父节点为中心节点进行左旋转
                        leftRotateWithoutChange(brotherNode, parentNode);
                        // 再将父节点变为红色, 兄弟节点变为黑色
                        // 即是旋转后的兄弟节点变为黑色, 兄弟节点的左子节点变红
                        brotherNode.setRed(false);
                        brotherNode.getLeftNode().setRed(true);
                        // 重新赋值父节点和兄弟节点
                        parentNode = brotherNode.getLeftNode();
                        brotherNode = parentNode.getRightNode();
                        // 此时兄弟节点的左侧节点挂到父节点的右侧节点, 且为黑色
                        // 那么对于下沉下来变为红色的父节点来说, 此时存在一个删除掉的黑色节点和旋转过来的黑色节点
                        // 这时候问题转化为删除节点为黑, 且无子节点, 兄弟节点为黑的情况了
                        // 留到下一种情况继续处理
                    }
                    // 兄弟节点为黑, 无论上一个循环有没有走到, 都会走到这一步
                    // 如果走到了上一步, 那上一步也会遗留一个问题到这一步解决
                    if (!brotherNode.isRed()) {
                        // case1: 兄弟节点不存在子节点, 父节点为红色
                        // 这种场景先是一种情况, 然后会延续处理上一个情况遗留的问题
                        if (null == brotherNode.getRightNode() && null == brotherNode.getLeftNode()
                                && brotherNode.getParentNode().isRed()) {
                            // 父节点为红色时, 兄弟节点必定为黑
                            // 该子树只有一层黑节点, 删除当前节点, 剩下红色父节点和黑色兄弟节点
                            // 可以将父节点和兄弟节点的颜色互换, 保证该子树黑节点层数不变
                            boolean brotherColor = brotherNode.isRed();
                            brotherNode.setRed(parentNode.isRed());
                            parentNode.setRed(brotherColor);
                            return; // 处理完成可以退出
                        }
                        // case2: 兄弟节点右子节点为红, 左子节点颜色无所谓(要么为空, 要么为空), 父节点颜色随意
                        // 此时从父节点 - 兄弟节点 - 兄弟节点右子节点构成RR, 需一次旋转
                        if (null != brotherNode.getRightNode() && brotherNode.getRightNode().isRed()) {
                            // 删除之后, 左侧没有黑节点, 右侧有一个兄弟节点为黑, 有一个兄弟节点的右子节点为红, 且父节点颜色随意
                            // 先变色, 将兄弟节点染为父节点颜色, 将父节点染黑, 将右子节点染黑, 然后以父节点为中心进行左旋
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            brotherNode.setRed(parentColor);
                            brotherNode.getRightNode().setRed(false);
                            // 再进行左旋
                            leftRotateWithoutChange(brotherNode, brotherNode.getParentNode());
                            return; // 处理完成
                        }
                        // case3: 兄弟节点左子节点为红, 左子节点无所谓(要么为空, 要么为红), 父节点颜色随意
                        // 此时从父节点 - 兄弟节点 - 兄弟节点左子节点构成RL, 需两次旋转
                        if (null != brotherNode.getLeftNode() && brotherNode.getLeftNode().isRed()) {
                            // 先以兄弟节点为中心点进行右旋, 旋转暂时不变颜色
                            rightRotateWithoutChange(brotherNode.getLeftNode(), brotherNode);
                            // 此处注意旋转后位置已经变化
                            // 此时父节点的右子节点变为原兄弟节点的左子节点, 父节点右子节点的右子节点变为原兄弟节点
                            // 将父节点染黑, 父节点的右子节点变为父节点原来的颜色, 再以父节点为中心进行左旋, 结果同上
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            parentNode.getRightNode().setRed(parentColor);
                            leftRotateWithoutChange(parentNode.getRightNode(), parentNode);
                            return; // 处理完成
                        }
                    }
                } else if (null == parentNode.getRightNode() || delNode == parentNode.getRightNode()) {
                    // 为右节点处理, 与左节点相反
                    brotherNode = parentNode.getLeftNode();
                    if (brotherNode.isRed()) {
                        rightRotateWithoutChange(brotherNode, parentNode);
                        brotherNode.setRed(false);
                        brotherNode.getRightNode().setRed(true);
                    }
                    if (!brotherNode.isRed()) {
                        if (null == brotherNode.getRightNode() && null == brotherNode.getLeftNode()
                                && brotherNode.getParentNode().isRed()) {
                            boolean brotherColor = brotherNode.isRed();
                            brotherNode.setRed(parentNode.isRed());
                            parentNode.setRed(brotherColor);
                            return;
                        }
                        if (null != brotherNode.getLeftNode() && brotherNode.getLeftNode().isRed()) {
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            brotherNode.setRed(parentColor);
                            brotherNode.getLeftNode().setRed(false);
                            rightRotateWithoutChange(brotherNode, brotherNode.getParentNode());
                            return;
                        }
                        if (null != brotherNode.getRightNode() && brotherNode.getRightNode().isRed) {
                            leftRotateWithoutChange(brotherNode.getRightNode(), brotherNode);
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            parentNode.getLeftNode().setRed(parentColor);
                            rightRotateWithoutChange(parentNode.getLeftNode(), parentNode);
                            return;
                        }
                    }
                }
                // case4: 兄弟节点不存在子节点, 父节点为黑色
                // 父节点为黑, 当前节点为黑, 兄弟节点不存在子节点, 则兄弟节点必然为黑
                // 后续递归向上进行全黑处理时, 兄弟节点是肯定存在子节点的
                // 这里只对兄弟节点颜色进行判断, 如果兄弟节点存在子节点符合其他条件, 则在上面分支中已经判断完成
                if (!brotherNode.isRed() && !parentNode.isRed()) {
                    // 此时就是全黑场景, 左侧子树删除一个黑节点, 此时黑色少1, 右侧也没有多余节点补充, 此时只能递归调整数的黑高
                    // 现将兄弟节点改为红色
                    brotherNode.setRed(true);
                    // 再递归向上一直调整各个子树, 直到整个树的黑高平衡
                    delNode = parentNode;
                    parentNode = parentNode.getParentNode();
                }
            }
        }

        /**
         * 查找指定节点
         * @param node
         * @param value
         * @return
         */
        private Node findNode(Node node, int value) {
            if (null == node) {
                return null;
            }
            if (value < node.getValue()) {
                return findNode(node.getLeftNode(), value);
            } else if (value > node.getValue()) {
                return findNode(node.getRightNode(), value);
            } else {
                return node;
            }
        }

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
            leftRotateWithoutChange(parentNode, grandpaNode);
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
            rightRotateWithoutChange(parentNode, grandpaNode);
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
            // 构造父节点为节点
            Node newNode = new Node(parentNode.getValue(), parentNode.isRed());
            // 父节点的右子节点不变
            newNode.setRightNode(parentNode.getRightNode());
            if (null != parentNode.getRightNode()) {
                parentNode.getRightNode().setParentNode(newNode);
            }
            // 父节点的左子节点为当前节点的右子节点
            newNode.setLeftNode(currNode.getRightNode());
            if (null != currNode.getRightNode()) {
                currNode.getRightNode().setParentNode(newNode);
            }
            // 当前节点的右子节点为新节点, 当前节点的左子节点不变
            currNode.setRightNode(newNode);
            newNode.setParentNode(currNode);
            // 当前节点的父节点, 为父节点的父节点
            currNode.setParentNode(parentNode.getParentNode());
            // 如果祖父节点为根节点, 则替换根节点为父节点
            if (root == parentNode) {
                root = currNode;
            }
            // 如果祖父节点不为根节点, 则替换祖父父节点的右子节点为父节点
            else {
                parentNode.getParentNode().setRightNode(currNode);
            }
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
            // 构造父节点为节点
            Node newNode = new Node(parentNode.getValue(), parentNode.isRed());
            // 父节点的左子节点不变
            newNode.setLeftNode(parentNode.getLeftNode());
            if (null != parentNode.getLeftNode()) {
                parentNode.getLeftNode().setParentNode(newNode);
            }
            // 父节点的右子节点为当前节点的左子节点
            newNode.setRightNode(currNode.getLeftNode());
            if (null != currNode.getLeftNode()) {
                currNode.getLeftNode().setParentNode(newNode);
            }
            // 当前节点的左子节点为新节点, 当前节点的右子节点不变
            currNode.setLeftNode(newNode);
            newNode.setParentNode(currNode);
            // 当前节点的父节点, 为父节点的父节点
            currNode.setParentNode(parentNode.getParentNode());
            if (root == parentNode) {
                root = currNode;
            }
            // 如果祖父节点不为根节点, 则替换祖父父节点的左子节点为父节点
            else {
                parentNode.getParentNode().setLeftNode(currNode);
            }
            // 这样会直接将原来的parentNode挂空, 等待GC回收
        }
    }

    @Data
    static class Node {

        // 数据
        private int value;

        // 左子节点
        private Node leftNode;

        // 右子节点
        private Node rightNode;

        // 父节点
        private Node parentNode;

        // 是否红色节点
        private boolean isRed = true;

        Node(int value) {
            this.value = value;
        }

        Node(int value, boolean isRed) {
            this.value = value;
            this.isRed = isRed;
        }

        Node (Node node) {
            this.value = node.getValue();
            this.leftNode = node.getLeftNode();
            this.rightNode = node.getRightNode();
            this.parentNode = node.getParentNode();
            this.isRed = node.isRed();
        }

    }
}
