package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 排序_冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        // int[] array = {9, -1, 4, -2, 3, -5, 8};
        // int[] array = {1, 2, 3, 4, 6, 5, 7, 8};
        // 10万个数测试
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        bubbleSort(array);
        // System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 冒泡排序
    private static void bubbleSort(int[] array) {
        // 外循环表示跑的趟数, 总趟数为 (长度 - 1)
        for (int i = 0; i < array.length - 1; i++) {
            // 定义标志位, 如果已经有序排列, 不再执行后续逻辑
            boolean flag = true;
            // 内循环进行冒泡比较
            // 内循环从0开始, 循环 (array.length - i - 1) 次
            // 外循环每跑一趟, 固定一个最大数到右侧, 内循环比较不会再次比较最大数
            // 内循环比较实用当前数与后一个数进行比较, 所以需要额外减1, 保证数组边界
            // 用当前数和后一个数进行比较, 如果当前数大于后一个数, 则进行交换
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // 存在变更, 设置标志位为false
                    flag = false;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            // 已经顺序化, 直接退出
            if (flag) {
                break;
            }
        }
    }

}
