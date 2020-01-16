package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 排序_插入排序
 * @author PJ_ZHANG
 * @create 2020-01-16 15:20
 **/
public class InsertionSort {

    public static void main(String[] args) {
        // int[] array = {10, 8, 3, 9, 2, 6, -1};
        // 10万个数测试, 2S
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        insertionSort(array);
        // System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 插入排序
    private static void insertionSort(int[] array) {
        // 固定第一个元素, 从后续元素开始于第一个元素比较
        for (int i = 1; i < array.length; i++) {
            int temp = array[i]; // 保存当前操作数据
            int currIndex = i;
            // 如果后续元素小于固定的最后一个元素, 则进行位置交换, 并以此类推知道顺序位置
            for (;currIndex > 0 && temp < array[currIndex - 1];) {
                array[currIndex] = array[currIndex - 1];
                currIndex--;
            }
            array[currIndex] = temp;
        }
    }

}
