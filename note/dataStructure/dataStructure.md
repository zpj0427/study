# 1，概述

## 1.1，数据结构和算法的介绍

* 数据结构（data structure）是一门研究组织数据方式的学科，有了编程语言也就有了数据结构，使用数据结构可以编写更有效率的代码
* 程序 = 数据结构 + 算法
* 数据结构是算法的基础，要学号算法，一定要学好数据结构

## 1.2，线性结构和非线性结构

### 1.3.1，线性结构

* 数据元素之间存在一对一的线性关系
* 线性结构有两种不同的存储结构，即顺序存储结构（*数组*）和链式存储结构（*链表*）
* 顺序存储：即顺序表，顺表表中的元素是连续的
* 链式存储：元素不一定是连续的，元素节点中存放数据元素已经相邻元素的地址信息
* 线性结构常见的有：数组，队列，列表和栈

### 1.3.2，非线性结构

* 非线性结构包括：二维数组，多维数组，广义表，树结构和图结构

# 2，稀疏（SparseArray）数组

## 2.1，实际问题分析

* 在编写五子棋程序时，需要进行存盘和读盘操作，对棋盘现状进行保存，因为该二维数组的很多值是默认值0，所以直接通过棋盘的二维数组记录可能会记录很多没有意义的数据，此时可以使用稀疏数组进行存储。如图：

  ![1578293402952](E:\gitrepository\study\note\image\dataStructure\1578293402952.png)

## 2.2，基本介绍

* 当一个数组中大部分元素为0，或者为某一固定值，可以使用稀疏数组保存该数组

* 稀疏数组首先会记录该二维数组**一共有几行，几列，有多少个值**

* 之后把具有不同值的元素的行列及值记录在一个小规模的数组中，从而**缩小**数组的规模

  ![1578295549242](E:\gitrepository\study\note\image\dataStructure\1578295549242.png)
  * 如上图：稀疏数组第一个元素（**[0]**）表示当前二组数组有几行，几列及多少个值。也就是是棋盘元素汇总
  * 后续节点是对每一个棋盘元素解读，包括所在行，列及元素对应值

## 2.3，稀疏数组和二维数组互转思路

* 二维数组转稀疏数组
  * 遍历原始的二维数组，得到有效数据个数 `count`
  * 根据 `count` 就可以创建稀疏数组 `sparseArr int[count+1][3]`
  * 将二维数组的有效数据存入到稀疏数组
* 稀疏数组转二维数据
  * 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如棋盘图示的`int[11][11]`
  * 继续读取稀疏数组后几行的数据，并赋给原始的二维数组即可

## 2.4，代码示例

* 内存处理

```java
package com.self.datastructure.sparsearray;

/**
 * 稀疏数组
 * @author LiYanBin
 * @create 2020-01-06 16:07
 **/
public class SparseArray {

    public static void main(String[] args) {
        // 初始化棋盘数组
        int[][] array = new int[11][11];
        // 添加可旗子, 1为黑旗 2位白旗
        array[0][1] = 1;
        array[1][2] = 2;
        array[7][8] = 2;
        System.out.println("初始化数据.........");
        printArray(array);
        // 数组转换为稀疏数组
        System.out.println("转换的稀疏数组为........");
        int[][] sparseArray = arrayToSparse(array);
        printArray(sparseArray);
        // 稀疏数组转换为数据
        System.out.println("稀疏数组转换的数组为........");
        int[][] newArray = sparseToArray(sparseArray);
        printArray(newArray);
    }

    /**
     * 数组转稀疏数组
     */
    public static int[][] arrayToSparse(int[][] array) {
        // 遍历二维数组, 获取有效数据
        int sum = 0;
        for (int[] currArray : array) {
            for (int data : currArray) {
                if (data != 0) {
                    sum++;
                }
            }
        }
        // 初始化稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        // 填充第一行, 即统计行
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        // 填充后续行, 即元素行
        // 填充稀疏数组
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (0 != array[i][j]) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = array[i][j];
                }
            }
        }
        return sparseArray;
    }

    /**
     * 稀疏数组转数组
     */
    public static int[][] sparseToArray(int[][] sparseArray) {
        // 解析稀疏数组第一行, 初始化二维数组
        int[][] array = new int[sparseArray[0][0]][sparseArray[0][1]];
        int sum = sparseArray[0][2];
        for (int i = 1; i <= sum; i++) {
            // 稀疏数组二维三列,
            // 第一列表示横坐标
            // 第二列表示纵坐标
            // 第三列表示值
            array[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return array;
    }

    public static void printArray(int[][] array) {
        for (int[] currArray : array) {
            for (int data : currArray) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }
    }

}
```

* 持久化磁盘处理

```java
package com.self.datastructure.sparsearray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 稀疏数组
 * @author LiYanBin
 * @create 2020-01-06 16:07
 **/
public class SparseArrayWithDisk {

    public static void main(String[] args) throws  Exception {
        // 初始化棋盘数组
        int[][] array = new int[11][11];
        // 添加可旗子, 1为黑旗 2位白旗
        array[0][1] = 1;
        array[1][2] = 2;
        array[7][8] = 2;
        System.out.println("初始化数据.........");
        printArray(array);
        // 数组转换为稀疏数组
        System.out.println("转换的稀疏数组为........");
        int[][] sparseArray = arrayToSparse(array);
        printArray(sparseArray);
        // 将稀疏数据写到磁盘
        writeSparseArray(sparseArray);
        // 从磁盘读取稀疏数据
        sparseArray = readSparseArray();
        // 稀疏数组转换为数据
        System.out.println("稀疏数组转换的数组为........");
        int[][] newArray = sparseToArray(sparseArray);
        printArray(newArray);
    }

    private static int[][] readSparseArray() {
        try {
            FileInputStream fileInputStream = new FileInputStream("E:\\1.txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String json = new String(bytes);
            JSONArray jsonArray = JSON.parseArray(json);
            int[][] sparseAarray = new int[jsonArray.size()][3];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray currJsonArray = JSON.parseArray(jsonArray.get(i).toString());
                sparseAarray[i][0] = Integer.parseInt(currJsonArray.get(0).toString());
                sparseAarray[i][1] = Integer.parseInt(currJsonArray.get(1).toString());
                sparseAarray[i][2] = Integer.parseInt(currJsonArray.get(2).toString());
            }
            return sparseAarray;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeSparseArray(int[][] sparseArray) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\1.txt");
            fileOutputStream.write(JSON.toJSONString(sparseArray).getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数组转稀疏数组
     */
    public static int[][] arrayToSparse(int[][] array) {
        // 遍历二维数组, 获取有效数据
        int sum = 0;
        for (int[] currArray : array) {
            for (int data : currArray) {
                if (data != 0) {
                    sum++;
                }
            }
        }
        // 初始化稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        // 填充第一行, 即统计行
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        // 填充后续行, 即元素行
        // 填充稀疏数组
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (0 != array[i][j]) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = array[i][j];
                }
            }
        }
        return sparseArray;
    }

    /**
     * 稀疏数组转数组
     */
    public static int[][] sparseToArray(int[][] sparseArray) {
        // 解析稀疏数组第一行, 初始化二维数组
        int[][] array = new int[sparseArray[0][0]][sparseArray[0][1]];
        int sum = sparseArray[0][2];
        for (int i = 1; i <= sum; i++) {
            // 稀疏数组二维三列,
            // 第一列表示横坐标
            // 第二列表示纵坐标
            // 第三列表示值
            array[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return array;
    }

    public static void printArray(int[][] array) {
        for (int[] currArray : array) {
            for (int data : currArray) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }
    }

}
```

# 3，队列

## 3.1，队列介绍

* 队列是一个有序列表，可以用数组或者链表来实现
* 队列遵循先入先出（FIFO）原则

![1578381708571](E:\gitrepository\study\note\image\dataStructure\1578381708571.png)

## 3.2，数组模拟队列

* 队列本身是有序列表，若使用数组的结构存储队列，则队列的声明入上图第一小图
* 因为队列的输入和输出是分别从两端处理，所以需要两个对应的索引数据`write`和`read`，而`write`和`read` 随着数据数据的输入和输出移动
* 在添加数据时，需要考虑的问题
  * 数组是否已经初始化，如果没有初始化，初始化为默认长度，比如10
  * 判断`写索引 + 写数据 > 读索引`，成立则数组越界
  * 写索引到索引末尾后，应该重置为0继续写入，如果此时读索引位置未变，即没有读事件，则数据越界
* 在取数据时，需要考虑的问题
  * 写索引与读索引是否相等，相等则没有数据
  * 读索引读取到末尾后，继续读0位置，实现循环处理

## 3.3，代码实现

