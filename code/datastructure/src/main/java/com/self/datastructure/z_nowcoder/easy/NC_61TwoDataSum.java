package com.self.datastructure.z_nowcoder.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * NC61: 两数之和
 * 给出一个整数数组，请在数组中找出两个加起来等于目标值的数，
 * 你给出的函数twoSum 需要返回这两个数字的下标（index1，index2），需要满足 index1 小于index2.。注意：下标是从1开始的
 * 假设给出的数组中只存在唯一解
 * 例如：
 * 给出的数组为 {20, 70, 110, 150},目标值为90
 * 输出 index1=1, index2=2
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 16:07
 **/
public class NC_61TwoDataSum {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum_1(new int[] {20, 70, 110, 150}, 260)));
    }

    /**
     * 第二种算法: 引入Map
     * 一次遍历, 在Map中进行差值记录, 满足条件后返回
     * * 遍历到某一个值时, 取目标值和该值的差值, 存储到 Map 中记录
     * * 表示该值需要一个怎样的值来组成 target, value为该值索引
     * * 在遍历到每一个数据的时候, 先从 map 中以自身为 key 进行匹配
     * * 如果匹配成功说明, 则直接返回 key 对应的 value为index1, 该值索引为index2
     * * 如果没有匹配成功, 则重复第一步, 存储差值, 进行遍历
     * * 最后注意返回的索引全部+1
     * 时间复杂度为O(n)
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum_1 (int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int otherData = target - numbers[i];
            if (map.containsKey(numbers[i])) {
                return new int[] {map.get(numbers[i]) + 1, i + 1};
            } else {
                map.put(otherData, i);
            }
        }
        return new int[0];
    }

    /**
     * 第一种算法: 暴力算法
     * 双循环进行集合遍历, 计算每两种组合的数据和, 满足要求直接返回
     * 时间复杂度为O(n2)
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum (int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] != target) {
                    continue;
                }
                // 索引从0开始, 计数从1开始, 所以加1
                return new int[] {i + 1, j + 1};
            }
        }
        return new int[0];
    }

}
