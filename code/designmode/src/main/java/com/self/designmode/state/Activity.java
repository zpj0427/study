package com.self.designmode.state;

/**
 * 状态模式: 流程控制, 抽奖流程类
 * @author PJ_ZHANG
 * @create 2020-12-17 17:30
 **/
public class Activity {

    private State state;

    private int count;

    public Activity(int count) {
        this.count = count;
        state = new NonState();
    }

    /**
     * 扣除积分,兑换抽奖机会
     */
    public void lessPoints() {
        state.lessPoints(this);
    }

    /**
     * 抽奖
     */
    public boolean raffle() {
        return state.raffle(this);
    }

    /**
     * 发放奖品
     */
    public void awards() {
       state.awards(this);
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getStateType() {
        return state.getStateType();
    }

    public void subCount() {
        count--;
    }

    public int getCount() {
        return count;
    }

}
