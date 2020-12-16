package com.self.designmode.mediator;

/**
 * @author PJ_ZHANG
 * @create 2020-12-16 22:46
 **/
public abstract class Colleague {

    private Mediator mediator;

    private String name;

    public Colleague(String name) {
        this.name = name;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    abstract void sendMsg(String receiverType, String msg);

    abstract void receiverMsg(String senderType, String msg);

    abstract String getColleagueType();
}
