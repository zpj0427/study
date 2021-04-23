package com.self.datastructure.z_nowcoder.accidence;

import java.util.Arrays;

/**
 * NC110: 旋转数组
 * 一个数组A中存有N（N > 0）个整数，在不允许使用另外数组的前提下，将每个整数循环向右移M（M>=0）个位置，
 * 即将A中的数据由（A0 A1 ……AN-1 ）变换为（AN-M …… AN-1 A0 A1 ……AN-M-1 ）
 * （最后M个数循环移至最前面的M个位置）。如果需要考虑程序移动数据的次数尽量少，要如何设计移动的方法？
 *
 * * 对数组进行三次反转
 * * 如数组 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 向右移动五位变成{5, 6, 7, 8, 9, 0, 1, 2, 3, 4}
 * * 先对全数组进行第一次反转: {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
 * * 再对 M=5 前的位置进行第二次反转: {5, 6, 7, 8, 9, 4, 3, 2, 1, 0}
 * * 最后对 M=5 后的位置进行第三次反转: {5, 6, 7, 8, 9, 0, 1, 2, 3, 4}
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 10:52
 **/
public class NC110_RevolveArray {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solve(10, 3, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})));
    }

    public static int[] solve(int n, int m, int[] a) {
        // 对 m 取模, 存在 m > n 的情况, m = n 说明数组不动
        m = m % n;
        // 第一次反转, 全数据反转
        reverse(a, 0, n);
        // 第二次反转, 前半部分反转
        reverse(a, 0, m);
        // 第三次反转, 后半部分反转
        reverse(a, m, n);
        return a;
    }

    public static void reverse(int[] a, int startIndex, int endIndex) {
        for (int i = startIndex; i < (startIndex + endIndex) / 2; i++) {
            int temp = a[i];
            a[i] = a[startIndex + endIndex - 1 - i];
            a[startIndex + endIndex - 1 - i] = temp;
        }
    }

}
