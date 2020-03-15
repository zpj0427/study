package com.self.datastructure.search;


/**
 * 插入查找
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 15:37
 **/
public class InsertValueSearch {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15};
        System.out.println(insertValueSearch(array, 0, array.length - 1, 12));
    }

    /**
     * 插入查找获取到对应值索引
     * @param array 目标数组
     * @param left 左索引
     * @param right 右索引
     * @param target 目标值
     * @return
     */
    public static int insertValueSearch(int[] array, int left, int right, int target) {
        if (left > right || target < array[left] || target > array[right]) {
            return -1;
        }
        // 插入查找, 获取到自适应middle索引
        int middle = left + (right - left) * (target - array[left]) / (array[right] - array[left]);
        System.out.println("middle: " + middle);
        // 大于 向右查找
        if (target > array[middle]) {
            return insertValueSearch(array, middle + 1, right, target);
        } else if (target < array[middle]) { // 小于, 向左查找
            return insertValueSearch(array, left, middle - 1, target);
        } else {
            return middle;
        }
    }

}
