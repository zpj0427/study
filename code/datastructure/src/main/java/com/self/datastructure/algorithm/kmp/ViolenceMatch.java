package com.self.datastructure.algorithm.kmp;

/**
 * 暴力匹配算法
 * 算法解析:
 * * 对一个字符串A, 匹配目标子串a
 * * 定义两个变量, 字符串A的起始标记i, 从0开始, 字符串a的起始比较offset, 从0开始
 * * 用A[i]匹配a[offset], 如果匹配则++
 * * 如果不匹配, i移位到原始i的后一位, 因为过程中, i可能存在++操作
 * * 但是i具体移位了多少是通过offset体现出来的, 所以i的后一位就是(i-j+1)
 * @author pj_zhang
 * @create 2020-07-05 12:33
 **/
public class ViolenceMatch {

    public static void main(String[] args) {
        String source = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String target = "尚硅谷你尚硅你";
        System.out.println(violenceMatch(source, target));
    }

    private static int violenceMatch(String source, String target) {
        // 先分别转换为char数组
        char[] sourceArr = source.toCharArray();
        char[] targetArr = target.toCharArray();

        // 定义source字符串的偏移量i
        // 定义target字符串的偏移量offset
        int i = 0;
        int offset = 0;
        // 计算i的最大范围, 因为target的长度不定, 所以source剩余位置不够即可匹配完成
        int max = sourceArr.length - targetArr.length;
        // 开始匹配, 只要i <= max, 即可满足
        for (;i <= max;) {
            // 先查找到第一个位置, 第一个位置不对, 遍历向后查找
            if (sourceArr[i] != targetArr[offset]) {
                for (;sourceArr[i] != targetArr[offset] && i <= max; i++);
            }
            // if条件符合, 说明已经查找到第一个位置, 进行后续位置查找
            if (i <= max) {
                // 进行后续字符串匹配, 如果全部匹配成功, 则j == targetArr.length - 1
                for (;i < sourceArr.length && offset < targetArr.length && sourceArr[i] == targetArr[offset]; i++, offset++);
                // 如果j不等于该值, 说明中间存在位置没有匹配上
                // 此时对j置0
                // i设置为i的原始值+1
                if (offset != targetArr.length) {
                    i = i - offset + 1;
                    offset = 0;
                } else {
                    // 匹配到, 直接返回结果
                    return i - offset;
                }
            }
        }
        // 没有匹配到直接返回-1
        return -1;
    }

}
