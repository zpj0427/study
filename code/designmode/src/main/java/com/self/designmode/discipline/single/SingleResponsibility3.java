package com.self.designmode.discipline.single;

/**
 * 设计模式七大原则_单一职责原则
 * 即一个类应该只负责一项职责
 * @author pj_zhang
 * @create 2020-07-15 22:03
 **/
public class SingleResponsibility3 {

    public static void main(String[] args) {
        CarVehicle carVehicle = new CarVehicle();
        // 初始需求
        carVehicle.runCar("汽车");

        // 后续添加需求, 进行方法职责单一维度的变更
        carVehicle.runStreamer("轮船");
        carVehicle.runAir("飞机");
    }

    static class CarVehicle {

        public void runCar(String vehicleName) {
            System.out.println(vehicleName + " 在公路上跑...");
        }

        public void runStreamer(String vehicleName) {
            System.out.println(vehicleName + " 在大海上航行...");
        }

        public void runAir(String vehicleName) {
            System.out.println(vehicleName + " 在天空中飞...");
        }

    }

}
