package com.self.designmode.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求发起者, 即万能遥控,
 * @author PJ_ZHANG
 * @create 2020-12-10 13:55
 **/
public class RemoteController {

    private Map<String, Command> onCommandMap = new HashMap<>(16);

    private Map<String, Command> offCommandMap = new HashMap<>(16);

    private Command undoCommand;

    /**
     * 初始化开关
     * @param type
     * @param onCommand
     * @param offCommand
     */
    public void setCommand(String type, Command onCommand, Command offCommand) {
        onCommandMap.put(type, onCommand);
        offCommandMap.put(type, offCommand);
    }

    /**
     * 打开开关命令
     * @param type
     */
    public void onCommand(String type) {
        Command onCommand = null == onCommandMap.get(type) ? new NoCommand() : onCommandMap.get(type);
        onCommand.execute();
        undoCommand = onCommand;
    }

    /**
     * 关闭开关命令
     * @param type
     */
    public void offCommand(String type) {
        Command offCommand = null == offCommandMap.get(type) ? new NoCommand() : offCommandMap.get(type);
        offCommand.execute();
        undoCommand = offCommand;
    }

    /**
     * 撤销开关命令
     */
    public void undoCommand() {
        undoCommand.undo();
    }

}
