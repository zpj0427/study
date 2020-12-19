package com.self.designmode.state;

/**
 * 状态模式, 客户端
 * @author PJ_ZHANG
 * @create 2020-12-17 17:54
 **/
public class Client {

    public static void main(String[] args) {
        Activity activity = new Activity(1);
        // 抽10次, 可能抽奖失败
        for (int i = 0; i < 10; i++) {
            activity.lessPoints();
            boolean result = activity.raffle();
            if (result) {
                activity.awards();
            }
        }
    }

}
