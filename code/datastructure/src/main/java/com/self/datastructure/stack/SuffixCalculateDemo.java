package com.self.datastructure.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 后缀表达式进行计算器计算
 * @author LiYanBin
 * @create 2020-01-13 14:40
 **/
public class SuffixCalculateDemo {

    public static void main(String[] args) {
        // 转换后的后缀表达式: 34+5*6-
        // 每一位之间添加中断符#
        String permission = TransformPermission.transformPermission("10+((20+30)*40)-50");
        System.out.println(calculate(permission));
    }

    // 后缀表达式计算
    public static int calculate(String suffixPermission) {
        // 对表达式进行list转换
        List<String> lstElement = Arrays.asList(suffixPermission.split("#", -1));
        Stack<Integer> numberStack = new Stack<>();
        for (String element : lstElement) {
            if (element.matches("^[+-]?[0-9]+$")) {
                numberStack.push(Integer.valueOf(element));
            } else if (element.matches("^[+\\-*/]$")) {
                doCalculate(numberStack, element);
            }
        }
        // 全部计算完成后, 弹出结果
        return numberStack.pop();
    }

    // 计算
    private static void doCalculate(Stack<Integer> numberStack, String operate) {
        if (numberStack.size() < 2) {
            throw new RuntimeException("表达式有误...");
        }
        // 栈后入先出, 所以应该用 number2 [operate] number1
        Integer number1 = numberStack.pop();
        Integer number2 = numberStack.pop();
        numberStack.push(getResult(number2, number1, operate));
    }

    private static Integer getResult(Integer firstNumber, Integer secondNumber, String operate) {
        switch (operate) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                return firstNumber / secondNumber;
        }
        throw new RuntimeException("操作符无效...");
    }

}
