package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC657: 最小差值
 * 给你一个数组aa，请你求出数组a中任意两个元素间差的绝对值的最小值。
 * 输入: [1,2,4]
 * 输出: 1 (|1 - 2| = 1)
 * 输入: [1,3,1]
 * 输出: 0 (1 - 1 = 0)
 * @author PJ_ZHANG
 * @create 2021-04-23 15:02
 **/
public class NC657_MinDValue {

    public static void main(String[] args) {
        System.out.println(minDifference(new int[] {-2147483648,0,2147483647}));
    }

    /**
     * 取最小差值的绝对值, 数组任意两个元素之间
     * * 从第一个元素开始, 依次与后续元素进行比较, 取最小差值进行一次记录替换
     * * 如果差值为0, 表示已经最小, 直接退出即可
     *
     * @param a
     * @return
     */
    public static int minDifference (int[] a) {
        // 记录最小差值
        long minData = Integer.MAX_VALUE;
        // 外层循环遍历到倒数第二个数
        for (int i = 0; i < a.length - 1; i++) {
            // 内层循环从完成循环的后一个数开始, 到最后一个数结束
            for (int j = i + 1; j < a.length; j++) {
                // 此处考虑int溢出, 用long暂时处理
                long tempData = Math.abs((long)a[i] - (long)a[j]);
                tempData = tempData < 0 ? Integer.MAX_VALUE : tempData;
                if (tempData < minData) {
                    if (0 == tempData) {
                        return 0;
                    }
                    minData = tempData;
                }
            }
        }
        // 处理完成后, 如果值大于int最大值, 以最大值返回, 或者直接返回该值即可
        return minData > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) minData;
    }

}
