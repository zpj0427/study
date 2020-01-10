package com.self.datastructure.stack;

/**
 * 通过数组模拟栈数据结构
 * @author LiYanBin
 * @create 2020-01-10 14:53
 **/
public class ArrayStackDemo {

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(10);
        for (int i = 0; i < 10; i++, arrayStack.push(i));
        arrayStack.showDetails();
    }

    private static class ArrayStack {
        private int count; // 数组有效数据数量

        private int capacity; // 数组总长度

        private int[] stack; // 底层数组

        private static final int DEFAULT_LENGTH = 10;

        public ArrayStack() {
            this(DEFAULT_LENGTH);
        }

        public ArrayStack(int length) {
            this.capacity = length;
            this.stack = new int[length];
        }

        // 添加数据, 添加到数组尾部
        public void push(int data) {
            if (isFull()) {
                throw new IndexOutOfBoundsException("数组已满...");
            }
            stack[count++] = data;
        }

        // 弹出数据, 从数组尾部弹出
        public int pop() {
            if (isEmpty()) {
                throw new IndexOutOfBoundsException("数组为空...");
            }
            return stack[--count];
        }

        // 判断数组是否已满
        public boolean isFull() {
            return count == capacity;
        }

        // 判断数组是否为空
        public boolean isEmpty() {
            return count == 0;
        }

        // 栈遍历, 从栈顶开始遍历
        public void showDetails() {
            int tempCount = count;
            for (;tempCount > 0;) {
                System.out.println(stack[--tempCount]);
            }
        }

        public int size() {
            return count;
        }
    }

}
