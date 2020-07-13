package com.self.datastructure.algorithm.horse;

import java.awt.Point;
import java.util.*;

/**
 * 马踏棋盘问题_不通过贪心算法求解
 * * 此处通过递归加回溯进行马踏棋盘求解
 * * 先初始化一个棋盘, 并对应的确定纵横坐标
 * * 定义一点作为马踏棋盘的出发点, 并取该点可以走的附近最多8个坐标
 * * 遍历每一个可走的点, 如果该点没有被走过, 则继续以该点进行深度遍历
 * * 对每一个已经被访问的点标记为已读
 * * 全部遍历完成后, 如果马踏棋盘失败, 则重置棋盘为0值
 * @author pj_zhang
 * @create 2020-07-13 22:07
 **/
public class Horse {

    /**
     * 横坐标最大值
     */
    private static int MAX_X;

    /**
     * 纵坐标最大值
     */
    private static int MAX_Y;

    private static boolean flag;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("开始进行计算");
        // 构建一个8*8的棋盘
        MAX_X = 8;
        MAX_Y = 8;
        int[][] chessBoardArr = new int[MAX_X][MAX_Y];
        horse(chessBoardArr, 0, 0, 1);
        System.out.println("计算完成...., cost: " + (System.currentTimeMillis() - start));
        for (int[] data : chessBoardArr) {
            System.out.println(Arrays.toString(data));
        }
    }

    /**
     * 马踏棋盘求解
     * @param chessBoardArr 构架棋盘的二维数组
     * @param row 当前访问的横坐标
     * @param column 当前访问的纵坐标
     * @param step 当前走的步数
     */
    private static void horse(int[][] chessBoardArr, int row, int column, int step) {
        // 默认当前点已经被访问, 并且走的步数为step
        chessBoardArr[row][column] = step;
        // 获取邻接的最多8个可选步数
        List<Point> lstData = getNextPoint(new Point(row, column));

        // 使用贪心算法对访问的下一个点进行优化
        // 将下一个可能访问到的点集合进行排序
        // 按下一个点能访问到的下一个点的数量进行增量排序
        // 因为基础逻辑是基于深度遍历优先
        // 如果访问点的下一次可选点是最小的, 则可能让这一轮深度尽快完成
        // 不使用贪心算法优化时, 不排序即可
        sort(lstData);

        for (Point point : lstData) {
            // 判断节点没有被访问
            if (chessBoardArr[point.x][point.y] == 0) {
                // 递归根据深度遍历优先原则, 进行访问
                horse(chessBoardArr, point.x, point.y, step + 1);
            }
        }
        // 节点遍历完成后, 判断是否完成
        // 如果当前走的步数没有覆盖完整个棋盘, 说明失败, 则对棋盘该位置进行置0
        // 此处也就是回溯的逻辑所在, 该点走错了, 回去继续走
        // 如果全部走错了, 也就完全归0了
        if (step < MAX_Y * MAX_X && !flag) {
            chessBoardArr[row][column] = 0;
        } else {
            // 表示全部走完
            flag = true;
        }
    }

    /**
     * 贪心算法优化
     * 对下一步可能访问的节点按照该节点下一步可能访问节点的数量增序排列
     * 在初始深度遍历时, 减少遍历的次数
     * @param lstPoint
     */
    private static void sort(List<Point> lstPoint) {
        Collections.sort(lstPoint, (o1, o2) -> (getNextPoint(o1).size() - getNextPoint(o2).size()));
    }

    /**
     * 获取当前节点的周边可走节点
     * point.x: 表示横坐标
     * point.y: 表示纵坐标
     * @param point 当前节点
     * @return 周边可走节点集合, 最多为8个点
     */
    private static List<Point> getNextPoint(Point point) {
        List<Point> lstPoint = new ArrayList<>(10);
        Point addPoint = new Point();
        // 5号点位, 横坐标-2, 纵坐标-1
        if ((addPoint.x = point.x - 2) >= 0 && (addPoint.y = point.y - 1) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 6号点位, 横坐标-1, 纵坐标-2
        if ((addPoint.x = point.x - 1) >= 0 && (addPoint.y = point.y - 2) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 7号点位, 横坐标+1, 纵坐标-2
        if ((addPoint.x = point.x + 1) < MAX_X && (addPoint.y = point.y - 2) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 0号点位, 横坐标+2, 纵坐标-1
        if ((addPoint.x = point.x + 2) < MAX_X && (addPoint.y = point.y - 1) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 1号点位, 横坐标+2, 纵坐标+1
        if ((addPoint.x = point.x + 2) < MAX_X && (addPoint.y = point.y + 1) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 2号点位, 横坐标+1, 纵坐标+2
        if ((addPoint.x = point.x + 1) < MAX_X && (addPoint.y = point.y + 2) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 3号点位, 横坐标-1, 纵坐标+2
        if ((addPoint.x = point.x - 1) >= 0 && (addPoint.y = point.y + 2) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 4号点位, 横坐标-2, 纵坐标+1
        if ((addPoint.x = point.x - 2) >= 0 && (addPoint.y = point.y + 1) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 最终返回最多8个点位
        return lstPoint;
    }

}
