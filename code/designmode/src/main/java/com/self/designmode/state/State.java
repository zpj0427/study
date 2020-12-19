package com.self.designmode.state;

/**
 * 状态模式: 顶层状态类接口
 * @author PJ_ZHANG
 * @create 2020-12-17 17:26
 **/
public interface State {

    /**
     * 扣除积分
     * @param activity
     */
    void lessPoints(Activity activity);

    /**
     * 抽奖
     * @param activity
     */
    boolean raffle(Activity activity);

    /**
     * 发放奖品类
     * @param activity
     */
    void awards(Activity activity);

    /**
     * 获取状态类型值
     * @return
     */
    String getStateType();
}
