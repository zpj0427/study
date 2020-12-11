package com.self.designmode.command;

/**
 * 具体命令, 对应某种功能
 * 打开电灯
 * @author PJ_ZHANG
 * @create 2020-12-10 13:54
 **/
public class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}
