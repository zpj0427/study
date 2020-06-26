package com.self.datastructure.algorithm.dac;

/**
 * 分治算法实现之_汉诺塔
 * @author pj_zhang
 * @create 2020-06-26 17:55
 **/
public class HanoiTower {

    public static void main(String[] args) {
        hanoiTower(5, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔实现
     * @param num 汉诺塔数量
     * @param a 处理中的第一个塔柱
     * @param b 处理中的第二个塔柱
     * @param c 处理中的第三个塔柱
     */
    private static void hanoiTower(int num, char a, char b, char c) {
        if (num == 1) {
            // 如果数量为1, 则直接从A移动到C
            System.out.println("将第 " + num + " 个塔片从 " + a + " 移动到 " + c);
        } else {
            // 如果有多个塔片, 则分开处理
            // 首先最下层塔片上面的所有塔片从A移动到B
            hanoiTower(num - 1, a, c, b);
            // 然后将最下面的盘从A移动到C
            System.out.println("将第 " + num + " 个塔片从 " + a + " 移动到 " + c);
            // 最后将移动到B的盘统一移动到C
            hanoiTower(num - 1, b, a, c);
        }
    }

}
