package com.self.datastructure.chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 图基本入门
 *
 * @author PJ_ZHANG
 * @create 2020-04-20 16:16
 **/
public class QuickStartChart {

    public static void main(String[] args) {
        MyChart myChart = createChart(5);
    }

    /**
     * 创建图
     * @param vertexNum 顶点数量
     * @return
     */
    private static MyChart createChart(int vertexNum) {
        MyChart myChart = new MyChart(vertexNum);
        myChart.addAllVertex("A", "B", "C", "D", "E");
        myChart.addEdge(myChart.indexOf("A"), myChart.indexOf("B"), 1);
        myChart.addEdge(myChart.indexOf("A"), myChart.indexOf("C"), 1);
        myChart.addEdge(myChart.indexOf("B"), myChart.indexOf("C"), 1);
        myChart.addEdge(myChart.indexOf("B"), myChart.indexOf("D"), 1);
        myChart.addEdge(myChart.indexOf("B"), myChart.indexOf("E"), 1);
        myChart.showChart();
        return myChart;
    }

    /**
     * 自定义图类进行处理
     */
    static class MyChart {

        /**
         * 顶点数量
         */
        private int vertexNum;

        /**
         * 顶点列表
         */
        private List<String> lstVertex;

        /**
         * 顶点路径
         */
        private int[][] vertexPathArray;

        /**
         * 边数量
         */
        private int edgeNum;

        MyChart(int vertexNum) {
            this.vertexNum = vertexNum;
            lstVertex = new ArrayList<>(vertexNum);
            vertexPathArray = new int[vertexNum][vertexNum];
        }

        /**
         * 添加顶点, 此处不涉及扩容
         *
         * @param vertex 添加的顶点
         */
        void addVertex(String vertex) {
            if (vertexNum == lstVertex.size()) {
                throw new ArrayIndexOutOfBoundsException("数组已满");
            }
            lstVertex.add(vertex);
        }

        /**
         * 添加多个顶点
         *
         * @param vertexArr 顶点数组, 可变参数
         */
        void addAllVertex(String ... vertexArr) {
            if (vertexNum < lstVertex.size() + vertexArr.length) {
                throw new ArrayIndexOutOfBoundsException("数组已满");
            }
            lstVertex.addAll(Arrays.asList(vertexArr));
        }

        /**
         * 返回顶点所在的下标
         *
         * @param vertex 目标顶点
         * @return 返回下标
         */
        int indexOf(String vertex) {
            return lstVertex.indexOf(vertex);
        }

        /**
         * 添加边
         *
         * @param xIndex 横坐标
         * @param yIndex 纵坐标
         * @param weight 边的权重
         */
        void addEdge(int xIndex, int yIndex, int weight) {
            if (xIndex >= vertexNum || yIndex >= vertexNum) {
                throw new IndexOutOfBoundsException("索引越界");
            }
            vertexPathArray[xIndex][yIndex] = weight;
            vertexPathArray[yIndex][xIndex] = weight;
            edgeNum++;
        }

        /**
         * 获取边数量
         *
         * @return 返回边数量
         */
        int getEdgeNum() {
            return edgeNum;
        }

        /**
         * 展示图
         */
        void showChart() {
            for (int[] array : vertexPathArray) {
                System.out.println(Arrays.toString(array));
            }
        }

    }

}
