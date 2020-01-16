package com.self.datastructure.sort;

/**
 * 时间复杂度相关演示
 * @author LiYanBin
 * @create 2020-01-14 17:52
 **/
public class TimeComplexity {

    public static void main(String[] args) {

    }

    /**
     * O(n3) 立方阶, 就是三层线性循环嵌套
     */
    public static void n3(int count) {
        // 线性阶
        for (int z = 0; z < count; z++) {
            // 线性阶
            for (int i = 0; i < count; i++) {
                // 线性阶
                for (int j = 0; j < count; j++) {
                    System.out.println(z + i + j);
                }
            }
        }
    }

    /**
     * O(n2) 平方阶, 就是双层线性循环嵌套
     */
    public static void n2(int count) {
        // 线性阶
        for (int i = 0; i < count; i++) {
            // 线性阶
            for (int j = 0; j < count; i++) {
                System.out.println(i + j);
            }
        }
    }

    /**
     * O(nlog2n) 线程对数阶, 线性阶与对数阶的嵌套
     */
    public static void nlog2n(int count) {
        // 线性阶
        for (int i = 0; i < count; i++) {
            // 对数阶
            int j = 0;
            while (j < count) {
                j *= 2;
            }
        }
    }

    /**
     * O(n) 线性阶, 即代码循环次数随count的变化成线性变化
     */
    public static void n(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(i);
        }
    }

    /**
     * O(log2n) 对数阶
     * 此处 i 以二倍的速度增长, 也就是说到 2^n 后趋近于count, 整个过程执行log2n次
     */
    public static void log2n(int count) {
        for (int i = 1; i <= count; i *= 2);
    }

    /**
     * O(1) 常量
     * 没有循环结构的顺序执行, 无论执行多少行, 时间复杂度均为O(1)
     */
    public static void o1() {
        int i = 0;
        int j = 0;
        i++;
        j++;
        System.out.println(i + j);
    }

}
