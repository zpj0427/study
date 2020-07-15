package com.self.designmode.discipline;

/**
 * 设计模式七大原则_单一职责原则
 * 即一个类应该只负责一项职责
 * @author pj_zhang
 * @create 2020-07-15 22:03
 **/
public class SingleResponsibility1 {

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        // 初始需求
        vehicle.run("汽车");

        // 后续添加需求
        vehicle.run("轮船");
        vehicle.run("飞机");
    }

    static class Vehicle {

        public void run(String vehicleName) {
            System.out.println(vehicleName + " 在公路上跑...");
        }

    }

}
