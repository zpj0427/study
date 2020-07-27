package com.self.designmode.adapter.interfaceadapter;

/**
 * 抽象适配器类: 用于对适配器顶层接口的方法进行初始化空实现
 * @author PJ_ZHANG
 * @create 2020-07-27 15:12
 **/
public abstract class AbstractAdapter implements IVoltageAdapter {

    @Override
    public int voltage() {
        return 0;
    }

    @Override
    public void m1() {
    }

    @Override
    public void m2() {
    }
}
