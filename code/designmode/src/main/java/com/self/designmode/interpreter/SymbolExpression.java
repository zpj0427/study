package com.self.designmode.interpreter;

/**
 * 解释器模式: 实现计算器, 符号解释器类
 * @author PJ_ZHANG
 * @create 2020-12-17 15:43
 **/
public abstract class SymbolExpression implements Expression {

    /**
     * 左侧元素解释器
     */
    protected Expression leftExpression;

    /**
     * 右侧元素解释器
     */
    protected Expression rightExpression;

    public SymbolExpression(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

}
