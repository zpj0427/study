package com.self.datastructure.z_nowcoder.easy;

import java.util.LinkedList;
import java.util.List;

/**
 * NC13: 二叉树最大深度
 * 求给定二叉树的最大深度，
 * 最大深度是指树的根结点到最远叶子结点的最长路径上结点的数量
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 14:59
 **/
public class NC13_TreeMaxDepth {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(0);
        treeNode.left = new TreeNode(11);
        treeNode.right = new TreeNode(12);
//        treeNode.left.left = new TreeNode(21);
//        treeNode.left.right = new TreeNode(22);
//        treeNode.right.left = new TreeNode(211);
//        treeNode.right.left.left = new TreeNode(311);
        System.out.println(maxDepth_1(treeNode));
    }

    /**
     * 非递归方式
     * * 逐层进行数据遍历, 去第一层数据后, 将数据存储在集合中, 此时高度为1
     * * 第一层处理完后, 集合中数据为第一层数据
     * * 遍历集合, 取第一层数据的第二层数据, 加入到集合中, 此时高度为2, 并移除第一层数据
     * * 以此类推, 知道集合中没有数据, 此时高度为最大高度
     * @param root
     * @return
     */
    public static int maxDepth_1(TreeNode root) {
        if (null == root) {
            return 0;
        }
        LinkedList<TreeNode> lstNode = new LinkedList<>();
        lstNode.add(root);
        int depth = 0;
        for (;!lstNode.isEmpty();) {
            depth++;
            int length = lstNode.size();
            for (int i = 0; i < length; i++) {
                TreeNode node = lstNode.poll();
                if (null != node.left) lstNode.add(node.left);
                if (null != node.right) lstNode.add(node.right);
            }
        }
        return depth;
    }

    /**
     * 递归方式
     * * 分别获取左子树和右子树的高度
     * * 取较深子树值, 加上根节点的高度返回
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return left > right ? left + 1 : right + 1;
    }

}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {this.val = val;}
}
