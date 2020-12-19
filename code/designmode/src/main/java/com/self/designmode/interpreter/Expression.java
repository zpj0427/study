package com.self.designmode.interpreter;

import java.util.Map;

/**
 * 解释器模式: 实现计算器, 顶层表达式接口
 * @author PJ_ZHANG
 * @create 2020-12-17 15:39
 **/
public interface Expression {

    int interpreter(Map<String, Integer> dataMap);

}
