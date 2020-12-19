package com.self.designmode.memento;

/**
 * 备忘录模式: 原型类
 * @author PJ_ZHANG
 * @create 2020-12-17 10:39
 **/
public class Origination {

    // 攻击力
    private int vit;

    // 防御力
    private int def;

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

    public Origination(int vit, int def) {
        this.vit = vit;
        this.def = def;
    }

    /**
     * 保存当前对象状态到备忘类
     * @return
     */
    public Memento saveMemento() {
        return new Memento(vit, def);
    }

    /**
     * 从备忘录中恢复状态
     * @param memento
     */
    public void recoverMemento(Memento memento) {
        this.def = memento.getDef();
        this.vit = memento.getVit();
    }

    public void show() {
        System.out.println("vit: " + this.vit + ", def: " + this.getDef());
    }

}
