package com.self.datastructure.z_leetcode.easy;

/**
 * @author PJ_ZHANG
 * @create 2021-04-28 16:05
 **/
public class Q633_QuadraticSum {

    public static void main(String[] args) {
        System.out.println(judgeSquareSum(2147482647));
    }

    public static boolean judgeSquareSum(int c) {
        long data = c;
        int sqrt = (int) Math.sqrt(data);
        for (long i = sqrt; i >= 0; i--) {
            for (long j = 0; j <= sqrt && j < i; j++) {
                if (i * i + j * j == data) {
                    return true;
                }
                if (i * i + j * j > data) {
                    break;
                }
            }
        }
        return false;
    }

}
