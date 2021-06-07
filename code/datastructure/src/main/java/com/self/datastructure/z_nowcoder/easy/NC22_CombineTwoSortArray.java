package com.self.datastructure.z_nowcoder.easy;

import java.util.Arrays;

/**
 * NC22: 合并两个有序数组
 * 给出两个有序的整数数组 A 和 B ，请将数组 B 合并到数组 A 中，变成一个有序的数组
 * 可以假设 A 数组有足够的空间存放 B 数组的元素， A 和 B 中初始的元素数目分别为 m 和 n
 *
 * @author PJ_ZHANG
 * @create 2021-04-25 18:55
 **/
public class NC22_CombineTwoSortArray {

    public static void main(String[] args) {
        int[] arrA = new int[]{4, 5, 6, 7, 0, 0, 0};
        int[] arrB = new int[]{1, 2, 3};
        merge_1(arrA, 4, arrB, 3);
        System.out.println(Arrays.toString(arrA));
    }

    /**
     * 第一种方式通过一个一个数据插入, 时间复杂度为O(n2)
     * * 前提条件: A数组有足够的空间存储B数组
     * * A + B数组的数组长度为 m + n
     * * 此时可以将A数组本身作为一个新数组, 该数组长度为m + n
     * * 将A数组的初始元素和B数组从后往前依次加入 (m + n) 的数组长度中
     * * 加入完成后, 数组完成
     *
     * @param A
     * @param m
     * @param B
     * @param n
     */
    public static void merge_1(int A[], int m, int B[], int n) {
        int i = m - 1;
        int j = n - 1;
        int maxIndex = m + n - 1;
        // 第一次循环, 将A, B两个数组元素依次往A数组中添加,
        // 从后往前遍历, 从后往前添加
        for (; i >= 0 && j >= 0; ) {
            A[maxIndex--] = A[i] > B[j] ? A[i--] : B[j--];
        }
        // 从上一步跳出循环后
        // 可能两个数组全部添加完成, 此时已经完成
        // 也可能只有A数组添加完成, B数组还有剩余, 则继续处理B数组
        // 也可能只有B数组添加完成, A数组还有剩余, 因为本就是在A数组上操作, 所以无需再操作
        for (; j >= 0; ) {
            A[maxIndex--] = B[j--];
        }
    }

    /**
     * 比较简单粗暴的方式
     * * 从后往前遍历 B 数组, 将B数组元素插入到A数组中
     * * 在插入A数组的时候, 从后往前遍历与B数组元素进行比较
     * * 如果大于A元素 > B元素, 则将A元素置于 (索引 + 1) 的位置处
     * * 如果 A 元素 <= B元素, 将 B元素置于 (索引 + 1) 的位置处即可
     *
     * @param A
     * @param m
     * @param B
     * @param n
     */
    public static void merge(int A[], int m, int B[], int n) {
        if (n == 0 || n + m > A.length) {
            return;
        }
        // 从 B 数组开始遍历, 倒序遍历
        a:
        for (int i = n - 1; i >= 0; i--) {
            // 与 A 数组进行比较, 倒序处理
            for (int j = m - 1; j >= 0; j--) {
                // 如果B数组元素值小于A数组元素值
                // 则将A数组的元素值后移一位
                if (B[i] < A[j]) {
                    A[j + 1] = A[j];
                } else {
                    // 否则将B数组的元素值插入A数组里面,
                    // 此时A数组实际长度加1
                    // 继续进行下一个B数组元素循环
                    A[j + 1] = B[i];
                    m++;
                    continue a;
                }
            }
            // 如果全部与A数组元素比较完成后, 都没有插入到A数组中
            // 说明B数组该元素最小, 添加到O位置即可
            A[0] = B[i];
            m++;
        }
    }

}
