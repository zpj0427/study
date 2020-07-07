package com.self.datastructure.algorithm.kmp;

/**
 * KMP算法
 * * 生成部分匹配表, 此处注意生成规则
 * * 先遍历寻找到首个匹配字符
 * * 按照匹配字符依次向后匹配, 如果匹配后则继续匹配,
 * * 如果没有匹配到, 按照已经匹配到的字符长度从部分匹配表中找到对应的部分匹配值, 即已匹配部分的前缀
 * * 匹配的首字符后移位数 = 当前已经匹配位数 - 部分匹配表中以匹配部分对应的值
 * * 后移完成后, 继续从上次匹配的断点与子串需要新对应位置匹配, 匹配到继续, 未匹配到重复步骤
 * * 匹配完成后, 如果要进行所有子串匹配, 则后移子串位数, 继续匹配即可
 * @author PJ_ZHANG
 * @create 2020-07-07 17:35
 **/
public class KMP {

    public static void main(String[] args) {

    }

    /**
     * 通过KMP算法进行匹配
     * @param str 字符串
     * @param childStr 子串
     * @return 返回索引
     */
    private static int kmp(String str, String childStr) {
        // 获取串的部分匹配值, 通过int[]表示, 下标表示位数, 值表示对应的匹配数量长度
        return -1;
    }

}
