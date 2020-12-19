package com.self.designmode.state;

/**
 * 状态模式: 抽奖成功, 发放奖品
 * @author PJ_ZHANG
 * @create 2020-12-17 17:33
 **/
public class ProvideState implements State {

    @Override
    public void lessPoints(Activity activity) {
        throw new RuntimeException("状态不符合....");
    }

    @Override
    public boolean raffle(Activity activity) {
        throw new RuntimeException("状态不符合....");
    }

    @Override
    public void awards(Activity activity) {
        if (!StateTypeEnum.PROVIDE.getStrValue().equals(activity.getStateType())) {
            throw new RuntimeException("状态不符合....");
        }
        System.out.println("发放奖品");
        activity.subCount();
        if (activity.getCount() <= 0) {
            activity.setState(new NonState());
        } else {
            activity.setState(new CompleteState());
        }
    }

    @Override
    public String getStateType() {
        return StateTypeEnum.PROVIDE.getStrValue();
    }
}
