package com.self.datastructure.z_nowcoder;

import java.util.Scanner;

/**
 * @author PJ_ZHANG
 * @create 2021-05-18 17:03
 **/
public class Test_3 {

    public static void main(String[] args) {
        sum();
    }

    public static void sum() {
        Scanner scanner = new Scanner(System.in);
        String desc = scanner.nextLine();
        String[] descArr = desc.split(" ");
        try {
            int totalPerson = Integer.valueOf(descArr[0]);
            int totalTitle = Integer.valueOf(descArr[1]);
            if (totalPerson < 1 || totalPerson >= 100000) {
                System.out.println("NULL");
            }
            if (totalTitle < 1 || totalTitle >= 100000) {
                System.out.println("NULL");
            }
            TreeNode rootNode = new TreeNode(-1);
            for (int i = 0; i < totalTitle; i++) {
                String message = scanner.nextLine();
                String[] messageArr = message.split(" ");
                int a = Integer.valueOf(messageArr[0]);
                int b = Integer.valueOf(messageArr[1]);
                int c = Integer.valueOf(messageArr[2]);
                if (c == 0) {
                    addNode(rootNode, a);
                    addNode(rootNode, b);
                } else if (c == 1) {
                    boolean aFlag = checkTreeNode(rootNode, a);
                    boolean bFlag = checkTreeNode(rootNode, b);
                    if (aFlag && bFlag) {
                        System.out.println("we are a team");
                    } else {
                        System.out.println("we are not a team");
                    }
                } else {
                    System.out.println("da pian zi");
                }
            }
        } catch (Exception e) {
            System.out.println("NULL");
        }
    }

    private static boolean checkTreeNode(TreeNode rootNode, int a) {
        TreeNode tempNode = rootNode;
        for (;;) {
            if (null == tempNode) {
                return false;
            }
            if (a > tempNode.value) {
                tempNode = tempNode.rightNode;
            } else if (a < tempNode.value) {
                tempNode = tempNode.leftNode;
            } else {
                return true;
            }
        }
    }

    private static void addNode(TreeNode root, int a) {
        TreeNode tempNode = root;
        for (;;) {
            if (tempNode.value > a) {
                if (tempNode.leftNode == null) {
                    tempNode.leftNode = new TreeNode(a);
                    return;
                } else {
                    tempNode = tempNode.leftNode;
                }
            } else if (tempNode.value < a) {
                if (tempNode.rightNode == null) {
                    tempNode.rightNode = new TreeNode(a);
                    return;
                } else {
                    tempNode = tempNode.rightNode;
                }
            } else {
                return;
            }
        }
    }

}

class TreeNode {
    int value;
    TreeNode leftNode;
    TreeNode rightNode;
    public TreeNode(int value) {
        this.value = value;
    }
}
