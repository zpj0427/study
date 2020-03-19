package com.self.datastructure.hash;

import lombok.Data;

import java.util.UUID;

/**
 * 自定义哈希表进行数据存储和查找
 * 也就是写一个简单的HashMap
 *
 * @author PJ_ZHANG
 * @create 2020-03-18 17:53
 **/
public class MyHashTable {

    public static void main(String[] args) {

    }

    /**
     * 哈希列表类, 进行数据操作
     * Node[] 数组
     * Node自身为链表
     * 整体数据结构为数组+链表
     */
    class SelfHash {

        // 默认长度
        private int DEFAULT_SIZE = 16;

        // 初始化长度
        private int size = DEFAULT_SIZE;

        private Node[] nodeArray;

        public SelfHash() {
            this.size = DEFAULT_SIZE;
            nodeArray = new Node[this.size];
        }

        public SelfHash(int size) {
            this.size = size;
            nodeArray = new Node[this.size];
        }

        /**
         * 存数据/改数据
         * @param key
         * @param value
         */
        public void put(String key, Employee value) {

        }

        /**
         * 取数据
         * @param key
         */
        public void get(String key) {

        }

        /**
         * 移除数据
         * @param key
         */
        public void remove(String key) {

        }

    }

    /**
     * 自定义Node
     * 存储键值对信息,
     * 存储链表信心
     */
    class Node {

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
    class Employee {

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
