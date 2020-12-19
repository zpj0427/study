package com.self.designmode.interpreter;

import java.util.Map;

/**
 * 解释器模式: 计算器问题, 符号_减法解释器
 * @author PJ_ZHANG
 * @create 2020-12-17 15:44
 **/
public class SubSymbolExpression extends SymbolExpression {

    public SubSymbolExpression(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public int interpreter(Map<String, Integer> dataMap) {
        return super.leftExpression.interpreter(dataMap) - super.rightExpression.interpreter(dataMap);
    }
}
