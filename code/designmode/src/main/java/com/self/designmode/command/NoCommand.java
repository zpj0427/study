package com.self.designmode.command;

/**
 * @author PJ_ZHANG
 * @create 2020-12-10 14:02
 **/
public class NoCommand implements Command {

    @Override
    public void execute() {
        System.out.println("do nothing,,,");
    }

    @Override
    public void undo() {
        System.out.println("do nothing,,,");
    }

}
