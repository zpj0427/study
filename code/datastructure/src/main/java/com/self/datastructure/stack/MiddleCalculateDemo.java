package com.self.datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 中缀表达式实现计算器
 * 基本思路
 *  1, 定义两个栈对象进行数据存储, 分别为数字栈对象和符号栈对象, 数组栈对运算数据进行存储, 符号栈对操作符号进行存储
 *  2, 从左至右对数据依次入栈, 对应数据入对应栈
 *  3, 对操作服务进行优先级排序, 排序优先级为 ( > / > * > - > +
 *  4, 符号栈数据入栈时, 判断栈顶数据优先级是否大于当前优先级
 *  5, 如果小于或者等于当前优先级, 则数据依次入栈
 *  6, 如果大于当前优先级, 则先从符号栈弹出一个符号, 并从数字栈弹出两个数字进行计算, 计算完成后, 数字入栈并继续该操作直到符合第5步
 *  7, 对于带括号数据, 直接拎出括号部分进行计算并入栈
 *  8, 表达式遍历完成后, 依次弹出栈元素进行计算
 *  9, 暂时不考虑符号和小数
 * @author LiYanBin
 * @create 2020-01-13 9:06
 **/
public class MiddleCalculateDemo {

    public static void main(String[] args) {
        System.out.println("最终结果: " + calculate("4-(3+(2-1))"));;
    }

    public static int calculate(String permission) {
        // 表达式转数组, 按数字和符号位拆分, 暂不考虑负数
        String[] permissionArray = transformPermission(permission);
        // 数字栈
        Stack<Integer> numberStack = new Stack<>();
        // 符号栈
        Stack<String> operateStack = new Stack<>();
        // 依次获取元素进行处理
        for (int index = 0; index < permissionArray.length; index++) {
            try {
                String element = permissionArray[index]; // 获取当前元素
                if (element.matches("^[+-]?[0-9]+$")) {
                    numberStack.push(Integer.valueOf(element)); // 数字位直接入栈
                    continue;
                } else if (element.matches("^[+\\-*/]$")) {
                    // 入栈符号, 入栈时对符号优先级进行排序
                    dealOperatePriority(numberStack, operateStack, element);
                    continue;
                } else if (element.matches("^[(]$")) {
                    // 括号进行处理, 从当前括号获取到匹配的括号, 即最后一个括号, 拼接一个完整的表达式递归处理
                    int endIndex = findMatchRightBracket(permissionArray, index);
                    String newPermission = combineBracketPermission(permissionArray, index, endIndex);
                    int bracketResult = calculate(newPermission);
                    numberStack.push(bracketResult);
                    // index直接后移
                    index = endIndex;
                }
            } catch (Exception e) {
                System.out.println("发生错误: index: " + index);
                e.printStackTrace();
            }
        }
        // 全部入栈完成后, 弹栈
        return doCalculate(numberStack, operateStack);
    }

    // 对栈顶操作符与当前操作符进行优先级判断
    // 如果栈顶优先级大于当前优先级, 则先对栈顶优先级进行计算
    // 如果栈顶优先级小于当前优先级, 弹出现有数据进行计算, 计算后重复操作
    private static void dealOperatePriority(Stack<Integer> numberStack, Stack<String> operateStack, String operate) {
        if (operateStack.isEmpty()) {
            operateStack.push(operate); // 为空直接入库处理
            return;
        }
        // 获取栈顶操作符
        String preOperate = operateStack.peek();
        // 获取优先级
        if (higgerPriority(operate, preOperate)) {
            // 当前优先级高, 直接入栈
            operateStack.push(operate);
        } else {
            // 当前优先级低, 弹栈历史数据进行计算
            Integer number1 = numberStack.pop();
            Integer number2 = numberStack.pop();
            operateStack.pop(); // 弹出
            Integer result = doCalculate(number1, number2, preOperate);
            numberStack.push(result);
            // 计算完成后, 递归该操作
            dealOperatePriority(numberStack, operateStack, operate);
        }
    }

    // 判断优先级
    private static boolean higgerPriority(String operate, String preOperate) {
        int priorityCount = getPriorityCount(operate);
        int prePriorityCount = getPriorityCount(preOperate);
        return priorityCount >= prePriorityCount;
    }

    // 获取优先级代表的标志位
    private static int getPriorityCount(String operate) {
        int priorityCount = 0;
        switch (operate) {
            case "+": priorityCount = 1;
                break;
            case "-": priorityCount = 2;
                break;
            case "*": priorityCount = 3;
                break;
            case "/": priorityCount = 4;
                break;
        }
        return priorityCount;
    }

    // 最终计算栈数据
    private static int doCalculate(Stack<Integer> numberStack, Stack<String> operateStack) {
        Integer number1 = numberStack.pop();
        Integer number2 = numberStack.pop();
        String operate = operateStack.pop();
        // 计算数据
        Integer result = doCalculate(number1, number2, operate);
        numberStack.push(result);
        // 两个栈都不为空, 继续递归进行计算
        if (!numberStack.isEmpty() && !operateStack.isEmpty()) {
            result = doCalculate(numberStack, operateStack);
        }
        return result;
    }

    // 进行计算
    private static Integer doCalculate(Integer number1, Integer number2, String operate) {
        switch (operate) {
            case "+" :
                return number1 + number2;
            case "-" :
                return number2 - number1;
            case "*" :
                return number1 * number2;
            case "/" :
                return number2 / number1;
            default:
                throw new RuntimeException("运算符无效...");
        }
    }

    // 获取括号内有效数据
    private static String combineBracketPermission(String[] permissionArray, int index, int endIndex) {
        StringBuffer sb = new StringBuffer();
        for (index = index + 1; index < endIndex; index++) {
            sb.append(permissionArray[index]);
        }
        return sb.toString();
    }

    // 匹配该左括号对应的右括号
    private static int findMatchRightBracket(String[] permissionArray, int currIndex) {
        int matchingIndex = 0;
        // 获取到表达式组最后一个对应的), 为当前(的匹配括号
        for (currIndex = currIndex + 1; currIndex < permissionArray.length; currIndex++) {
            if (")".equals(permissionArray[currIndex])) {
                matchingIndex = currIndex;
            }
        }
        return matchingIndex;
    }

    // 转换表达式为数组形式, 方便后续操作
    public static String[] transformPermission(String permission) {
        List<String> lstPer = new ArrayList<>(10);
        char[] perArray = permission.toCharArray();
        StringBuffer sb = new StringBuffer();
        // 处理首元素带符号
        boolean isFirst = true;
        for (char data : perArray) {
            // 截取符号位
            if ((data >= '0' && data <= '9') || (String.valueOf(data).matches("^[+-]$") && isFirst)) {
                sb.append(data);
                isFirst = false;
            } else {
                // 数字位遍历完成, 入队列
                if (0 != sb.length()) {
                    lstPer.add(sb.toString());
                    sb.setLength(0);
                }
                // 关联入符号位
                lstPer.add(String.valueOf(data));
                if (String.valueOf(data).equals("(")) {
                    isFirst = true;
                }
            }
        }
        // 添加表达式最后一个数字元素
        // 最后一位如果为), 则sb长度为0, 不进行拼接
        if (0 != sb.length()) {
            lstPer.add(sb.toString());
        }
        System.out.println("表达式转数组后: " + lstPer);
        String[] permissionAarray = new String[lstPer.size()];
        for (int i = 0; i < lstPer.size(); i++) {
            permissionAarray[i] = lstPer.get(i);
        }
        return permissionAarray;
    }

}
