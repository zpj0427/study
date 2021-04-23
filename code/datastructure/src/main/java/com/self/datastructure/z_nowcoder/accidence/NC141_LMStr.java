package com.self.datastructure.z_nowcoder.accidence;

import java.util.logging.Level;

/**
 * NC141: 判断回文
 * 给定一个字符串，请编写一个函数判断该字符串是否回文。如果回文请返回true，否则返回false。
 * 输入: "absba"
 * 输出: true
 * 输入: "ranko"
 * 输出: fasle
 *
 * * 之前做了一次字符串反转, 可以直接在反转的基础上判断字符串是否相等就行
 * * 在这个基础上, 可以在字符数组进行首位比较的时候直接进行判断
 * * 即第一位和最后一位判断, 第二位和倒数第二位判断
 * * 如果有一组对应不上, 说明不是回文串
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 9:56
 **/
public class NC141_LMStr {

    public static void main(String[] args) {
        System.out.println(judge("abcd"));
        System.out.println(judge("abcdcba"));
    }

    /**
     * 通过字符数组首尾比较实现
     * @param str
     * @return
     */
    public static boolean judge (String str) {
        if (null == str || str.length() <= 1) {
            return true;
        }
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

}
