package com.self.designmode.command;

/**
 * 具体命令, 对应某种功能
 * 关闭电灯
 * @author PJ_ZHANG
 * @create 2020-12-10 13:54
 **/
public class LightOffCommand implements Command {

    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
