package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC65: 斐波拉契数列
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0，第1项是1）。39n≤39
 * 输入: 4
 * 输出: 3
 *
 * @author PJ_ZHANG
 * @create 2021-04-22 10:42
 **/
public class NC65_Fibonacci {

    public static void main(String[] args) {
        System.out.println(fibonacci_1(7));
        System.out.println(fibonacci_2(7));
    }

    /**
     * 斐波拉契数列: 第一位为0, 第二位为1, 后续每一位为前两位之和的数组
     * [0, 1, 1, 2, 3, 5, 8, 13, 21, 34...]
     * 按题目要求是取 n 索引出的数据: arr[4] = 3
     * * 先构建长度为 n + 1 的数组, 并按数列规则填充数列
     * * 对0位填0, 第1位填1, 后续每一位为前两位之和
     * * 数组构建完成后, 直接取第 n 位 索引的数据即可
     *
     * @param n
     * @return
     */
    public static int fibonacci_1(int n) {
        if (n == 0 || n == 1)
            return n;
        int[] arr = new int[n + 1];
        arr[1] = 1;
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }

    /**
     * 上一种方法空间复杂度太高, 可以不创建数组, 直接计算完成
     *
     * @param n
     * @return
     */
    public static int fibonacci_2(int n) {
        if (n == 0 || n == 1)
            return n;
        int num1 = 0, num2 = 1;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = num1 + num2;
            num1 = num2;
            num2 = result;
        }
        return result;
    }

}
