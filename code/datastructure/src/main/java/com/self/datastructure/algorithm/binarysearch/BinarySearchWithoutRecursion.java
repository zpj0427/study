package com.self.datastructure.algorithm.binarysearch;

/**
 * 非递归方式实现二分查找
 * @author pj_zhang
 * @create 2020-06-26 16:13
 **/
public class BinarySearchWithoutRecursion {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(binarySearchWithoutRecursion(arr, 10));
    }

    public static int binarySearchWithoutRecursion (int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        // 只要left的位置不超过right, 说明有效, 可以继续查找
        for (;left <= right;) {
            int middle = (left + right) / 2;
            // 找到了, 直接返回
            if (arr[middle] == target) {
                return middle;
            } else if (arr[middle] < target) {
                // 中值小于目标值, 向右找
                left = middle + 1;
            } else if (arr[middle] > target) {
                // 中值大于目标值, 向左找
                right = middle - 1;
            }
        }
        // 未找到, 返回-1
        return -1;
    }

}
