package com.self.datastructure.recursion;

/**
 * 递归_八皇后问题处理
 * @author LiYanBin
 * @create 2020-01-14 14:40
 **/
public class QueueDemo {

    private final static int MAX_COUNT = 8;

    private final static int[] QUEUE_SORT = new int[MAX_COUNT];

    public static void main(String[] args) {
        run(0); // 从0开始摆放, 总共有92中结果
    }

    /**
     * 进行八皇后递归运算
     * @param n 行号
     */
    public static void run(int n) {
        // 序列从0开始, n为最大值说明皇后已经位置摆放完毕, 直接输出
        if (n == MAX_COUNT) {
            showDetails();
            return;
        }
        // 此处n表示行号, 因为摆放棋盘是 MAX_COUNT * MAX_COUNT 的二维棋盘
        // n表示行号, 即从该行开始摆放
        for (int i = 0; i < MAX_COUNT; i++) {
            // 此处表示把皇后摆在第 n 行的 第 i 个索引上
            // 初始化时候, 第一个皇后摆在[0, 0]的位置,
            QUEUE_SORT[n] = i;
            // 摆放完成后, 判断该位置是否与其他位置冲突
            if (judge(n)) { // 为true表示不冲突
                // 不冲突时, 继续进行下一个皇后摆放
                run(n + 1);
            }
            // 冲突, 把当前皇后位置后移一位, 通过i++表示
        }
    }

    // 判断当前摆放位置是否合适
    // 不能在同一行, 一维数组表示, 这部分不存在
    // 不能在同一列, 即数组中不能有重复的值
    // 不能在同一斜线, 即 -> |索引 - 索引| != |索引值 - 索引值|
    public static boolean judge(int n) {
        // 遍历数组, 只遍历当前数组存在元素的部分
        // 即 n 以前的元素
        for (int i = 0; i < n; i++) {
            // QUEUE_SORT[i] == QUEUE_SORT[n] 表示在同一列
            // Math.abs(i - n) == Math.abs(QUEUE_SORT[i] - QUEUE_SORT[n]) 表示在同一斜线
            // 斜线算法可以根据正方形对角线考虑
            if (QUEUE_SORT[i] == QUEUE_SORT[n] || Math.abs(i - n) == Math.abs(QUEUE_SORT[i] - QUEUE_SORT[n])) {
                return false;
            }
        }
        return true;
    }

    // 打印组合方式
    public static void showDetails() {
        for (int data : QUEUE_SORT) {
            System.out.print(data + "  ");
        }
        System.out.println();
    }

}
