package com.self.designmode.facade;

/**
 * 外观模式, 外观类,
 * 抽取顶层接口进行统一管理
 * @author PJ_ZHANG
 * @create 2020-08-03 17:02
 **/
public class Facade {
    private Lamplight lamplight = null;
    private Projector projector = null;
    private Screen screen = null;
    public Facade() {
        lamplight = Lamplight.instance();
        projector = Projector.instance();
        screen = Screen.instance();
    }
    // 开始观影
    public void start() {
        screen.down();
        projector.open();
        lamplight.lightDown();
    }
    // 结束观影
    public void end() {
        screen.up();
        projector.close();
        lamplight.lightUp();
    }
}
