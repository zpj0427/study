package com.self.datastructure.algorithm.dijkstra;

import lombok.Getter;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法_D算法
 * * 先通过连通图和出发顶点构建一个访问过的顶点对象
 * * 该对象中包括: 标记顶点访问情况的数组, 顶点到出发顶点的距离数组, 顶点的前驱顶点数组
 * * 从出发顶点开始, 标记该顶点已经访问, 并计算各个顶点到该顶点的距离, 其中不连通的用一个极值表示
 * * 之后继续依照广度搜索优先的算法原则, 依次向外扩展
 * * 从与出发顶点连接的顶点的中, 依次找到距离最小的未访问顶点进行访问并刷新访问状态, 距离和前驱节点
 * * 直至所有顶点遍历完成后, 距离数组中的数据即为最短距离数据
 * @author pj_zhang
 * @create 2020-07-12 12:40
 **/
public class Dijkstra {

    private static final int NON = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点列表
        char[] lstVertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 顶点图
        int[][] vertexMap = {
                {NON, 5, 7, NON, NON, NON, 2},
                {5, NON, NON, 9, NON, NON, 3},
                {7, NON, NON, NON, 8, NON, NON},
                {NON, 9, NON, NON, NON, 4, NON},
                {NON, NON, 8, NON, NON, 5, 4},
                {NON, NON, NON, 4, 5, NON, 6},
                {2, 3, NON, NON, 4, 6, NON}
        };
        MyGraph myGraph = new MyGraph(lstVertex, vertexMap);
        dijkstra(myGraph);
    }

    /**
     * 开始迪杰斯塔拉算法
     * @param myGraph 连接图表
     */
    private static void dijkstra(MyGraph myGraph) {
        // index在此处作为出发顶点
        int index = myGraph.getVertexCount() - 1;
        // 初始化已经连接顶点类
        VisitedVertex visitedVertex = new VisitedVertex(myGraph.getVertexCount(), index);
        // 先从出发顶点, 修改连接顶点的距离和前置顶点
        updateVisitedVertex(visitedVertex, myGraph, index);
        // 出发顶点访问完成后, 还存在间接关联节点没有关联到
        // 同时直接关联顶点的距离不一定比间接关联的距离小
        // 所以此处参考图的广度遍历, 取还没有被访问过的距出发节点距离最小的顶点作为访问顶点继续处理
        // 因为已经访问了一个顶点, 所以此处少访问一个
        for (int i = 0; i < myGraph.getVertexCount() - 1; i++) {
            // index在此处重新赋值, 作为访问顶点
            index = getNextIndex(visitedVertex);
            // 从访问顶点继续修改顶点距离和前置顶点
            updateVisitedVertex(visitedVertex, myGraph, index);
        }
        System.out.println("最终结果: " + Arrays.toString(visitedVertex.getVertexDis()));
    }

    /**
     * 获取下一个未被访问的顶点
     * 注意此顶点一定要是距出发顶点距离最小的
     * @param visitedVertex
     * @return
     */
    private static int getNextIndex(VisitedVertex visitedVertex) {
        int[] visitedArr = visitedVertex.getVisitedArr();
        // 取最小距离为未连通状态下的最大距离
        int minDis = NON;
        int index = -1;
        for (int i = 0; i < visitedArr.length; i++) {
            if (visitedArr[i] == 0 && visitedVertex.getVertexDis()[i] < minDis) {
                index = i;
                minDis = visitedVertex.getVertexDis()[i];
            }
        }
        return index;
    }

    /**
     * 修改当前顶点下, 从出发顶点到各个顶点的距离
     * * 此处主要考虑存在顶点与出发顶点间接关联, 比如通过该顶点关联
     * * 则间接顶点到出发顶点的距离 = 当前顶点到出发顶点的距离 + 间接顶点到当前顶点的距离
     * @param visitedVertex 访问顶点记录对象
     * @param myGraph 顶点下标关联图
     * @param index 当前顶点下标
     */
    private static void updateVisitedVertex(VisitedVertex visitedVertex, MyGraph myGraph, int index) {
        // 标记当前访问顶点为已访问
        visitedVertex.getVisitedArr()[index] = 1;
        // 获取访问图
        int[][] vertexMap = myGraph.getVertexMap();
        // 当前顶点到各个顶点的距离图
        int[] distanceArr = vertexMap[index];
        for (int i = 0; i < distanceArr.length; i++) {
            // 遍历获取该顶点到每一个关联顶点的距离
            // 不为NON, 说明存在关联
            if (distanceArr[i] != NON) {
                // 如果当前顶点没有被访问
                // 并且当前顶点到访问顶点的距离 + 访问顶点到出发顶点的距离小于现有的当前顶点到访问的距离
                // 则进行替换, 并标记为前驱节点
                if (visitedVertex.getVisitedArr()[i] == 0
                        && (myGraph.getVertexMap()[index][i] + visitedVertex.getVertexDis()[index] < visitedVertex.getVertexDis()[i])) {
                    visitedVertex.getVertexDis()[i] = (myGraph.getVertexMap()[index][i] + visitedVertex.getVertexDis()[index]);
                    visitedVertex.getPreVertexArr()[i] = index;
                }
            }
        }
    }

    /**
     * 已访问顶点记录对象
     */
    @Getter
    static class VisitedVertex {

        /**
         * 已访问的顶点数组
         * 0表示未访问, 1表示已访问
         */
        private int[] visitedArr;

        /**
         * 顶点距离
         * 表示当前顶点到目标顶点的距离
         * 遍历过程中体现的是当前距离
         * 全部处理完成即最终结果
         */
        private int[] vertexDis;

        /**
         * 顶点的前驱顶点
         * 最后最后绘制连接图, 表示从出发顶点访问到当前顶点, 需要经过的路径
         */
        private int[] preVertexArr;

        /**
         * 初始化已连接顶点类
         * @param vertexCount 顶点数量
         * @param startIndex 出发顶点索引
         */
        public VisitedVertex(int vertexCount, int startIndex) {
            // 初始化已访问顶点数组
            this.visitedArr = new int[vertexCount];
            // 初始化距离数组
            this.vertexDis = new int[vertexCount];
            // 初始化距离为未连通距离
            Arrays.fill(vertexDis, NON);
            // 初始化出发节点距离为0
            this.vertexDis[startIndex] = 0;
            // 初始化前驱节点数组
            this.preVertexArr = new int[vertexCount];
        }

    }

    /**
     * 图表类
     */
    @Getter
    static class MyGraph {

        /**
         * 顶点数量
         */
        private int vertexCount;

        /**
         * 顶点列表
         */
        private char[] lstVertex;

        /**
         * 顶点连接图
         */
        private int[][] vertexMap;

        public MyGraph(char[] lstVertex, int[][] vertexMap) {
            this.vertexCount = lstVertex.length;
            this.lstVertex = lstVertex;
            this.vertexMap = vertexMap;
        }

    }

}
