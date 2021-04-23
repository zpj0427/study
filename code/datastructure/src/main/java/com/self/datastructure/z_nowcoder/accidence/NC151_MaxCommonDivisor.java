package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC151: 最大公约数
 * 求出两个数的最大公约数，
 * 如果有一个自然数a能被自然数b整除，则称a为b的倍数，b为a的约数。
 * 几个自然数公有的约数，叫做这几个自然数的公约数。
 * 公约数中最大的一个公约数，称为这几个自然数的最大公约数。
 * <p>
 * * 先取两个数中的较小值, 以较小值为蓝本进行比较, 最大公约数, 再大没意义
 * * 如果较大值能被较小值整除, 则直接获得公约数为较小值
 * * 如果不能直接整除, 则从一半处开始, 倒序向1处比较
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 11:31
 **/
public class NC151_MaxCommonDivisor {

    public static void main(String[] args) {
        System.out.println(gcd(234, 567));
    }

    /**
     * 更相减损法 - 源自《九章算术》
     * 可半者半之，不可半者，副置分母、子之数，以少减多，更相减损，求其等也。以等数约之。
     *
     * 就是比较两个数互相减, 用大的减小的, 把结果赋值给大的, 等到两个数相等了, 就是最大公约数了
     * @param a
     * @param b
     * @return
     */
    public static int gcd_2(int a, int b) {
        if (a % b == 0)
            return a;
        if (b % a == 0)
            return b;
        // 进行神奇的九章算术
        for (; a != b ;) {
            if (a > b) {
                a = a - b;
            }
            if (b > a) {
                b = b - a;
            }
        }
        return a;
    }

    /**
     * 辗转相除法 - 欧几里得算法
     * 用两个数进行取模, 如果余数为0, 表示可以整除, 除数为最大公约数
     * 如果不能整除, 则以除数作为被除数, 以余数作为除数继续整除
     * 直接余数为0, 此时除数即为最大公约数
     * @param a
     * @param b
     * @return
     */
    public static int gcd_1(int a, int b) {
        int temp = 0;
        for (; (temp = a % b) != 0; ) {
            a = b;
            b = temp;
        }
        return b;
    }

    /**
     * 暴力计算法
     * 对较小数取半之后进行计算, 稍微减少点计算时间
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcd(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if (max % min == 0) {
            return min;
        }
        for (int i = min / 2; i > 0; i--) {
            if (min % i == 0 && max % i == 0) {
                return i;
            }
        }
        return 1;
    }

}
