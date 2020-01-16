package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 选择排序
 * @author PJ_ZHANG
 * @create 2020-01-15 14:36
 **/
public class SelectSort {

    public static void main(String[] args) {
        // int[] array = {10, 8, 3, 9, 2, 6, -1};
        // 10万个数测试
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        selectSort(array);
        // System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 选择排序处理
    // 从一个数开始, 依次与后面所有的数比较, 并将当前数替换为最小数, 并在头部固定
    // 全部处理完成后, 会形成一个顺序数组
    private static void selectSort(int[] array) {
        // 从第一个元素开始比较, 比较到倒数第二个元素, 最后一个元素无需自比较
        for (int i = 0; i < array.length - 1; i++) {
            int temp = array[i]; // 默认取当前值为最小值
            int minIndex = i; // 最小值索引
            // 内循环从外循环的后一个元素开始算起, 进行元素比较, 一直比较到最后一个元素
            for (int j = i + 1; j < array.length; j++) {
                // 比较获取到最小值
                if (temp > array[j]) {
                    temp = array[j]; // 最小值
                    minIndex = j; // 最小值索引
                }
            }
            // 循环完成后, 如果存在小于该元素的索引值, 则进行替换
            if (minIndex != i) {
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }
    }

}
