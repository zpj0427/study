package com.self.designmode.interpreter;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author PJ_ZHANG
 * @create 2020-12-17 15:18
 **/
public class Client {

    public static void main(String[] args) {
//        Spring提供了一个计算器, 可以直接计算
//        String str = "-1 + 2 * (3 + 4)";
//        SpelExpressionParser parser = new SpelExpressionParser();
//        Expression expression = parser.parseExpression(str);
//        System.out.println(expression.getValue());
        // 输入表达式, 按a+b-c+d等类型输入
        System.out.println("请输入表达式: eg: a+b-c+d");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        Map<String, Integer> dataMap = parseExpression(expression);
        Calculator calculator = new Calculator();
        calculator.parseExpression(expression);
        System.out.println("最终结果: " + calculator.getValue(dataMap));
    }

    private static Map<String,Integer> parseExpression(String expression) {
        Map<String, Integer> dataMap = new HashMap<>(16);
        for (char c : expression.toCharArray()) {
            if (String.valueOf(c).matches("^[a-z]$")) {
                System.out.println("请输入 " + c + " 的值");
                Scanner scanner = new Scanner(System.in);
                int value = scanner.nextInt();
                dataMap.put(String.valueOf(c), value);
            }
        }
        return dataMap;
    }

}
