package com.self.designmode.discipline.ocp;

/**
 * 从一个普通的问题看问题所在
 * @author LiYanBin
 * @create 2020-07-17 14:50
 **/
public class OrdinaryFun {

    public static void main(String[] args) {
        Client client = new Client();
        client.draw(new Circle().typeEnum);
        client.draw(new Square().typeEnum);
        client.draw(new Triangle().typeEnum);
    }

    /**
     * 客户端调用
     */
    static class Client {
        public void draw(TypeEnum typeEnum) {
            if (TypeEnum.CIRCLE == typeEnum) {
                System.out.println("绘制圆...");
            } else if (TypeEnum.SQUARE == typeEnum) {
                System.out.println("绘制方形...");
            } else if (TypeEnum.TRIANGLE == typeEnum) {
                System.out.println("绘制三角形...");
            }
        }
    }

    enum TypeEnum {
        // 圆, 正方形, 三角形
        CIRCLE, SQUARE, TRIANGLE
    }

    // 圆
    static class Circle {
        TypeEnum typeEnum;
        public Circle() {
            typeEnum = TypeEnum.CIRCLE;
        }
    }

    // 正方形
    static class Square {
        TypeEnum typeEnum;
        public Square() {
            typeEnum = TypeEnum.SQUARE;
        }
    }

    // 三角形
    static class Triangle {
        TypeEnum typeEnum;
        public Triangle() {
            typeEnum = TypeEnum.TRIANGLE;
        }
    }

}
