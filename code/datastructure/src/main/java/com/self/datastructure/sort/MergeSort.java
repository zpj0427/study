package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-10 15:11
 **/
public class MergeSort {

    public static void main(String[] args) {
        //        int[] array = {6, 8, 9, 1, 4, 3, 5, 6, 8};
        // 10万个数测试, 29ms
        // 100万测试, 270ms
        // 1000万测试, 2480ms
        int[] array = new int[10000000];
        int[] tempArray = new int[array.length];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        mergeSort(array, 0, array.length - 1, tempArray);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 归并排序
     * @param array 原数组
     * @param left 左侧索引
     * @param right 右侧索引
     * @param tempArray 临时数组
     */
    private static void mergeSort(int[] array, int left, int right, int[] tempArray) {
        int middle = (left + right) / 2;
        // 先拆分, 拆分到单个数据
        if (left < right) {
            // 向左拆分
            mergeSort(array, left, middle, tempArray);
            // 向右拆分
            mergeSort(array, middle + 1, right, tempArray);
            // 再进行合并
            merge(array, left, right, middle, tempArray);
        }
    }

    /**
     * 合并数据
     * @param array 原始数组
     * @param left 左侧索引
     * @param right 右侧索引
     * @param middle 中间位置索引, 即要合并数据的中间索引
     * @param tempArray 临时数组
     */
    private static void merge(int[] array, int left, int right, int middle, int[] tempArray) {
        int tempIndex = 0;
        int leftIndex = left;
        int rightIndex = middle + 1;
        // 先对两部分数据重叠部分比较入组排序
        // 直到一边的数据处理完成即止, 到下一步继续处理
        while (leftIndex <= middle && rightIndex <= right) {
            if (array[leftIndex] > array[rightIndex]) {
                tempArray[tempIndex++] = array[rightIndex++];
            } else {
                tempArray[tempIndex++] = array[leftIndex++];
            }
        }

        // 分别对两部分数据多余部分直接入组排序
        while (leftIndex <= middle) {
            tempArray[tempIndex++] = array[leftIndex++];
        }
        while (rightIndex <= right) {
            tempArray[tempIndex++] = array[rightIndex++];
        }
        // 复制临时组数据到原数组
        tempIndex = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            array[tempLeft++] = tempArray[tempIndex++];
        }
    }

}
