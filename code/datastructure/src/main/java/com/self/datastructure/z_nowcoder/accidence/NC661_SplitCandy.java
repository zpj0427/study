package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC661: 热心的牛牛
 * 这一天你跟你的n个朋友一起出去玩，在出门前妈妈给了你k块糖果，
 * 你决定把这些糖果的一部分分享给你的朋友们。
 * 由于你非常热心，所以你希望你的每一个朋友分到的糖果数量都比你要多（严格意义的多，不能相等）。
 * 求你最多能吃到多少糖果？
 * @author PJ_ZHANG
 * @create 2021-04-23 15:27
 **/
public class NC661_SplitCandy {

    public static void main(String[] args) {
        System.out.println(maximumcandies(2, 10));
    }

    /**
     * 因为要保证我的糖果在绝对少的情况下尽可能的多, 可以分两步处理
     * 假设有13块糖, 有3个小朋友 + 我
     * 第一步: 先对糖果进行平分, 可能会剩下一些糖果, 也可能不会剩下
     * ***** 没人3块, 剩1块
     * 第二部: 分剩下的糖果, 此时可能存在三种情况
     * * 没有剩下糖果: 此时大家糖果都是一样的, 我随便给某个人一个糖果, 我就是绝对少时拥有最多的
     * * 剩下糖果了, 但不够给每个小朋友一块: 剩下1块, 有3个小朋友, 我和其他两个小朋友都拿的3块
     *               我直接给另外一个小朋友一块, 我就是绝对少时拥有最多的
     * * 剩下糖果了, 可以给每个小朋友一块: 如剩下三块, 3个小朋友各拿一块, 我不拿, 我就是绝对少时拥有最多的
     * 综上总结: 只要剩下的糖和小朋友的人数相等, 我就直接拿平均数;
     *           否则, 我就要让出去一块糖
     * @param n 小朋友人数
     * @param k 要分的糖果个数
     * @return
     */
    public static long maximumcandies (long n, long k) {
        long avg = k / (n + 1);
        long other = k % (n + 1);
        if (other == n) {
            return avg;
        }
        return avg - 1;
    }

}
