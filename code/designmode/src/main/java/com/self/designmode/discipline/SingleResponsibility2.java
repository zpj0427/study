package com.self.designmode.discipline;

/**
 * 设计模式七大原则_单一职责原则
 * 即一个类应该只负责一项职责
 * @author pj_zhang
 * @create 2020-07-15 22:03
 **/
public class SingleResponsibility2 {

    public static void main(String[] args) {
        CarVehicle carVehicle = new CarVehicle();
        // 初始需求
        carVehicle.run("汽车");

        // 后续添加需求, 进行类职责单一维度的变更
        SteamerVehicle steamerVehicle = new SteamerVehicle();
        steamerVehicle.run("轮船");
        AirVehicle airVehicle = new AirVehicle();
        airVehicle.run("飞机");
    }

    static class CarVehicle {

        public void run(String vehicleName) {
            System.out.println(vehicleName + " 在公路上跑...");
        }

    }

    static class SteamerVehicle {

        public void run(String vehicleName) {
            System.out.println(vehicleName + " 在大海上航行...");
        }

    }

    static class AirVehicle {

        public void run(String vehicleName) {
            System.out.println(vehicleName + " 在天空中飞...");
        }

    }

}
