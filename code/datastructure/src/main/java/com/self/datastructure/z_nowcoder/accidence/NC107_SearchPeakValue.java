package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC107: 寻找峰值
 * 山峰元素是指其值大于或等于左右相邻值的元素。
 * 给定一个输入数组nums，任意两个相邻元素值不相等，数组可能包含多个山峰。
 * 找到索引最大的那个山峰元素并返回其索引。
 * 输入: [2,4,1,2,7,8,4]
 * 输出: 5
 *
 * ***** 注意寻找最大的索引, 不是最大值的索引
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 10:14
 **/
public class NC107_SearchPeakValue {

    public static void main(String[] args) {
        System.out.println(slove_1(new int[]{1, 2, 3, 4, 3, 4, 9}));
    }

    /**
     * 第二种题解
     * 从数组的最后一位开始, 用后一位与前一位进行比较
     * 如果前一位小于该值, 则说明该位置是数组的做大索引峰值
     * 如果前一位大于该值, 会继续往前追 (i - 1 > i -> i > i + 1)
     * 追到前一位小于该值, 则说明该值峰值(i - 1 < i)
     * 此时前后数位比较就是(i - 1 < i > i + 1)
     * 因为是从后往前遍历, 所以该索引必然为最大索引
     * 遍历到最开始还没有找到峰值, 则峰值必然为0索引, 说明数组是一个倒序数组
     *
     * @param a
     * @return
     */
    public static int slove_1(int[] a) {
        if (a.length == 1) {
            return 0;
        }
        for (int i = a.length - 1; i > 0; i--) {
            if (a[i] > a[i - 1]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 暴力解题
     * * 注意题目要求: 取最大索引的峰值, 不是取峰值的最大值
     * * 从1索引出开始, 对每一个数与两边的数进行比较, 均大于说明为峰值
     * * 对该峰值的索引进行记录
     * * 执行完毕后, 对最后一个数进行与前一位的比较, 保证所有数据遍历完成
     * * 最终返回最大索引
     * ***** 这个办法太笨
     *
     * @param a
     * @return
     */
    public static int solve(int[] a) {
        // 对1和2先进行特殊处理
        if (a.length == 1) {
            return 0;
        }
        if (a.length == 2) {
            return a[0] > a[1] ? 0 : 1;
        }
        int maxIndex = -1;
        for (int i = 1; i < a.length - 1; i++) {
            // 找到峰值
            if (a[i] > a[i - 1] && a[i] > a[i + 1]) {
                maxIndex = i;
            }
        }
        // 最后对末尾进行比较
        return a[a.length - 1] > a[a.length - 2] ? a.length - 1 : maxIndex;
    }

}
