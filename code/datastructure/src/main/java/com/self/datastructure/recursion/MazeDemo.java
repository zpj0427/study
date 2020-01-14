package com.self.datastructure.recursion;

/**
 * 递归_迷宫问题处理
 * 1, 迷宫二维地图初始化状态为0, 1表示墙壁, 2表示已经走过的路, 3表示死路
 * 2, 约定迷宫走路策略: 下 -> 右 -> 上 -> 左, 逆时针尝试走
 * @author LiYanBin
 * @create 2020-01-13 16:46
 **/
public class MazeDemo {

    public static void main(String[] args) {
        // 初始化迷宫, 初始化二维地图
        int[][] map = initMap(8, 8);
        // 初始化地图
        showDetails(map);
        // 从指定坐标开始找路, 抵达目的地
        boolean hasWay = runWay(map, 1, 1, 6, 6);
        showDetails(map);
    }

    /**
     * 迷宫走路
     * @param map 地图
     * @param currFirstIndex 当前位置的一维坐标
     * @param currSecondIndex 当前位置的二维坐标
     * @param targetFirstIndex 目标诶只的一维坐标
     * @param targetSecondIndex 目标位置的二维坐标
     * @return
     */
    private static boolean runWay(int[][] map, int currFirstIndex, int currSecondIndex,
                                  int targetFirstIndex, int targetSecondIndex) {
        System.out.println("打印当前地图");
        showDetails(map);
        // 0表示初始化地图, 即未走
        // 1表示墙壁或者障碍, 走不通
        // 2表示已走
        // 3表示死路
        // 目标节点以走到, 则返回true, 并顺序返回
        if (currFirstIndex == targetFirstIndex && currSecondIndex == targetSecondIndex) {
            map[targetFirstIndex][targetSecondIndex] = 2;
            return true;
        } else { // 目标节点不为2, 则继续迷宫探路
            // 为0表示未走过
            if (map[currFirstIndex][currSecondIndex] == 0 || map[currFirstIndex][currSecondIndex] == 2) {
                // 修改为2, 表示已经走过
                map[currFirstIndex][currSecondIndex] = 2;
                // 先进行未知区域探索
                // 向下
                if (runUnkownArea(map, currFirstIndex + 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向右
                if (runUnkownArea(map, currFirstIndex, currSecondIndex + 1, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向上
                if (runUnkownArea(map, currFirstIndex - 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向下
                if (runUnkownArea(map, currFirstIndex, currSecondIndex - 1, targetFirstIndex, targetSecondIndex))
                    return true;
                // 未知区域探索完成, 无路可走, 当前节点置位无效
                map[currFirstIndex][currSecondIndex] = 3;
                // 进行节点回溯, 继续探索
                // 向下
                if (runkownArea(map, currFirstIndex + 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向右
                if (runkownArea(map, currFirstIndex, currSecondIndex + 1, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向上
                if (runkownArea(map, currFirstIndex - 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向下
                if (runkownArea(map, currFirstIndex, currSecondIndex - 1, targetFirstIndex, targetSecondIndex))
                    return true;
            } else { // 不为0或者2, 表示是墙壁或者死路
                return false;
            }
        }
        return false;
    }

    // 区域回溯
    private static boolean runkownArea(int[][] map, int nextFirstIndex, int nextSecondIndex, int targetFirstIndex, int targetSecondIndex) {
        if (map[nextFirstIndex][nextSecondIndex] != 3
                && runWay(map, nextFirstIndex, nextSecondIndex, targetFirstIndex, targetSecondIndex)) {
            return true;
        }
        return false;
    }

    // 探索未知区域
    private static boolean runUnkownArea(int[][] map, int nextFirstIndex, int nextSecondIndex, int targetFirstIndex, int targetSecondIndex) {
        if (map[nextFirstIndex][nextSecondIndex] == 0
                && runWay(map, nextFirstIndex, nextSecondIndex, targetFirstIndex, targetSecondIndex)) {
            return true;
        }
        return false;
    }

    /**
     * 初始化地图
     * @param firstIndexCount 一位长度
     * @param secondIndexCount 二维长度
     * @return
     */
    private static int[][] initMap(int firstIndexCount, int secondIndexCount) {
        // 初始化地图, 此时地图全部状态为0
        int[][] map = new int[firstIndexCount][secondIndexCount];
        // 为地图设置围墙,
        // 设置上下,
        // [firstIndexCount - 1, 0], [0, secondIndexCount - 1]
        // [firstIndexCount - 1, secondIndexCount - 1]
        for (int i = 0; i < firstIndexCount; i++) {
            map[i][0] = 1;
            map[i][secondIndexCount - 1] = 1;
        }
        // 设置左右,
        // [0, 0] -> [0, secondIndexCount - 1],
        // [firstIndexCount - 1, 0] -> [firstIndexCount - 1, secondIndexCount - 1]
        for (int i = 0; i < secondIndexCount; i++) {
            map[0][i] = 1;
            map[firstIndexCount - 1][i] = 1;
        }
        // 设置障碍
        map[3][1] = 1;
        map[3][2] = 1;
        map[2][2] = 1;
        map[4][4] = 1;
        map[5][4] = 1;
        map[6][4] = 1;
        return map;
    }

    public static void showDetails(int[][] map) {
        for (int[] firstIndexData : map) {
            for (int secondIndexData : firstIndexData) {
                System.out.print(secondIndexData + " ");
            }
            System.out.println();
        }
    }

}
