package com.self.designmode.uml.association;

/**
 * @author PJ_ZHANG
 * @create 2020-07-22 12:37
 **/
public class OneClass {
    private OtherClass otherClass;
}

class OtherClass {
    // 单向图示中不存在该部分
    private OneClass oneClass;
}
