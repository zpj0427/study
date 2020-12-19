package com.self.designmode.composite;

/**
 * 组合模式:客户端
 * @author PJ_ZHANG
 * @create 2020-07-29 12:51
 **/
public class Client {
    public static void main(String[] args) {
        OrgComponent university = new OneComposite("学校", "挺好");
        OrgComponent college = new TowComposite("学院", "挺不错");
        university.add(college);
        OrgComponent leaf = new Leaf("专业", "挺棒");
        college.add(leaf);
        university.print();
    }
}
