package com.self.datastructure.z_nowcoder.easy;

/**
 * NC68: 跳台阶
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）
 *
 * 首先找规律
 * * F(1) = 1 {1},
 * * F(2) = 2 {1+1, 2},
 * * F(3) = 3 {1+1+1, 1+2, 2+1},
 * * F(4) = 5 {1+1+1+1, 2+2, 2+1+1, 1+2+1, 1+1+2}
 * * F(N) = F(N - 1) + F(N - 2)
 *
 * 其次可以逆向推理
 * * 假如现在需要直接上到第6阶台阶
 * * 上去的方式只有两涨
 * *** 1, 从第5阶台阶跳一阶跳上去
 * *** 2, 从第4阶台阶跳两阶跳上去
 * * 无论是第4阶和第5阶台阶都可以上到第6阶台阶
 * * 第6阶台阶的上法就是上第4阶台阶的上法和第5阶台阶的上法之和
 * * 向下以此类推, 首先明确 F(1) = 1, F(2) = 2
 * * F(6) = F(4) + F(5) = 5 + 8 = 13
 *
 * @author PJ_ZHANG
 * @create 2021-04-25 15:02
 **/
public class NC68_JumpFloor {

    public static void main(String[] args) {
        // System.out.println(jumpFloor(100));
        System.out.println(jumpFloor_1(40));
    }

    /**
     * 非递归方式处理
     * 递归方式是从顶往下逆推取最终结果值
     * 非递归方式是从下向上顺序叠加
     *
     * 已知第1阶有一种跳法, 第2阶有两种跳法
     * 第N阶有 F(N - 1) + F(N - 2) 中跳法
     * 那么可以对前两个数据进行迭代保存, 循环获取第N个数的大小
     *
     * @param target
     * @return
     */
    public static int jumpFloor_1(int target) {
        if (target <= 2) {
            return target;
        }
        int first = 1, second = 2;
        int result = 0;
        for (int i = 3; i <= target; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }

    /**
     * 递归处理
     * 无论目标是多少台阶, 直接取前一阶跳法和前两阶跳发的和
     * 在目标台阶为1和2时, 取定值跳出即可
     *
     * @param target
     * @return
     */
    public static int jumpFloor(int target) {
        if (target <= 2) {
            return target;
        }
        return jumpFloor(target - 1) + jumpFloor(target - 2);
    }

}
