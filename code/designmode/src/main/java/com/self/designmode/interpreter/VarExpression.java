package com.self.designmode.interpreter;

import java.util.Map;

/**
 * 解释器模式: 实现计算器, 元素解释器类
 * @author PJ_ZHANG
 * @create 2020-12-17 15:41
 **/
public class VarExpression implements Expression {

    private String name;

    public VarExpression(String name) {
        this.name = name;
    }

    @Override
    public int interpreter(Map<String, Integer> dataMap) {
        return dataMap.get(name);
    }
}
