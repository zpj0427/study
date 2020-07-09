package com.self.datastructure.algorithm.prim;

import java.util.Arrays;

/**
 * 普里姆算法
 * * 假设顶点之前已经全部存在关联, 没有关联的用一个最大值表示
 * * 从任意一个指定顶点开始, 并将该顶点标记为已读
 * * 将所有已读顶点与所有未读顶点的关联权值进行比较, 取出最小的关联权值
 * * 此时该已读顶点与该未读顶点构成了当前场景下的最优路径(此处类似于贪心算法, 每一步都要最优解, 都要最小路径)
 * * 并将该未读顶点标记为已读顶点
 * * 重复第三步, 直到所有路径都构建完成
 * * 在N个顶点时, 路径有N-1条
 * @author PJ_ZHANG
 * @create 2020-07-09 15:01
 **/
public class Prim {

    /**
     * 表示顶点未连接
     */
    private static final int NOT_CONNECT = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点列表
        char[] vertexArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 顶点对应的与各个顶点的连接情况, 此处没有排除顶点自连接
        // NOT_CONNECT表示没有连接, 且二维顺序与顶点列表顺序一致
        int[][] vertexMap = {
                {NOT_CONNECT, 5, 7, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 2},
                {5, NOT_CONNECT, NOT_CONNECT, 9, NOT_CONNECT, NOT_CONNECT, 3},
                {7, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 8, NOT_CONNECT, NOT_CONNECT},
                {NOT_CONNECT, 9, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 4, NOT_CONNECT},
                {NOT_CONNECT, NOT_CONNECT, 8, NOT_CONNECT, NOT_CONNECT, 5, 4},
                {NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 4, 5, NOT_CONNECT, 6},
                {2, 3, NOT_CONNECT, NOT_CONNECT, 4, 6, NOT_CONNECT}};
        MyGraph myGraph = new MyGraph(vertexArr.length);
        myGraph.setVertexArr(vertexArr);
        myGraph.setVertexMap(vertexMap);
        // 从0索引开始进行连接
        prim(myGraph, 1);
    }

    private static void prim(MyGraph myGraph, int startIndex) {
        // 展示二维图
        myGraph.showVertexMap();
        // 初始化一个顶点访问情况的数组, 未访问为0, 访问为1
        int[] visitArr = new int[myGraph.getVertexCount()];
        // 默认当前节点已经访问
        visitArr[startIndex] = 1;
        // 定义最小长度
        int minValue = NOT_CONNECT;
        // 定义权值最小时, 已访问的顶点坐标和未访问的顶点坐标
        int hasVisited = -1;
        int notVisited = -1;
        // 顶点有N个, 则顶点间的变肯定存在N-1个, 所以一定存在N-1个边
        for (int i = 0; i < myGraph.getVertexCount() - 1; i++) {
            // 下面循环, 从已经被访问的顶点和还没有被访问的顶点中
            // 寻找出权值最小的路径作为下一步需要连接的路径
            for (int x = 0; x < myGraph.getVertexCount(); x++) {
                for (int y = 0; y < myGraph.getVertexCount(); y++) {
                    // x对应值为1表示该顶点已经被访问过
                    // y对应值为0, 表示该顶点还没有被访问过
                    if (visitArr[x] == 1 && visitArr[y] == 0) {
                        // 如果这两个顶点的连接值较小, 则进行记录
                        if (myGraph.getVertexMap()[x][y] < minValue) {
                            minValue = myGraph.getVertexMap()[x][y];
                            hasVisited = x;
                            notVisited = y;
                        }
                    }
                }
            }
            // 一条边处理完成后, 对这条边进行记录
            if (minValue != NOT_CONNECT) {
                // 标记未访问的顶点未已访问
                visitArr[notVisited] = 1;
                // 表示最小长度为初始长度
                minValue = NOT_CONNECT;
                // 打印顶点连接情况
                System.out.println("顶点 " + myGraph.getVertexArr()[hasVisited] + " 与顶点 "
                    + myGraph.getVertexArr()[notVisited] + " 连接, 权值为: "
                    + myGraph.getVertexMap()[hasVisited][notVisited]);
            }
        }
    }


    /**
     * 图对象
     */
    static class MyGraph {

        /**
         * 顶点
         */
        private char[] vertexArr;

        /**
         * 顶点权值图
         */
        private int[][] vertexMap;

        /**
         * 顶点数量
         */
        private int vertexCount;

        public MyGraph(int vertexCount) {
            this.vertexCount = vertexCount;
            this.vertexArr = new char[vertexCount];
            this.vertexMap = new int[vertexCount][vertexCount];
        }

        public void showVertexMap() {
            for (int[] curr : vertexMap) {
                System.out.println(Arrays.toString(curr));
            }
        }

        public char[] getVertexArr() {
            return vertexArr;
        }

        public void setVertexArr(char[] vertexArr) {
            if (vertexArr.length > this.vertexArr.length) {
                throw new IndexOutOfBoundsException("顶点数组越界");
            }
            this.vertexArr = vertexArr;
        }

        public int[][] getVertexMap() {
            return vertexMap;
        }

        public void setVertexMap(int[][] vertexMap) {
            if (vertexMap.length > this.vertexMap.length) {
                throw new IndexOutOfBoundsException("顶点连接线数组越界");
            }
            this.vertexMap = vertexMap;
        }

        public int getVertexCount() {
            return vertexCount;
        }
    }

}
