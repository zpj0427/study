package com.self.designmode.state;

/**
 * 状态模式: 奖品发放完成类
 * @author PJ_ZHANG
 * @create 2020-12-17 17:33
 **/
public class CompleteState implements State {

    @Override
    public void lessPoints(Activity activity) {
        System.out.println("奖品发放完成...");
    }

    @Override
    public boolean raffle(Activity activity) {
        throw new RuntimeException("状态不符合....");
    }

    @Override
    public void awards(Activity activity) {
        throw new RuntimeException("状态不符合....");
    }

    @Override
    public String getStateType() {
        return StateTypeEnum.COMPLETE.getStrValue();
    }
}
