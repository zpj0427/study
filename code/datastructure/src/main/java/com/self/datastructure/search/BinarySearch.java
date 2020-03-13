package com.self.datastructure.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 15:37
 **/
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = {1, 12, 55, 55, 55, 78, 156, 765, 873, 987};
        System.out.println(binarySearchWitAll(array, 0, array.length - 1, 55));
    }

    public static List<Integer> binarySearchWitAll(int[] array, int left, int right, int target) {
        if (left > right) {
            return null;
        }
        int middle = (left + right) / 2;
        if (target > array[middle]) {
            return binarySearchWitAll(array, middle + 1, right, target);
        } else if (target < array[middle]) {
            return binarySearchWitAll(array, left, middle - 1, target);
        } else {
            List<Integer> lstIndex = new ArrayList<>(10);
            // 获取到目标数据
            lstIndex.add(middle);
            // 向右扫描所有数据
            for (int i = middle + 1; i < array.length; i++) {
                if (array[i] == target) {
                    lstIndex.add(i);
                } else {
                    break;
                }
            }
            // 向左扫描所有数据
            for (int i = middle - 1; i >= 0; i--) {
                if (array[i] == target) {
                    lstIndex.add(i);
                } else {
                    break;
                }
            }
            return lstIndex;
        }
    }

    /**
     * 二分查找获取到对应值索引
     * @param array 目标数组
     * @param left 左索引
     * @param right 右索引
     * @param target 目标值
     * @return
     */
    public static int binarySearch(int[] array, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        // 二分, 获取到中间索引
        int middle = (left + right) / 2;
        // 大于 向右查找
        if (target > array[middle]) {
            return binarySearch(array, middle + 1, right, target);
        } else if (target < array[middle]) { // 小于, 向左查找
            return binarySearch(array, left, middle - 1, target);
        } else {
            return middle;
        }
    }

}
