package com.self.designmode.adapter.interfaceadapter;

/**
 * 适配器类: 手机电压
 * @author PJ_ZHANG
 * @create 2020-07-27 14:17
 **/
public class PhoneVoltageAdapter extends AbstractAdapter {

    private NormalVoltage normalVoltage;

    public PhoneVoltageAdapter(NormalVoltage normalVoltage) {
        this.normalVoltage = normalVoltage;
    }

    @Override
    public int voltage() {
        // 获取标准电压
        int voltage = normalVoltage.voltage();
        // 获取手机充电标准电压5V
        return voltage / 44;
    }
}
