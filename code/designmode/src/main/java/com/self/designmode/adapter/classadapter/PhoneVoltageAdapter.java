package com.self.designmode.adapter.classadapter;

/**
 * 适配器类: 手机电压
 * @author PJ_ZHANG
 * @create 2020-07-27 14:17
 **/
public class PhoneVoltageAdapter extends NormalVoltage implements IVoltageAdapter {
    @Override
    public int voltage() {
        // 获取标准电压
        int voltage = super.voltage();
        // 获取手机充电标准电压5V
        return voltage / 44;
    }
}
