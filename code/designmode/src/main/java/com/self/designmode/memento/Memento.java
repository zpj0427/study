package com.self.designmode.memento;

/**
 * 备忘录模式: 备忘类
 * @author PJ_ZHANG
 * @create 2020-12-17 10:39
 **/
public class Memento {

    // 攻击力
    private int vit;

    // 防御力
    private int def;

    public Memento(int vit, int def) {
        this.vit = vit;
        this.def = def;
    }

    public Memento(Origination origination) {
        this.vit = origination.getVit();
        this.def = origination.getDef();
    }

    public int getVit() {
        return vit;
    }

    public void setVit(int vit) {
        this.vit = vit;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
