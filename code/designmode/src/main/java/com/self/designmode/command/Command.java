package com.self.designmode.command;

/**
 * 命令顶层接口
 * @author PJ_ZHANG
 * @create 2020-12-10 13:50
 **/
public interface Command {

    /**
     * 执行操作
     */
    void execute();

    /**
     * 撤销操作
     */
    void undo();

}
