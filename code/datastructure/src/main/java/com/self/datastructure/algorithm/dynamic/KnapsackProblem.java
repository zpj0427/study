package com.self.datastructure.algorithm.dynamic;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 动态规划: 背包问题
 * 问题: 有一个背包, 容量为4磅, 现在有三种不同重量不同价值商品, 怎样放能让背包价值更大
 * 吉他     1磅   1500
 * 音响     4磅   3000
 * 电脑     3磅   2000
 *
 * String[] nameArr = {"吉他", "音响", "电脑"}
 * int[] weightArr = {1, 4, 3}
 * int[] priceArr = {1500, 3000, 2000}
 * int packageCapacity = 4; // 背包容量
 *
 *
 * 分析:
 * * 通过物品和背包重量构建一个二维数组, 背包重量从0到指定数量, 给每一种重量递进填充当前情况下的最优解
 * * 以此递进到指定背包容量时, 该二维数组对应数据即为最大价值,
 * @author PJ_ZHANG
 * @create 2020-07-02 12:29
 **/
public class KnapsackProblem {

    public static void main(String[] args) {
        // 商品信息, 数组索引相对应
        // 名称数组
        String[] nameArr = {"吉他", "音响", "电脑"};
        // 重量数组
        int[] weightArr = {1, 4, 3};
        // 价格数组
        int[] priceArr = {1000, 3000, 6000};
        // 背包容量
        int packageCapacity = 6;
        // 不可重复
        backpackWithoutRepeat(nameArr, weightArr, priceArr, packageCapacity);
        // 可重复, 与不可重复变更不大, 只对关键部分注释
        backpackWithRepeat(nameArr, weightArr, priceArr, packageCapacity);
    }


    /**
     * 装入背包
     * @param nameArr 名称数组
     * @param weightArr 重量数组
     * @param priceArr 价格数组
     * @param packageCapacity 背包容量
     */
    private static void backpackWithRepeat(String[] nameArr, int[] weightArr, int[] priceArr, int packageCapacity) {
        int[][] packageArr = new int[nameArr.length + 1][packageCapacity + 1];
        String[][] contentArr = new String[nameArr.length + 1][packageCapacity + 1];
        for (int i = 1; i < packageArr.length; i++) {
            for (int j = 1; j < packageArr[i].length; j++) {
                if (weightArr[i - 1] > j) {
                    packageArr[i][j] = packageArr[i - 1][j];
                } else {
                    int onePart = packageArr[i - 1][j];
                    // 此处取当前行的前置价格, 当前行就可能UC你再重复
                    int otherPart = priceArr[i - 1] + packageArr[i][j - weightArr[i - 1]];
                    packageArr[i][j] = Math.max(onePart, otherPart);
                    // 商品已经使用, 通过String数组对名称进行记录
                    if (packageArr[i][j] == onePart) {
                        contentArr[i][j] = contentArr[i - 1][j];
                    } else {
                        contentArr[i][j] = nameArr[i - 1] + "," +
                                (StringUtils.isEmpty(contentArr[i][j - weightArr[i - 1]])
                                        ? ""
                                        : contentArr[i][j - weightArr[i - 1]]);
                    }
                }
            }
        }
        // 背包价值
        System.out.println("背包价值: " + packageArr[nameArr.length][packageCapacity]);
        // 背包内容
        System.out.println("背包内容: " + contentArr[nameArr.length][packageCapacity]);
    }


    /**
     * 装入背包
     * @param nameArr 名称数组
     * @param weightArr 重量数组
     * @param priceArr 价格数组
     * @param packageCapacity 背包容量
     */
    private static void backpackWithoutRepeat(String[] nameArr, int[] weightArr, int[] priceArr, int packageCapacity) {
        // 构建背包重量从0到指定重量对应的价格最优解二维数组
        // 该数组是对背包从0到指定重量的所有重点上价格最优解的罗列
        // 第一维表示商品, 并空出第一行, 初始化为0
        // 第二维表示背包重量从0到指定重量,
        // 值表示在当前背包容量下, 和现有商品条件下, 背包包含价格的最优解
        // 如果商品数量是(50), 背包容量是(100),
        // 值(int[i][j])表示在当前背包容量下, 前i个元素的最优解(不表示所有元素)
        // int数组初始化为0, 所以不用刻意处理, 空出即可
        int[][] packageArr = new int[nameArr.length + 1][packageCapacity + 1];
        // 构建可能装入背包的二维数组
        // 第一位表示商品, 第二维表示重量
        // 值为0时说明不会装进背包, 值为1说明可能装入背包, 最终最优解可能不会装入
        int[][] contentArr = new int[nameArr.length + 1][packageCapacity + 1];
        // 装入背包基本计算公式
        // 如果当前商品重量大于当前遍历到的背包容量, 则把当前列(重量列)的上一行值(也可能是取的上一行值)赋给该值
        // 即 int[i][j] = int[i - 1][j]
        // 如果当前商品重点小于等于遍历到的背包容量, 则对两部分内容进行比较
        // 第一部分: 该列上一行的最优解
        // 第二部分: 当前商品价值 +  该行在(总重量-当前商品重量)处的最优解
        // 开始遍历, 先遍历第一维, 即商品维度, 从1开始遍历, 跳过第一行
        for (int i = 1; i < packageArr.length; i++) {
            // 再遍历第二维, 即背包容量维度, 从1开始遍历, 跳过第一列
            for (int j = 1; j < packageArr[i].length; j++) {
                // 对商品重量和背包容量(j)进行比较
                if (weightArr[i - 1] > j) {
                    // 当前商品 > 背包容量, 取同列上一行数据
                    packageArr[i][j] = packageArr[i - 1][j];
                    // 因为不存在商品装入, 不对contentArr进行处理
                } else {
                    // 当前商品 <= 背包容量, 对两部分内容进行比较
                    int onePart = packageArr[i - 1][j];
                    // priceArr[i - 1]: 当前商品价格
                    // weightArr[i - 1]: 当前商品重量
                    // j - weightArr[i - 1]: 去掉当前商品, 背包剩余容量
                    // 不可重复: packageArr[i - 1][j - weightArr[i - 1]]: 在上一行, 取剩余重量下的价格最优解
                    // 根据是否可以重复取横坐标
                    int otherPart = priceArr[i - 1] + packageArr[i - 1][j - weightArr[i - 1]];
                    // 取最大值为当前位置的最优解
                    packageArr[i][j] = Math.max(onePart, otherPart);
                    // 如果最优解包含当前商品, 则表示当前商品已经被使用, 进行记录
                    if (otherPart == packageArr[i][j]) {
                        contentArr[i][j] = 1;
                    }
                }
            }
        }

        // 不能重复的场景中
        // 如果该位置的标志位为1, 说明该商品参与了最终的背包添加
        // 如果该位置的标志位为0, 即使该位置的价格为最大价格, 也是从其他位置引用的价格
        // 因为不能重复, 所以每行只取一个数据参与最终计算, 并只判断在最大位置该商品是否参与
        // 该最大位置会随着已经遍历出其他元素而对应不断减小, 直到为0

        // 二维数组最后一个元素必然是最大值, 但是需要知道该最大值是自身计算的 还是比较后引用其他的
        int totalPrice = 0;
        // 最大行下标数, 即商品数
        int maxLine = contentArr.length - 1;
        // 最大列下标数, 即重量
        int maxColumn = contentArr[0].length - 1;
        for (;maxLine > 0 && maxColumn > 0;) {
            // 等于1表示在该位置该商品参与了计算
            if (contentArr[maxLine][maxColumn] == 1) {
                maxColumn -= weightArr[maxLine - 1];
                totalPrice += priceArr[maxLine - 1];
                System.out.printf("%s 加入了背包 \n", nameArr[maxLine - 1]);
            }
            maxLine--;
        }
        System.out.println("不重复情况下, 背包可容纳的最大价值: " + totalPrice);
    }

}
