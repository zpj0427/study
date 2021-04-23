package com.self.datastructure.z_nowcoder.easy;

import java.util.Stack;

/**
 * NC76: 用两个栈模拟队列
 * 首先: Stack是先进后出, 队列是先进先出的
 * 所以通过Stack实现队列, 需要保证push到栈顶 但是pop需要从栈底开始
 * 这时候就需要第二个Stack, 在pop的时候, 将Stack1的数据先出栈再入栈, 就能从Stack2上实现队列的FIFO
 * 一次将Stack1的数据拷贝到Stack2, 如果Stack2里面数据 pop 完, 则触发再次拷贝
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 17:10
 **/
public class NC76_TwoStack2List {

    static Stack<Integer> stack1 = new Stack<Integer>();

    static Stack<Integer> stack2 = new Stack<Integer>();

    public static void main(String[] args) {
        push(1);
        push(2);
        push(3);
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
    }

    /**
     * 数据入栈, 直接推数据即可
     * @param node
     */
    public static void push(int node) {
        stack1.push(node);
    }

    /**
     * 模拟队列进行数据出栈
     * @return
     */
    public static int pop() {
        // 如果栈2以空, 将栈1的数据导入到栈2
        if (stack2.isEmpty()) {
            for (; !stack1.isEmpty(); ) {
                stack2.push(stack1.pop());
            }
        }
        // 因为栈是先进后出, 经过栈1到栈2导数据, 此时栈2栈顶数据即为先进数据
        // 直接弹数据, 先进数据先出, 满足队列要求
        return stack2.pop();
    }

}