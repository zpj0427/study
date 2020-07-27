package com.self.designmode.adapter.interfaceadapter;

/**
 * 适配器: 被适配类, 原类, 标准电压220V
 * @author PJ_ZHANG
 * @create 2020-07-27 14:12
 **/
public class NormalVoltage {
    public int voltage() {
        System.out.println("适配器源类... 获取220V电压...");
        return 220;
    }
}
