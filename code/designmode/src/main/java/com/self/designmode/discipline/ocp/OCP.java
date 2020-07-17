package com.self.designmode.discipline.ocp;

/**
 * 设计模式七大原则_OCP原则
 * @author PJ_ZHANG
 * @create 2020-07-17 15:31
 **/
public class OCP {

    public static void main(String[] args) {
        Client client = new Client();
        client.draw(new Circle());
        client.draw(new Square());
        client.draw(new Triangle());
    }

    /**
     * 客户端调用
     */
    static class Client {
        public void draw(Shape shape) {
            shape.draw();
        }
    }

    interface Shape {
        void draw();
    }

    // 圆
    static class Circle implements Shape {
        @Override
        public void draw() {
            System.out.println("绘制圆...");
        }
    }

    // 正方形
    static class Square implements Shape {
        @Override
        public void draw() {
            System.out.println("绘制正方形...");
        }
    }

    // 三角形
    static class Triangle implements Shape {
        @Override
        public void draw() {
            System.out.println("绘制三角形...");
        }
    }

}
