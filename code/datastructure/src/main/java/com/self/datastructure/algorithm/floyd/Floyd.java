package com.self.datastructure.algorithm.floyd;

import lombok.Getter;

import java.util.Arrays;

/**
 * 弗洛伊德算法
 * * 弗洛伊德算法其实就是三层for循环, 通过间接连接比较直接连接是否距离最短
 * * 三层for循环都是以全部顶点进行循环
 * * 第一层循环作为中间顶点循环
 * * 第二层循环作为出发顶点循环
 * * 第三层循环作为结束顶点循环
 * * 如果通过中间顶点过度后的三点距离, 小于出发顶点到结束顶点的距离,
 * * 则对距离表进行替换, 并更新前驱关系表的前驱顶点为当前遍历的中间顶点
 * * 第一层循环的全部顶点作为中间顶点遍历完成后, 则算法完成
 * @author pj_zhang
 * @create 2020-07-12 22:05
 **/
public class Floyd {

    private static final int NON = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点列表
        char[] lstVertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 顶点图, 该图中各自访问为0点
        int[][] vertexMap = {
                {0, 5, 7, NON, NON, NON, 2},
                {5, 0, NON, 9, NON, NON, 3},
                {7, NON, 0, NON, 8, NON, NON},
                {NON, 9, NON, 0, NON, 4, NON},
                {NON, NON, 8, NON, 0, 5, 4},
                {NON, NON, NON, 4, 5, 0, 6},
                {2, 3, NON, NON, 4, 6, 0}
        };
        MyGraph myGraph = new MyGraph(lstVertex, vertexMap);
        // 进行弗洛伊德计算
        floyd(myGraph);
        // 处理完成, 进行结果输出
        int[][] result = myGraph.getVertexMap();
        System.out.println("距离结果输出: ");
        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
        char[][] preVertexArr = myGraph.getPreVertexArr();
        System.out.println("前驱顶点结果输出: ");
        System.out.println("    A  B  C  D  E  F  G ");
        for (int i = 0; i < preVertexArr.length; i++) {
            System.out.print(lstVertex[i] + ": ");
            System.out.println(Arrays.toString(preVertexArr[i]));
        }
    }

    /**
     * 弗洛伊德计算
     * @param myGraph
     */
    private static void floyd(MyGraph myGraph) {
        int[][] vertexMap = myGraph.getVertexMap();
        char[][] preVertexArr = myGraph.getPreVertexArr();
        char[] lstVertex = myGraph.getLstVertex();
        // 首先遍历中间顶点
        for (int middleIndex = 0; middleIndex < lstVertex.length; middleIndex++) {
            int[] middleDis = vertexMap[middleIndex];
            // 然后遍历起始顶点
            for (int startIndex = 0; startIndex < lstVertex.length; startIndex++) {
                // 如果顶点不连通, 直接进行下一轮循环
                if (NON == middleDis[startIndex]) {
                    continue;
                }
                // 最后遍历结束顶点
                for (int endIndex = 0; endIndex < lstVertex.length; endIndex++) {
                    // 顶点不连通, 直接进行下一轮循环
                    if (NON == middleDis[endIndex]) {
                        continue;
                    }
                    // 顶点连通的情况下进行后续处理
                    // 获取中间顶点到初始顶点的值
                    int startMidDis = vertexMap[middleIndex][startIndex];
                    // 获取中间顶点到到结束顶点的值
                    int endMidDis = vertexMap[middleIndex][endIndex];
                    // 获取初始顶点到结束顶点的值
                    int startEndDis = vertexMap[startIndex][endIndex];
                    // 如果通过中间顶点连接后, 距离小于直接连接的距离(未连接为极值)
                    // 则进行顶点值替换, 并将前驱顶点数组对应位置的前驱顶点改为中间顶点
                    if ((startMidDis + endMidDis) < startEndDis) {
                        vertexMap[startIndex][endIndex] = startMidDis + endMidDis;
                        vertexMap[endIndex][startIndex] = startMidDis + endMidDis;
                        preVertexArr[startIndex][endIndex] = lstVertex[middleIndex];
                        preVertexArr[endIndex][startIndex] = lstVertex[middleIndex];
                    }
                }
            }
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

        /**
         * 前驱顶点数组
         */
        private char[][] preVertexArr;

        public MyGraph(char[] lstVertex, int[][] vertexMap) {
            this.vertexCount = lstVertex.length;
            this.lstVertex = lstVertex;
            this.vertexMap = vertexMap;
            // 初始化前驱顶点数组
            // 将每一组顶点的前驱顶点先设置为自身
            preVertexArr = new char[this.vertexCount][this.vertexCount];
            for (int i = 0; i < preVertexArr.length; i++) {
                char[] currData = this.preVertexArr[i];
                for (int j = 0; j < currData.length; j++) {
                    preVertexArr[i][j] = lstVertex[i];
                }
            }
        }

    }

}
