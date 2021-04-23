package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC103：反转字符串
 * 写出一个程序，接受一个字符串，然后输出该字符串反转后的字符串。（字符串长度不超过1000）
 * 输入: abcd
 * 输出: dcba
 *
 * @author PJ_ZHANG
 * @create 2021-04-22 9:42
 **/
public class NC103_ReverseStr {

    public static void main(String[] args) {
        System.out.println(reverseStr_1("abcd"));
        System.out.println(reverseStr_2("abcd"));
        System.out.println(reverseStr_3("abcd"));
    }

    /**
     * 第三种方式，调用API
     *
     * @param str
     * @return
     */
    public static String reverseStr_3(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 第二种方式，位置交换
     * * 将字符串先改为字符数组：如 "abcd" -> ['a', 'b', 'c', 'd']
     * * 然后对字符数组的第一个元素和最后一个元素进行交换, 第二个元素和倒数第二个元素进行交换
     * * 直到进行到 < 中间索引, 则停止交换(偶数位全部交换, 奇数位中间索引不动)
     * * 第0位和第 len - 1位交换, len - 0 - 1
     * * 第1位和第 len - 2位交换, len - 1 - 1
     * * 第2位和第 len - 3位交换, len - 2 - 1
     * * 第i位和第 len - i - 1位交换
     * * 最后重组字符串完成反转
     *
     * @param str
     * @return
     */
    public static String reverseStr_2(String str) {
        if (null == str || str.length() == 0) {
            return str;
        }
        // 完成交换
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length / 2; i++) {
            char temp = array[array.length - i - 1];
            array[array.length - i - 1] = array[i];
            array[i] = temp;
        }
        // 重组字符串
        String reverseStr = "";
        for (char c : array) {
            reverseStr += c;
        }
        return reverseStr;
    }

    /**
     * 第一种方式反转字符串：直接倒叙输出
     * 从最后一个字符开始遍历字符串，并追加到一个新的字符串后面，完成字符串反转
     *
     * @param str
     * @return
     */
    public static String reverseStr_1(String str) {
        if (null == str || str.length() == 0) {
            return str;
        }
        String reverseStr = "";
        // 反转输出
        for (int i = str.length() - 1; i >= 0; i--) {
            reverseStr += str.charAt(i);
        }
        return reverseStr;
    }

}
