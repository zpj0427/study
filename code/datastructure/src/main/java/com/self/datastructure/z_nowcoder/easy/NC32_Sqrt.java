package com.self.datastructure.z_nowcoder.easy;

/**
 * NC32: 求平方根
 * 实现函数 int sqrt(int x).
 * 计算并返回x的平方根（向下取整）
 * 算法提示: 二分法
 * 输入:2, 输出:1
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 10:16
 **/
public class NC32_Sqrt {

    public static void main(String[] args) {
        System.out.println(sqrt(1));
    }

    /**
     * 采用二分法处理, 则对数取中值进行处理
     * 该提注意整数溢出问题, 直接从int 提升到 long 进行处理
     *
     * @param x
     * @return
     */
    public static int sqrt(int x) {
        // 特殊数据单独处理
        if (x <= 1) {
            return x;
        }
        // 定义左右边界, 为两个极值
        long left = 0;
        long right = x;
        for (; ; ) {
            // 取中值进行后续处理
            long middle = (left + right) / 2;
            // 如果中值与左右边界相等, 说明已经处理完成没有找到, 该数无法整值平方
            // 取较小值进行返回
            if (middle == left || middle == right) {
                return (int)left;
            }
            if (middle * middle < x) {
                // 值较小, 向右半部分处理
                left = middle;
            } else if (middle * middle > x) {
                // 值较大, 向左半部分处理
                right = middle;
            } else {
                // 相等直接退出
                return (int)middle;
            }
        }
    }

}
