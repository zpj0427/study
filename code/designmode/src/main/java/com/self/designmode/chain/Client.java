package com.self.designmode.chain;

/**
 * 职责链模式: 客户端
 * @author PJ_ZHANG
 * @create 2020-12-18 11:40
 **/
public class Client {

    public static void main(String[] args) {
        // 创建一个请求
        Request request = new Request(1500000);
        // 构建职责链
        DepartmentApprover departmentApprover = new DepartmentApprover("主任");
        CollegeApprover collegeApprover = new CollegeApprover("院长");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("校长");
        departmentApprover.setNextApprover(collegeApprover);
        collegeApprover.setNextApprover(schoolMasterApprover);
        // 这部分为了构成环状, 不让请求走空, 这部分可以走一个空实现
        schoolMasterApprover.setNextApprover(departmentApprover);
        // 处理, 从主任开始
        departmentApprover.processRequest(request);
    }

}
