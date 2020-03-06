package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-06 15:00
 **/
public class QuickSort {

    public static void main(String[] args) {
        int[] array = {4, 6, 7, 8, 4, 3, 5, 8, 9};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    // 快速排序
    // 先从数组中随机取一个参考值,
    // 分别从数组两边(left, right)开始取数据进行比较
    // 如果left取到的数据大于基准数据, right取到的数据小于基准数据, 则进行交换
    // 如果left, right相等, 则重新赋值该索引数据为基本数据
    // 并递归左右两边进行处理
    private static void quickSort(int[] array, int left, int right) {
        int l = left;
        int r = right;
        // 取基准数据
        int baseData = array[l];
        // 左边索引小于右边索引就继续比较
        while (l < r) {
            // 左边数大于基准数
            while (array[l] < baseData) {
                l++;
            }
            // 右边数小于基准数
            while (array[r] > baseData) {
                r--;
            }
            // 超出界限, 则退出
            if (l >= r) {
                break;
            }
            // 如果两边存在需要交换的数据, 则进行交换
            int temp = array[l];
            array[l] = array[r];
            array[r] = temp;
            if (array[l] == baseData) {
                r--;
            }
            if (array[r] == baseData) {
                l++;
            }

        }

        if (l == r) {
            l++;
            r--;
        }
        // 继续递归两边处理
        if (left < r) {
            quickSort(array, left, r);
        }
        if (l < right) {
            quickSort(array, l, right);
        }
    }

}
