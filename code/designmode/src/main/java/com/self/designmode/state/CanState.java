package com.self.designmode.state;

import java.util.Random;

/**
 * 状态模式: 可以抽奖类
 * @author PJ_ZHANG
 * @create 2020-12-17 17:33
 **/
public class CanState implements State {

    @Override
    public void lessPoints(Activity activity) {
        throw new RuntimeException("状态不符合....");
    }

    @Override
    public boolean raffle(Activity activity) {
        if (!activity.getStateType().equals(StateTypeEnum.CAN.getStrValue())) {
            throw new RuntimeException("状态不符合....");
        }
        // 进行抽奖
        int data = new Random().nextInt(10);
        if (data == 0) {
            System.out.println("抽奖成功, 可以进行领奖");
            activity.setState(new ProvideState());
            return true;
        } else {
            System.out.println("抽奖失败, 继续花积分吧");
            activity.setState(new NonState());
            return false;
        }
    }

    @Override
    public void awards(Activity activity) {
        throw new RuntimeException("状态不符合....");
    }

    @Override
    public String getStateType() {
        return StateTypeEnum.CAN.getStrValue();
    }
}
