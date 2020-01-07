package com.self.datastructure.queue;

import java.util.Scanner;

/**
 * @author LiYanBin
 * @create 2020-01-07 15:34
 **/
public class MyQueue {

    // 初始化长度
    private final int DEFAULT_COUNT = 8;

    // 数组
    private int[] array;

    // 数组长度
    private int capacity;

    // 计数器
    private int totalCount;

    // 读索引, 读索引指向数组数据的当前位置, 即读取当前索引的值
    private int readIndex = 0;

    // 写索引, 写索引指向数据数据的后一个位置, 即写数据到当前索引
    private int writeIndex = 0;

    // 初始化
    public MyQueue(int count) {
        initMyQueue(count);
    }

    public void initMyQueue(int count) {
        count = count <= 0 ? DEFAULT_COUNT : count;
        capacity = count;
        array = new int[count];
    }

    // 是否为空
    public boolean isEmpty() {
        // 写指向实际数据前一个位置
        // 当读 + 1 = 写的时候, 说明已经空了
        return (readIndex + 1) % capacity == writeIndex;

    }

    // 是否已经满了
    public boolean isFull() {
        // 写指向实际数据前一个位置
        // 当写 == 读时, 说明数据已经写满了
        return readIndex == writeIndex;
    }

    // 写数据
    public boolean putData(int data) {
        if (null == array) {
            initMyQueue(DEFAULT_COUNT);
        }
        if (isFull()) {
            throw new IndexOutOfBoundsException("数据满了...");
        } else {
            array[writeIndex++] = data;
            totalCount++;
            // 如果指向尾部, 则循环执行0索引
            writeIndex = writeIndex % capacity;
        }
        return true;
    }

    // 读数据
    public int readData() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("没有数据...");
        }
        int data = array[readIndex++];
        totalCount--;
        readIndex = readIndex % capacity;
        return data;
    }

    public void showDetails() {
        for (int i : array) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    // 获取总数
    public int getTotalCount() {
        return totalCount;
    }

    public void showProp() {
        System.out.println("read: " + readIndex + ", write: " + writeIndex);
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue(2);
        Scanner scanner = new Scanner(System.in);
        boolean eventloop = true;
        for (;eventloop;) {
            System.out.println("s(show): 展示");
            System.out.println("w(write): 增加");
            System.out.println("r(read): 读取");
            System.out.println("e(exit): 退出");
            System.out.println("t(total): 获取总数");
            String input = scanner.nextLine();
            switch (input) {
                case "s" :
                    myQueue.showDetails();
                    myQueue.showProp();
                    break;
                case "w" :
                    System.out.println("请输入数据");
                    int data = scanner.nextInt();
                    myQueue.putData(data);
                    myQueue.showProp();
                    break;
                case "r" :
                    System.out.println("获取到的数据: " + myQueue.readData());
                    myQueue.showProp();
                    break;
                case "t" :
                    System.out.println("当前数组总数: " + myQueue.getTotalCount());
                    myQueue.showProp();
                    break;
                case "e" :
                    System.out.println("退出...");
                    myQueue.showProp();
                    eventloop = false;
                    break;
            }
        }
    }

}
