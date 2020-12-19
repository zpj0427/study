package com.self.designmode.interpreter;

import java.util.Map;
import java.util.Stack;

/**
 * 解释器模式: 实现计算器, 上下文类_计算器
 * @author PJ_ZHANG
 * @create 2020-12-17 15:45
 **/
public class Calculator {

    private Expression expression;

    /**
     * 解析表达式, 生成解释器表达式, 为后续计算做准备
     * @param expressionStr 计算器串
     * @return
     */
    public void parseExpression(String expressionStr) {
        // 通过一个栈对数据进行存储
        Stack<Expression> stack = new Stack<>();
        // 转换表达式为char数组, 没有做复杂处理, 说明问题即可
        char[] charArray = expressionStr.toCharArray();
        // 遍历元素组进行处理
        for (int i = 0; i < charArray.length; i++) {
            Expression leftExpression;
            Expression rightExpression;
            switch (charArray[i]) {
                // 加法处理
                // 遍历到符号, 说明左侧已经处理(不考虑第一位+-)
                // 取左侧数据, 作为符号处理的左侧表达式
                // 取右侧元素, 作则符号处理的右侧表达式
                // 构建符号解释器, 加入栈中
                case '+':
                    leftExpression = stack.pop();
                    rightExpression = new VarExpression(charArray[++i] + "");
                    stack.push(new AddSymbolExpression(leftExpression, rightExpression));
                    break;
                // 减法处理
                // 减法同上
                case '-':
                    leftExpression = stack.pop();
                    rightExpression = new VarExpression(charArray[++i] + "");
                    stack.push(new SubSymbolExpression(leftExpression, rightExpression));
                    break;
                // 元素处理
                // 直接将元素构建表达式添加到栈中
                default:
                    stack.push(new VarExpression(charArray[i] + ""));
            }
        }
        // 最终生成的抽象语法树
        expression = stack.pop();
    }

    /**
     * 根据生成的解释器表达式, 计算最终结果
     * @param dataMap 数组的元素数据
     * @return 返回最终结果
     */
    public int getValue(Map<String, Integer> dataMap) {
        // 这部分会是一个递归处理,
        // 执行该抽象语法树, 生成最终结果
        return expression.interpreter(dataMap);
    }

}
