package com.self.datastructure.z_nowcoder.easy;

/**
 * NC7_股票交易
 * 假设你有一个数组，其中第 i 个元素是股票在第 i 天的价格。
 * 你有一次买入和卖出的机会。（只有买入了股票以后才能卖出）。请你设计一个算法来计算可以获得的最大收益。
 * 输入: [1,4,2]
 * 输出: 3
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 11:48
 **/
public class NC7_StockDeal {

    public static void main(String[] args) {
        System.out.println(maxProfit_1(new int[] {1,4,2}));
    }

    /**
     * 动态规划, 取每一天的最优值
     * @param prices
     * @return
     */
    public static int maxProfit_1 (int[] prices) {
        // 最大收益默认为0
        int max = 0;
        // 最小买进价格默认为1
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 当前价格小于最小买进价格, 进行替换
            if (prices[i] < min) {
                min = prices[i];
            }
            // 用当前价格 - 最小买进价格与最大值进行比
            // 取较大值作为最大收益
            max = Math.max(prices[i] - min, max);
        }
        return max;
    }

    /**
     * 暴力算法, 前面买, 后面卖, 去差值最大值即可
     * 时间复杂度: O(n2)
     * @param prices
     * @return
     */
    public static int maxProfit (int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                // 前面买, 后面卖
                int money = prices[j] - prices[i];
                max = money > max ? money : max;
            }
        }
        return max;
    }

}
