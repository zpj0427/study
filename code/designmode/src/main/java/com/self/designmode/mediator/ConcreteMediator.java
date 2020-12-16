package com.self.designmode.mediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 具体中介者类
 * @author PJ_ZHANG
 * @create 2020-12-16 22:47
 **/
public class ConcreteMediator extends Mediator {

    Map<String, Colleague> colleagueMap = new HashMap<>(16);

    @Override
    void registry(Colleague colleague) {
        if (!colleagueMap.containsKey(colleague.getColleagueType())) {
            colleagueMap.put(colleague.getColleagueType(), colleague);
            colleague.setMediator(this);
        }
    }

    @Override
    void relay(String senderType, String receiverType, String msg) {
        if (!colleagueMap.containsKey(senderType) || !colleagueMap.containsKey(receiverType)) {
            return;
        }
        colleagueMap.get(receiverType).receiverMsg(senderType, msg);
    }
}
