package com.self.datastructure.hash;

import lombok.Data;
import lombok.ToString;

import java.util.Scanner;
import java.util.UUID;

/**
 * 自定义哈希表进行数据存储和查找
 * 也就是写一个简单的HashMap, 没有扩容和树转换逻辑
 *
 * @author PJ_ZHANG
 * @create 2020-03-18 17:53
 **/
public class MyHashTable {

    public static void main(String[] args) {
        SelfHash selfHash = new SelfHash();
        for (int i = 0; i < 100; i++) {
            selfHash.put(i + "", new Employee(i + "name", i + ""));
        }
        System.out.println("总数: " + selfHash.size());
        for (;;) {
            System.out.println("输入要删除的元素编号");
            Scanner scanner = new Scanner(System.in);
            String inputId = scanner.nextLine();
            System.out.println(selfHash.get(inputId));
            System.out.println(selfHash.remove(inputId));
            System.out.println(selfHash.get(inputId));
        }
    }

    /**
     * 哈希列表类, 进行数据操作
     * Node[] 数组
     * Node自身为链表
     * 整体数据结构为数组+链表
     */
    static class SelfHash {

        // 默认长度
        private static int DEFAULT_SIZE = 16;

        // 初始化长度
        private static int length = DEFAULT_SIZE;

        // 元素数量
        private static int size;

        // 数组
        // 数组中的每一个元素为链表
        private Node[] nodeArray;

        public SelfHash() {
            this(length);
        }

        public SelfHash(int size) {
            this.length = size;
            nodeArray = new Node[this.length];
        }

        /**
         * 存数据/改数据
         * @param key
         * @param value
         */
        public void put(String key, Employee value) {
            if (nodeArray == null) nodeArray = new Node[DEFAULT_SIZE];
            // 获取对应存储下标
            int targetIndex = key.hashCode() % length;
            // 为空, 说明元素不存在
            if (null == nodeArray[targetIndex]) {
                nodeArray[targetIndex] = new Node(key, value);
                size++;
            } else {
                // 获取到当前链表, 并获取链表最后一个元素
                Node node = nodeArray[targetIndex];
                Node preNode = node;
                for (;node != null;) {
                    if (node.getKey().equals(key)) {
                        node.setValue(value);
                        return;
                    }
                    preNode = node;
                    node = node.getNextNode();
                }
                // node为空, preNode表示最后一个元素
                // 将当前元素挂到该位置
                preNode.setNextNode(new Node(key, value));
                size++;
            }
        }

        /**
         * 取数据
         * @param key
         */
        public Employee get(String key) {
            int targetIndex = key.hashCode() % length;
            Node node = nodeArray[targetIndex];
            for (;null != node;) {
                if (key.equals(node.getKey())) {
                    return node.getValue();
                }
                node = node.getNextNode();
            }
            return null;
        }

        /**
         * 移除数据
         * @param key
         */
        public boolean remove(String key) {
            int targetIndex = key.hashCode() % length;
            Node node = nodeArray[targetIndex];
            Node preNode = node;
            for (;null != node;) {
                if (key.equals(node.getKey())) {
                    // 头结点, 当数组元素设置为下一个节点
                    if (preNode == node) {
                        nodeArray[targetIndex] = node.getNextNode();
                    } else { // 非头节点, 挂空当前节点
                        preNode.setNextNode(node.getNextNode());
                    }
                    return true;
                }
                preNode = node;
                node = node.getNextNode();
            }
            return false; // 移除失败
        }

        /**
         * 列表展示
         */
        public void showArray() {
            if (size == 0) {
                System.out.println("数据为空...");
                return;
            }
            for (int i = 0; i < length; i++) {
                Node node = nodeArray[i];
                for (;null != node;) {
                    System.out.println("Node: INDEX: " + i + ", " + node.getValue());
                    node = node.getNextNode();
                }
            }
        }

        public int size() {
            return size;
        }

        /**
         * 获取数组长度
         * @return
         */
        public int length() {
            return length;
        }

    }

    /**
     * 自定义Node
     * 存储键值对信息,
     * 存储链表信心
     */
    @Data
    static class Node {

        private String key;

        private Employee value;

        private Node nextNode;

        public Node() {}

        public Node(String key, Employee value) {
            this(key, value, null);
        }

        public Node(String key, Employee value, Node nextNode) {
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }

    }

    /**
     * 员工类, 实体数据
     * 存储到数据表时, 基本格式为{id, Employee}
     */
    @Data
    @ToString
    static class Employee {

        String id;

        String name;

        public Employee() {}

        public Employee(String name) {
            this(UUID.randomUUID().toString().replaceAll("-", ""), name);
        }

        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }

    }

}
