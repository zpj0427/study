package com.self.designmode.state;

/**
 * 状态模式: 抽奖流程状态枚举类
 * @author PJ_ZHANG
 * @create 2020-12-17 17:26
 **/
public enum StateTypeEnum {

    // 0:不能抽奖, 1:可以抽奖, 2:抽奖成功,发放奖品, 3:奖品发放结束
    NON("0"), CAN("1"), PROVIDE("2"), COMPLETE("3");

    private String type;

    private StateTypeEnum(String type) {
        this.type = type;
    }

    public String getStrValue() {
        return this.type;
    }
}
