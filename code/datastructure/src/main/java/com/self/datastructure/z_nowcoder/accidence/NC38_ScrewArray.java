package com.self.datastructure.z_nowcoder.accidence;

import java.util.ArrayList;

/**
 * NC38: 螺旋矩阵
 * 给定一个m x n大小的矩阵（m行，n列），按螺旋的顺序返回矩阵中的所有元素。
 * 输入: [[1,2,3],[4,5,6],[7,8,9]]
 * 输出: [1,2,3,6,9,8,7,4,5]
 *
 * 输入数组如:
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * 螺旋输出: 即以[0, 0]位置为起点, 以横轴为初始方向, 遇边右转的行进方式, 已经访问的元素不再多次访问
 * 即螺旋输出顺序:
 * 1 -> 2 -> 3
 *           |
 * 4 -> 5    6
 * |         |
 * 7 <- 8 <- 9
 * 最终输出结果为: 1, 2, 3, 6, 9, 8, 7, 4, 5
 * @author PJ_ZHANG
 * @create 2021-04-22 11:00
 **/
public class NC38_ScrewArray {

    public static void main(String[] args) {
        System.out.println(screwArray(new int[][] {{1, 2, 3},{4, 5, 6},{7, 8, 9}}));
        System.out.println(screwArray(new int[][] {{2,3}}));
    }

    /**
     * * 以螺旋的一个圈为一次循环,
     * * 以一维数组为纵坐标, 二维数组为横坐标时
     * * 在这个圈中会完成左, 下, 右, 上四个步骤,
     * * 右: 横坐标不变, 纵坐标递增(当前二位数组操作)
     *       执行完成后进入下一个二维数组, 即横坐标加1
     * * 下: 纵坐标不变, 横坐标递增(在所有一维数组的该位置操作)
     *       执行完成后进入前一个索引位置, 即纵坐标减1
     * * 左: 横坐标不变, 纵坐标递减(当前二维数组操作)
     *       执行完成后进入前一个二维数组, 即横坐标减1
     * * 上: 纵坐标不变, 横坐标递减(固定二维数组索引, 在一维数组间切换)
     *       执行完成后进入后一个索引位置, 即纵坐标+1
     *
     * @param matrix
     * @return
     */
    public static ArrayList<Integer> screwArray(int[][] matrix) {
        ArrayList<Integer> lstData = new ArrayList<>();
        if (matrix.length == 0) {
            return lstData;
        }
        // 定义开始的纵横坐标
        int startX = 0, startY = 0;
        // 定义结束的纵横坐标
        int endX = matrix.length - 1, endY = matrix[0].length - 1;
        // 当startX <= endX, startY <= endY时, 说明还存在未处理完的数据, 循环继续
        // 考虑一行或者一列的情况
        for (;startX <= endX && startY <= endY;) {
            // 向右: 横坐标不变, 纵坐标递增
            for (int i = startY; i <= endY; i++) {
                lstData.add(matrix[startX][i]);
            }
            startX++;

            // 向下: 纵坐标不变, 横坐标递增
            for (int i = startX; i <= endX; i++) {
                lstData.add(matrix[i][endY]);
            }
            endY--;

            // 开始横坐标小于结束横坐标, 说明该点位没有执行过
            if (startX <= endX) {
                // 向左: 横坐标不变, 纵坐标递减
                for (int i = endY; i >= startY; i--) {
                    lstData.add(matrix[endX][i]);
                }
                endX--;
            }

            // 开始纵坐标小于结束纵坐标, 说明该点位没有执行过
            if (startY <= endY) {
                // 向上: 纵坐标不变, 横坐标递减
                for (int i = endX; i >= startX; i--) {
                    lstData.add(matrix[i][startY]);
                }
                startY++;
            }
        }
        return lstData;
    }

}
