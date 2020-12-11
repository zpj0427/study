package com.self.designmode.command;

/**
 * 客户端操作
 * @author PJ_ZHANG
 * @create 2020-12-10 14:09
 **/
public class Client {

    public static void main(String[] args) {
        // 初始化请求接收者,即实际执行类
        Light light = new Light();
        // 初始化具体命令类,即命令
        Command lightOnCommand = new LightOnCommand(light);
        Command lightOffCommand = new LightOffCommand(light);
        // 初始化遥控器, 即请求发起者
        RemoteController remoteController = new RemoteController();
        // 绑定命令
        remoteController.setCommand("1", lightOnCommand, lightOffCommand);
        // 开灯
        remoteController.onCommand("1");
        // 关灯
        remoteController.offCommand("1");
        // 撤销
        remoteController.undoCommand();
        // 如果需要添加其他智能家居,
        // 只需要添加请求接收者和对应的具体命令类
        // 在初始化遥控器时设置为不同的type即可
    }

}
