package com.self.designmode.adapter.interfaceadapter;

/**
 * 使用类
 * @author PJ_ZHANG
 * @create 2020-07-27 14:19
 **/
public class Phone {
    public void charge(IVoltageAdapter voltageAdapter) {
        int voltage = voltageAdapter.voltage();
        System.out.println("对手机进行充电, 充电电压: " + voltage);
    }
}
