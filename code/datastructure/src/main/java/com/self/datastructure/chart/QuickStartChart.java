package com.self.datastructure.chart;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 图基本入门
 *
 * @author PJ_ZHANG
 * @create 2020-04-20 16:16
 **/
public class QuickStartChart {

    public static void main(String[] args) {
        MyChart myChart = createChart(8);
        System.out.println("广度优先遍历: ");
        myChart.bfs();
        myChart = createChart(8);
        System.out.println();
        System.out.println("深度优先遍历: ");
        myChart.dfs();
    }

    /**
     * 创建图
     * @param vertexNum 顶点数量
     * @return
     */
    private static MyChart createChart(int vertexNum) {
        MyChart myChart = new MyChart(vertexNum);
        for (int i = 1; i <= vertexNum; i++) {
            myChart.addVertex(String.valueOf(i));
        }
        myChart.addEdge(myChart.indexOf("1"), myChart.indexOf("2"), 1);
        myChart.addEdge(myChart.indexOf("1"), myChart.indexOf("3"), 1);
        myChart.addEdge(myChart.indexOf("2"), myChart.indexOf("4"), 1);
        myChart.addEdge(myChart.indexOf("2"), myChart.indexOf("5"), 1);
        myChart.addEdge(myChart.indexOf("3"), myChart.indexOf("6"), 1);
        myChart.addEdge(myChart.indexOf("3"), myChart.indexOf("7"), 1);
        myChart.addEdge(myChart.indexOf("4"), myChart.indexOf("8"), 1);
        myChart.addEdge(myChart.indexOf("5"), myChart.indexOf("8"), 1);
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

        /**
         * 是否已经被访问
         */
        private boolean[] isVisited;

        MyChart(int vertexNum) {
            this.vertexNum = vertexNum;
            lstVertex = new ArrayList<>(vertexNum);
            vertexPathArray = new int[vertexNum][vertexNum];
            isVisited = new boolean[vertexNum];
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

        /**
         * 深度优先遍历
         * 从第一个顶点开始进行遍历, 遍历过的顶点标记为已经遍历
         * 先获取遍历该顶点的下一个邻接顶点
         * 如果不存在, 则继续第二个未遍历顶点开始
         * 如果存在, 判断该邻接顶点是否已经遍历过
         * 如果没有遍历过, 则继续深度遍历该顶点(递归)
         * 如果已经遍历过, 则继续寻找下一个邻接顶点
         */
        void dfs() {
            for (int i = 0; i < lstVertex.size(); i++) {
                // 从第一个节点开始进行遍历
                // 先访问初始节点
                if (!isVisited[i]) {
                    dfs(i);
                }
            }
        }

        private void dfs(int index) {
            // 输出当前遍历的节点, 并标记为已访问
            System.out.print(lstVertex.get(index) + " -> ");
            isVisited[index] = true;
            // 获取它的第一个邻接节点进行访问
            int nextIndex = getFirstNeighbor(index);
            // 不等于-1说明存在下一个节点, 继续进行处理
            // 如果等于-1, 说明此次遍历结束, 交由主函数进行下一个节点遍历
            while (nextIndex != -1) {
                if (!isVisited[nextIndex]) {
                    // 如果没有被访问, 则继续深度循环遍历进行处理
                    dfs(nextIndex);
                }
                // 如果已经被访问了, 则查找nextIndex的下一个临界节点
                nextIndex = getNextNeighbor(index, nextIndex);
            }
        }

        /**
         * 获取下一个邻接节点
         * X, Y轴已知, 查找Y轴后面第一个存在权值的节点
         * @param index
         * @param nextIndex
         * @return
         */
        private int getNextNeighbor(int index, int nextIndex) {
            for (int i = nextIndex + 1; i < lstVertex.size(); i++) {
                if (vertexPathArray[index][i] > 0) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 获取第一个邻接节点
         * X轴已知, 获取Y轴上第一个存在权值的节点
         * @param index
         * @return
         */
        private int getFirstNeighbor(int index) {
            // 在该行坐标轴上进行遍历查找
            // 如果对应坐标的权值大于0, 说明这两个点是有关联关系的
            // 直接返回该点对应的下标索引
            for (int i = 0; i < lstVertex.size(); i++) {
                if (vertexPathArray[index][i] > 0) {
                    return i;
                }
            }
            // 如果没有找到, 直接返回-1
            return -1;
        }

        /**
         * 广度优先遍历
         */
        public void bfs() {
            for (int i = 0; i < lstVertex.size(); i++) {
                if (!isVisited[i]) {
                    bfs(i);
                }
            }
        }

        private void bfs(int index) {
            LinkedList<Integer> lstSearch = new LinkedList<>();
            // 添加节点到集合
            lstSearch.add(index);
            System.out.print(lstVertex.get(index) + " -> ");
            // 标识节点为已经遍历
            isVisited[index] = true;
            // 队列不为空, 进行顺序处理
            for (;CollectionUtils.isNotEmpty(lstSearch);) {
                // 获取队列第一个顶点
                Integer currIndex = lstSearch.removeFirst();
                // 获取顶点的邻接节点
                int nextIndex = getFirstNeighbor(currIndex);
                // 邻接节点存在
                for (;-1 != nextIndex;) {
                    // 如果邻接节点没有被访问过
                    if (!isVisited[nextIndex]) {
                        lstSearch.add(nextIndex);
                        isVisited[nextIndex] = true;
                        System.out.print(lstVertex.get(nextIndex) + " -> ");
                    }
                    // 获取下一个节点进行处理
                    nextIndex = getNextNeighbor(currIndex, nextIndex);
                }
                // 当前顶点处理完成后, 注意循环开始数据已经被移除
                // 如果集合不为空, 第二次开始时会继续移除下一个顶点, 并对该顶点进行处理
            }
        }

    }

}
