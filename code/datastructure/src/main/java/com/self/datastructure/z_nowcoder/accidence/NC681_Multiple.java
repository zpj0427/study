package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC681: 牛牛找数
 * 牛牛有两个数a和b，他想找到一个大于a且为b的倍数的最小整数，
 * 只不过他算数没学好，不知道该怎么做，现在他想请你帮忙。
 * 给定两个数a和b，返回大于a且为b的倍数的最小整数。
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 15:38
 **/
public class NC681_Multiple {

    public static void main(String[] args) {
        System.out.println(findNumber_1(3, 2));
    }

    /**
     * 先找出 a 对 b 的倍数
     * 以这个倍数为基础进行计算,
     * 如果 b 与这个倍数的积大于 a, 则直接返回
     * 如果 b 与这个倍数的积小于等于 a, 则对这个倍数加1, 肯定大于 a
     * 注意该题有时间复杂度要求
     * @param a
     * @param b
     * @return
     */
    public static int findNumber_1 (int a, int b) {
        int data = a / b;
        if (b * data <= a) {
            return b * (data + 1);
        }
        return b * data;
    }

    /**
     * 时间复杂度超出
     * @param a
     * @param b
     * @return
     */
    public static int findNumber (int a, int b) {
        for (int i = a + 1; i <= Integer.MAX_VALUE; i++) {
            if (i % b == 0)
                return i;
        }
        return -1;
    }

}
