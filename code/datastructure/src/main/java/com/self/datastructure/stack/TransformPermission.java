package com.self.datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 表达式转换, 中缀表达式转后缀表达式
 * @author LiYanBin
 * @create 2020-01-13 11:27
 **/
public class TransformPermission {

    public static void main(String[] args) {
        // 34*5+66/+3-+
        System.out.println(transformPermission("3*4+5+6/6-3+4"));;
    }

    // 转换
    public static String transformPermission(String permission) {
        // 字符串表达式转数据
        String[] permissionArray = MiddleCalculateDemo.transformPermission(permission);
        // 中缀表达式转后缀表达式
        return doTransformPermission(permissionArray);
    }

    //
    private static String doTransformPermission(String[] permissionArray) {
        if (0 == permissionArray.length) {
            return null;
        }
        // 数字栈
        Stack<String> numberStack = new Stack<>();
        // 符号栈
        Stack<String> operateStack = new Stack<>();
        // 遍历表达式数组, 进行处理
        for (int i = 0; i < permissionArray.length; i++) {
            // 数组直接入栈
            if (permissionArray[i].matches("^[+-]?[0-9]+$")) {
                numberStack.push(permissionArray[i]);
            } else if(permissionArray[i].matches("^\\($")) {
                // 左括号直接入栈
                operateStack.push(permissionArray[i]);
            } else if(permissionArray[i].matches("^\\)$")) {
                // 右括号,将最近的左括号前的数据, 移到数字栈
                moveElement(numberStack, operateStack);
            } else if(permissionArray[i].matches("^[+\\-*/]$")) {
                // 运算符号, 进行优先级判断
                while (operateStack.size() != 0 && !higgerPriority(permissionArray[i], operateStack.peek())) {
                    numberStack.push(operateStack.pop());
                }
                operateStack.push(permissionArray[i]);
            }
        }
        // 处理完成后, 弹出符号栈元素到数字栈
        for (;0 != operateStack.size(); numberStack.push(operateStack.pop()));
        // 返回表达式
        List<String> lstElement = new ArrayList<>(10);
        for (;0 != numberStack.size(); lstElement.add(numberStack.pop()));
        StringBuffer sb = new StringBuffer();
        for (int i = lstElement.size() - 1; i >= 0; i--) {
            // 加上一个中断位
            sb.append(lstElement.get(i) + "#");
        }
        return sb.subSequence(0, sb.length() - 1).toString();
    }

    // 判断优先级
    private static boolean higgerPriority(String operate, String preOperate) {
        int priorityCount = getPriorityCount(operate);
        int prePriorityCount = getPriorityCount(preOperate);
        return priorityCount > prePriorityCount;
    }

    // 获取优先级代表的标志位
    private static int getPriorityCount(String operate) {
        int priorityCount = 0;
        switch (operate) {
            case "+": priorityCount = 1;
                break;
            case "-": priorityCount = 1;
                break;
            case "*": priorityCount = 2;
                break;
            case "/": priorityCount = 2;
                break;
            // 括号不参与, 遇到括号直接入栈
            case "(": priorityCount = 0;
                break;
        }
        return priorityCount;
    }

    // 将符号栈数据迁移到数字栈
    private static void moveElement(Stack<String> numberStack, Stack<String> operateStack) {
        for (String currOperate = operateStack.pop(); !"(".equals(currOperate); numberStack.push(currOperate), currOperate = operateStack.pop());
    }

}
