package com.self.datastructure.z_nowcoder.easy;

import java.util.Map;

/**
 * NC19: 子树最大累加和问题
 * 给定一个数组arr，返回子数组的最大累加和
 * 例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子数组中，[3, 5, -2, 6]可以累加出最大的和12，所以返回12.
 * 要求: 时间复杂度为O(n), 空间复杂度为O(1)
 * <p>
 * 此题最大的难点在于时间复杂度和空间复杂度限制
 * * O(n): 只能有一层循环
 * * O(1): 不能开另外的数组
 * <p>
 * 解题思路:
 * * 首先定义一个初始化的最大值, 默认取 arr[0] 作为最大值
 * * 从1索引开始, 遍历数组数据, 并对数据进行累加,
 * * 用本次累加结果与当前遍历元素进行比较
 * ** 如果大于, 本次累加结果为下次累加的基础值
 * ** 如果小于, 以当前遍历元素为下次累加的基础值
 * * 用本次比较记录的值与记录的最大值进行比较, 将较大值记为最大值
 *
 * * 累加值存储, 可直接存储在遍历的当前位, 为下一次遍历提供支持, 不需要单独定义
 *
 * @author PJ_ZHANG
 * @create 2021-04-25 15:51
 **/
public class NC19_MaxSubArraySum {

    public static void main(String[] args) {
        System.out.println(maxsumofSubarray(new int[]{1, -2, 3, 5, -2, 6, -1, 9}));
    }

    public static int maxsumofSubarray(int[] arr) {
        if (null == arr || 0 == arr.length) {
            return 0;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            // 默认累加值为前一位, 取 (累加值+当前值) 与 当前值 的较大值作为新值
            arr[i] = Math.max(arr[i - 1] + arr[i], arr[i]);
            // 取累加后的值与最大值的较大值作为最大值
            max = Math.max(arr[i], max);
        }
        return max;
    }

}
