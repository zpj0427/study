package com.self.datastructure.z_nowcoder.easy;

import java.util.Stack;

/**
 * NC52: 括号序列
 * 给出一个仅包含字符'(',')','{','}','['和']',的字符串，判断给出的字符串是否是合法的括号序列
 * 括号必须以正确的顺序关闭，"()"和"()[]{}"都是合法的括号序列，但"(]"和"([)]"不合法。
 * 输入: "[", 输出: fasle
 * 输入: "[]", 输出: true
 *
 * @author PJ_ZHANG
 * @create 2021-04-27 17:00
 **/
public class NC52_BracketSequence {

    public static void main(String[] args) {
        System.out.println(isValid_1("{()()()()()}"));
    }

    /**
     * 不通过栈完成, 直接通过字符串替换完成
     * * 对括号组合进行无限替换, 即将(){}[]的连续组合替换为""
     * * 当替换到字符串长度不再变化时不再替换
     * * 如果此时字符串中还有元素, 说明不成对的括号
     * * 如果没有, 则说明括号全部成对
     * @param s
     */
    public static boolean isValid_1(String s) {
        for (;;) {
            int len = s.length();
            s = s.replace("()", "");
            s = s.replace("[]", "");
            s = s.replace("{}", "");
            if (len == s.length()) {
                return s.isEmpty();
            }
        }
    }

    /**
     * 通过栈完成,
     * * 在为左括号时, 统一进行括号入栈
     * * 在为右括号时, 弹栈一个元素
     * * 如果弹栈括号与该右括号匹配, 则继续进行下一个括号处理
     * * 如果不匹配, 直接返回 false
     * * 如果遇到右括号, 而栈里已经没有元素, 说明不匹配, 直接 false
     * * 如果元素已经遍历结束, 栈里面还有元素, 说明右括号不足, 直接 false
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if (null == s || s.length() <= 1) {
            return false;
        }
        char[] charArray = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(' || charArray[i] == '[' || charArray[i] == '{') {
                stack.push(charArray[i]);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                Character character = stack.pop();
                if (character == '(' && charArray[i] != ')')
                    return false;
                else if (character == '[' && charArray[i] != ']')
                    return false;
                else if (character == '{' && charArray[i] != '}')
                    return false;
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }

}
