package com.self.designmode.iterator;

/**
 * 客户端, 进行执行
 * @author PJ_ZHANG
 * @create 2020-12-11 17:38
 **/
public class Client {

    public static void main(String[] args) {
        // 总体展示类
        PrintCollege printCollege = new PrintCollege();

        // 构建计算机学院
        ComputerCollege computerCollege = new ComputerCollege();
        computerCollege.addDepartment(new Department("Java专业", "Java专业"));

        // 构造信息工程学院
        InfoCollege infoCollege = new InfoCollege();
        infoCollege.addDepartment(new Department("信息工程", "信息工程"));

        // 添加学院
        printCollege.addCollege(computerCollege);
        printCollege.addCollege(infoCollege);
        // 展示
        // 注意: 计算机学院使用数组, 信息工程学院使用的是集合
        printCollege.showCollege();
    }

}
