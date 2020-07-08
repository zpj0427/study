package com.self.datastructure.algorithm.kmp;

/**
 * KMP算法
 * * 生成部分匹配表, 此处注意生成规则
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
        //String str = "ABCDABCABCDABCCABCDABCABCDABCD";
        //String childStr = "ABCDABCABCDABCD";
        String str = "BBC ABCDAB ABCDABCDABDE";
        String childStr = "ABCDABD";
        System.out.println(kmp(str, childStr));
    }

    /**
     * 通过KMP算法进行匹配
     * @param str 字符串
     * @param childStr 子串
     * @return 返回索引
     */
    private static int kmp(String str, String childStr) {
        // 获取串的部分匹配值
        int[] arr = partMachingValue(childStr);
        // 进行KMP计算
        // i: 主串的匹配索引位置
        // j: 模式串的匹配索引位置
        for (int i = 0, j = 0; i < str.length(); i++) {
            // 如果存在匹配过的位数, 并且当前字符没有匹配到
            // 则将模式串后移, 通过j减的方式实现
            // 比如j已经匹配到了6位, 第7位没有匹配到
            // 则取前6位的部分匹配值, 即arr[5]
            // 如果前六位有三位是前后缀匹配的, 则继续从第四位开始匹配剩余的部分, 以此类推
            // 如果不存在匹配过的位数, 则直接通过i++进行第一位的匹配
            if (j > 0 && str.charAt(i) != childStr.charAt(j)) {
                j = arr[j - 1];
            }
            // 如果匹配, 则j++ 继续向后比较
            if (str.charAt(i) == childStr.charAt(j)) {
                j++;
            }
            // 如果j的值等于childStr的长度, 则匹配完成
            if (j == childStr.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    /**
     * 部分匹配值数组生成
     * @param modelStr 字符串
     * @return 部分匹配值数组
     */
    private static int[] partMachingValue(String modelStr) {
        int[] arr = new int[modelStr.length()];
        // 第一个空初始化为0, 此处不多余处理
        // 将长字符串先按两个字符进行处理, 并依次添加字符, 知道匹配完成
        for (int i = 1, j = 0; i < modelStr.length(); i++) {
            // 如果对应的两个字符不相等
            // 个人感觉此处基于的思想是:
            // 首先, j > 0, 也就是说已经在前后缀有较长字符串匹配了, 再继续匹配该值
            // 然后, 添加上该值后, 因为该值影响, 前后缀现有的长字符串匹配断开,
            // 再然后, 虽然添加该值影响长匹配断开, 但不排除依旧有较短的前后缀字符串可以匹配, 如: ABCDABCABCDABCD
            // 虽然 ABCDABC 的匹配因为前缀的 A 和后缀的 D匹配不到端口, 但是不影响 ABC 的后一个字符 D 的匹配
            // 所以此处需要往前找, 找到的合适的位置与后缀的新匹配字符进行匹配, 如果匹配到则可关联上部分匹配
            // 此时找的标准是, 先找前缀字符串(ABCDABC)最末索引(j - 1 = 7 - 1 = 6)对应的部分匹配值(arr[j - 1] = 3),
            // 并以该部分匹配值作为索引获取到对应的字符(modelStr.charAt(j) = D)
            //     此处注意, 一定是前缀再前缀, 如果前缀的后一个字符与当前字符能匹配到, 那就不可能走到这一步
            //     继续注意: 前缀与后缀匹配, 前缀再前缀就相当于后缀再后缀, 所以前缀再前缀等于即将与当前字符关联的子串
            // 所以, 判断前缀的后一个字符, 即索引出的字符与当前拼接字符是否相等, 如果相等, 则部分匹配值+1
            // 如果不相等, 继续这部分循环, 直到匹配到或者遍历完前缀确定为0
            while (j > 0 && modelStr.charAt(i) != modelStr.charAt(j)) {
                j = arr[j - 1];
            }
            // 如果两个字符相等, 说明已经匹配到了
            if (modelStr.charAt(i) == modelStr.charAt(j)) {
                // j自增是将j一直向推
                // 如果只有两个字符, 则i表示第一个, j表示第二个
                // 此时如果对应的字符相等, 则匹配到一个
                // 如果有三个字符, 在两个字符的基础上继续推进, i, j都有自增
                // 此时如果对应的字符依旧相等, 则j=2, 表示匹配两个, 以此类推
                j++;
            }
            arr[i] = j;
        }

        return arr;
    }

}
