package com.self.designmode.state;

/**
 * 状态模式: 不能抽奖类
 * @author PJ_ZHANG
 * @create 2020-12-17 17:33
 **/
public class NonState implements State {

    @Override
    public void lessPoints(Activity activity) {
        if (!StateTypeEnum.NON.getStrValue().equals(activity.getStateType())) {
            return;
        }
        if (activity.getCount() <= 0) {
            throw new RuntimeException("兑换积分失败, 领完了...");
        }
        // 扣除积分即可以抽奖
        System.out.println("扣除了积分, 有了一次抽奖机会");
        // 变更状态为可抽奖
        activity.setState(new CanState());
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
        return StateTypeEnum.NON.getStrValue();
    }
}
