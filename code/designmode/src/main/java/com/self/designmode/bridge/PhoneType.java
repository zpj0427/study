package com.self.designmode.bridge;

import com.self.designmode.adapter.interfaceadapter.Phone;

/**
 * 手机类型: 抽象层顶层类
 * @author PJ_ZHANG
 * @create 2020-07-27 17:53
 **/
public abstract class PhoneType {
    private IBrand brand;
    public PhoneType(IBrand brand) {
        this.brand = brand;
    }
    public void open() {
        brand.open();
    }
    public void call() {
        brand.call();
    }

    public void close() {
        brand.close();
    }
}
