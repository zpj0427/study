package com.self.designmode.bridge;

/**
 * @author PJ_ZHANG
 * @create 2020-07-27 17:58
 **/
public class Client {
    public static void main(String[] args) {
        PhoneType phoneType = new UpRightType(new Huawei());
        phoneType.call();

        phoneType = new FlodedType(new Xiaomi());
        phoneType.open();
    }
}
