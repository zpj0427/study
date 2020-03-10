package com.self.datastructure.sort;

import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * 希尔排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-05 14:26
 **/
public class ShellSort {

    public static void main(String[] args) {
//        int[] array = {4, 6, 7, 8, 1, 3, 5, 2, 9, 0};
        // 10万个数测试,  96ms
        // 100万, 323ms
        // 1000万, 3733ms
        int[] array = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        sortAndMove(array);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 希尔排序_交换法排序
    // * 对数组元素进行两两分组, 分为 (N / 2) 组, 且一组的两个数据间步长为 (N / 2)
    // * 对各组数据进行插入排序, 使各组数据有序
    // * 对上一步有序的数据, 继续分为 (N / 2 / 2) 组, 每组数据步长为 (N / 2 / 2)
    // * 继续对该组数进行插入排序, 使各组数据有序
    // * 以此类推, 直到 (N / 2 / ... / 2 = 1) 时, 不能继续分组, 最后进行插入排序, 使有序
    // * 10W数据 -> 14330ms
    public static void sortAndChange(int[] array) {
        // 初始化分组
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            // 从每一组的第二个数据开始进行比较
            for (int i = gap; i < array.length; i++) {
                // 依次与前面的数据进行比较
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如果前一个数量大于后一个数量
                    // 则依次循环向前替换
                    if (array[j] > array[j + gap]) {
                        int temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    }
                }
            }
        }
    }

    // 希尔排序_移位法排序
    // 10W数据: 40ms
    public static void sortAndMove(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int j = i; // 从当前索引开始处理
                int temp = array[i]; // 存储当前索引位置值进行比较
                for (;j - gap >= 0 && temp < array[j - gap]; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

}
