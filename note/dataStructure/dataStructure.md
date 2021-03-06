# 1，概述

## 1.1，数据结构和算法的介绍

* 数据结构（data structure）是一门研究组织数据方式的学科，有了编程语言也就有了数据结构，使用数据结构可以编写更有效率的代码
* 程序 = 数据结构 + 算法
* 数据结构是算法的基础，要学好算法，一定要学好数据结构

## 1.2，线性结构和非线性结构

### 1.3.1，线性结构

* 数据元素之间存在一对一的线性关系
* 线性结构有两种不同的存储结构，即顺序存储结构（*数组*）和链式存储结构（*链表*）
* 顺序存储：即顺序表，顺序表中的元素是连续的
* 链式存储：元素不一定是连续的，元素节点中存放数据元素以及相邻元素的地址信息
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
  * 根据 `count` 就可以创建稀疏数组 `int[][] sparseArr = new int[count+1][3]`
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

* 队列本身是有序列表，若使用数组的结构存储队列，则队列的声明如上图
* 因为队列的输入和输出是分别从两端处理，所以需要两个对应的索引数据`write`和`read`，而`write`和`read` 随着数据的输入和输出移动
* 在添加数据时，需要考虑的问题
  * 数组是否已经初始化，如果没有初始化，初始化为默认长度，比如10
  * 判断`写索引 + 写数据 > 读索引`，成立则数组越界
  * 写索引到索引末尾后，应该重置为0继续写入，如果此时读索引位置未变，即没有读事件，则数据越界
* 在取数据时，需要考虑的问题
  * 写索引与读索引是否相等，相等则没有数据
  * 读索引读取到末尾后，继续读0位置，实现循环处理

## 3.3，代码实现

```java
package com.self.datastructure.queue;

import java.lang.annotation.Target;
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

    // 有效数据
    private int totalCount;

    // 读索引, 读索引指向数组数据的当前位置, 即读取当前索引的值
    private int readIndex = 0;

    // 写索引, 写索引指向数据的后一个位置, 即写数据到当前索引
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
        return totalCount == 0;
    }

    // 是否已经满了
    public boolean isFull() {
        return totalCount == capacity;
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

    // 遍历需要从读位置遍历到写的位置
    public void showDetails() {
        // 条件是从读索引开始, 读取有效数据个数
        System.out.print("totalCount: " + totalCount + "\t");
        for (int i = readIndex; i < readIndex + getTotalCount(); i++) {
            System.out.print(array[i % capacity] + ", ");
        }
        System.out.println();
    }

    // 获取有效数据总数
    public int getTotalCount() {
        // 参考图示
        return totalCount;
    }

}
```

# 4，链表（Linked List）

## 4.1，单向链表

### 4.1.1，单向链表基本介绍

* 链表是以节点的方式存储
* 链表的每个节点都包括`data`域和`next`域，指向下一个节点
* 链表的各个节点不一定都是连续存储的
* 链表分为带头结点的链表和不带头结点的链表

### 4.1.2，单向链表的逻辑示意图

![1578469277971](E:\gitrepository\study\note\image\dataStructure\1578469277971.png)

### 4.1.3，单向链表应用实例

* 创建一个单向链表，并完成对该链表的增删改查

* 此处演示基本带头节点的单向链表

  ```java
  package com.self.datastructure.linked;
  
  import lombok.Data;
  
  /**
   * @author LiYanBin
   * @create 2020-01-08 14:06
   **/
  public class MyLinkedList {
  
      public static void main(String[] args) {
          SimpleLinkedList linkedList = new SimpleLinkedList();
          linkedList.addByOrder(new LinkedNode(2, "名称", "昵称"));
          linkedList.addByOrder(new LinkedNode(1, "名称", "昵称"));
          linkedList.addByOrder(new LinkedNode(4, "名称", "昵称"));
          linkedList.addByOrder(new LinkedNode(3, "名称", "昵称"));
          linkedList.update(new LinkedNode(1, "覆盖", "覆盖"));
          linkedList.delete(new LinkedNode(3, "", ""));
          linkedList.showDetails();
      }
  
  }
  
  class SimpleLinkedList {
  
      // 初始化头结点
      private LinkedNode head = new LinkedNode(-1, "", "", null);
  
      // 添加数据, 直接添加不带过滤
      public void add(LinkedNode linkedNode) {
          // 赋值头节点到临时节点
          LinkedNode temp = head;
          while (null != temp.getNext()) {
              temp = temp.getNext();
          }
          // 将尾节点的下一个节点指向当前节点
          temp.setNext(linkedNode);
      }
  
      // 添加数据, 按照主键排序
      public void addByOrder(LinkedNode linkedNode) {
          // 赋值头几点到临时节点
          LinkedNode temp = head;
          while (null != temp.getNext()) {
              LinkedNode nextNode = temp.getNext();
              // 进行升序排列
              if (nextNode.getNo() > linkedNode.getNo()) {
                  temp.setNext(linkedNode);
                  linkedNode.setNext(nextNode);
                  return;
                  // 相同直接进行修改
              } else if (nextNode.getNo() == linkedNode.getNo()) {
                  temp.setNext(linkedNode);
                  linkedNode.setNext(nextNode.getNext());
                  nextNode.setNext(null); // 辅助GC
                  return;
              }
              temp = nextNode;
          }
          // 将尾节点的下一个节点指向当前节点
          temp.setNext(linkedNode);
      }
  
      // 修改数据
      public void update(LinkedNode linkedNode) {
          LinkedNode temp = head;
          while (null != temp) {
              LinkedNode next = temp.getNext();
              if (null != next && next.getNo() == linkedNode.getNo()) {
                  temp.setNext(linkedNode);
                  linkedNode.setNext(next.getNext());
                  next.setNext(null); // 辅助GC
                  return;
              }
              temp = next;
          }
      }
  
      // 删除节点, 根据编号删除
      public void delete(LinkedNode linkedNode) {
          LinkedNode temp = head;
          while (null != temp.getNext()) {
              LinkedNode next = temp.getNext();
              // 找到该节点, 并把该节点的next节点指向上一个节点的next, 把该节点挂空
              if (next.getNo() == linkedNode.getNo()) {
                  temp.setNext(next.getNext());
                  next.setNext(null); // 辅助GC
                  return;
              }
              temp = next;
          }
      }
  
      // 展示详情信息
      public void showDetails() {
          LinkedNode next = head.getNext();
          while (next != null) {
              System.out.println(next);
              next = next.getNext();
          }
      }
  
  }
  
  @Data
  class LinkedNode {
  
      // 编号, 编号为主键, 不允许重复, 并需要顺序处理
      private int no;
  
      // 名称
      private String name;
  
      // 昵称
      private String nickName;
  
      // 单项链表
      private LinkedNode next;
  
      public LinkedNode(int no, String name, String nickName) {
          this(no, name, nickName, null);
      }
  
      public LinkedNode(int no, String name, String nickName, LinkedNode next) {
          this.no = no;
          this.name = name;
          this.nickName = nickName;
          this.next = next;
      }
  
      @Override
      public String toString() {
          return "[LinkedNode: no = " + no + ", name = " + name + ", nickName = " + nickName + "]";
      }
  
  }
  ```

### 4.1.4，单向链表面试题

* 获取单链表的节点个数

  ```java
  /**
   * 获取单向链表中有效节点的数量
   * @param head 头节点
   * @param isNeedHead 头节点是否是有效节点
   * @return
   */
  public static int getNodeLength(LinkedNode head, boolean isNeedHead) {
      if (null == head) {
          return 0;
      }
      // 需要头节点直接初始化为1，表示头结点已经统计
      int count = isNeedHead ? 1 : 0;
      LinkedNode temp = head;
      for (;null != temp.getNext();) {
          count++;
          temp = temp.getNext();
      }
      return count;
  }
  ```

* 查找单向链表中的倒数第K个节点：即获取正数第（count - K）个节点的数据

  ```java
/**
   * 获取单链表中的倒数第K个节点
   * 索引从0开始算, 即倒是第K个节点就是正数第(length - k)
   * @param head 单向链表头结点
   * @param isNeedHead 头结点是否是有效节点
   * @param descCount 倒数位置
   * @return
   */
  public static LinkedNode getIndexDataDesc(LinkedNode head, boolean isNeedHead, int descCount) {
  	// 获取数量
  	int count = getNodeLength(head, isNeedHead);
  	if (count < descCount) {
  		throw new IndexOutOfBoundsException("索引越界");
  	}
  	int index = count - descCount;
  	LinkedNode temp = isNeedHead ? head : head.getNext();
  	for (int i = 0; i < index; i++, temp = temp.getNext());
  	return temp;
  }
  ```
  
* 单链表反转

  ```java
  /**
   * 单向链表反转
   * 每次获取下一个节点, 并重新构造该节点为头节点
   * 把之前存储的头节点置位该节点的next节点, 即一步步遍历, 一步步后推
   * @param head 单向链表
   * @param isNeedHead 头节点是否是有效节点
   * @return
   */
  public static LinkedNode reverseLinked(LinkedNode head, boolean isNeedHead) {
  	// 获取有效数据
  	LinkedNode realData = isNeedHead ? head : head.getNext();
  	// 初始化返回数据, 不需要头节点的直接初始化虚拟节点
  	LinkedNode reverseLinked = isNeedHead ? null : new LinkedNode(head);
  	// 反转
  	for (; null != realData ;) {
  		// 构造新节点
  		LinkedNode newNode = new LinkedNode(realData);
  		if (null == reverseLinked) {
  			reverseLinked = newNode;
  		} else {
  			// 获取下一个节点
  			// 非虚拟头节点
  			if (isNeedHead) {
  				newNode.setNext(reverseLinked);
  				reverseLinked = newNode;
  			} else { // 虚拟头节点
  				// 获取虚拟头节点的下一个节点
  				LinkedNode nextNode = reverseLinked.getNext();
  				// 把原节点挂到该节点下
  				newNode.setNext(nextNode);
  				// 把当前节点设为下一个节点
  				reverseLinked.setNext(newNode);
  			}
  		}
  		realData = realData.getNext();
  	}
  	return reverseLinked;
  }
  ```

* 反向打印链表

  ```java
  /**
   * 从尾节点开始打印链表
   * 方式1:
   *     先将单链表进行翻转操作, 然后进行遍历即可
   *     该方式可能会改变原链表结构, 不建议
   *     该方式可以直接调用反转方法, 并打印
   * 方式2:
   *     利用栈数据结构, 将各个节点压入栈中
   *     利用栈先进后出的特点, 完成逆序打印
   * @param linkedNode 节点
   * @param isNeedHead 头结点是否是有效节点
   */
  public static void showDetailsReverse(LinkedNode linkedNode, boolean isNeedHead) {
  	Stack<LinkedNode> stack = new Stack<>();
  	LinkedNode realNode = isNeedHead ? linkedNode : linkedNode.getNext();
  	if (null == realNode) {
  		return;
  	}
  	// 遍历节点, 添加到栈中
  	for (; null != realNode; ) {
  		stack.push(realNode);
  		realNode = realNode.getNext();
  	}
  	// 打印栈对象
  	LinkedNode currNode = null;
  	for (;stack.size() > 0; ) {
  		System.out.println(stack.pop());
  	}
  }
  ```

* 合并两个有序的单链表，合并之后的链表依然有序

  ```java
  /**
   * 合并两个有序(顺序)链表, 默认不需要头结点
   * @param firstNode
   * @param secondNode
   */
  public static LinkedNode mergeOrderNode(LinkedNode firstNode, LinkedNode secondNode) {
  	// 获取有效节点
  	firstNode = firstNode.getNext();
  	secondNode = secondNode.getNext();
  	// 存在为空, 直接返回
  	if (null == firstNode || null == secondNode) {
  		return null == firstNode ? secondNode : firstNode;
  	}
  	// 比较节点数据
  	// 用首节点编号较小的链表进行遍历,
  	// 较大编号的链表进行填充, 最终返回有效节点
  	return firstNode.getNo() > secondNode.getNo()
  			? doMergeOrderNode(secondNode, firstNode)
  			: doMergeOrderNode(firstNode, secondNode);
  }
  
  public static LinkedNode doMergeOrderNode(LinkedNode firstNode, LinkedNode secondNode) {
  	// 初始化头节点
  	SimpleLinkedList simpleLinkedList = new SimpleLinkedList();
  	// 遍历节点进行填充
  	for (;null != firstNode;) {
  		// first节点数据大于second节点数据, 将second节点数据置于之前
  		for (;secondNode != null && firstNode.getNo() > secondNode.getNo();) {
  			simpleLinkedList.add(new LinkedNode(secondNode));
  			// 当前second已经被比较过, 向前推动一位
  			secondNode = secondNode.getNext();
  		}
  		// 处理完成当前区间的seconde数据后, 添加first数据
  		simpleLinkedList.add(new LinkedNode(firstNode));
  		firstNode = firstNode.getNext();
  	}
  	// first节点遍历完成后, 如果second节点还存在数据, 全部添加到最后
  	for (;null != secondNode;) {
  		simpleLinkedList.add(new LinkedNode(secondNode));
  		secondNode = secondNode.getNext();
  	}
  	return simpleLinkedList.getHead();
  }
  ```

## 4.2，双向链表

### 4.2.1，双向链表介绍

* 单向链表查找方向是能是一个方向；双向链表可以向前或者向后查找
* 单向链表不能自我删除，需要靠辅助节点即前一个节点；双向链表可以进行自删除，因为同时持有上一个节点和下一个节点的地址
* **遍历**：遍历方式与单向链表基本一致，不过双向链表可以直接从后往前遍历
* **添加**：
  * 先找到需要插入到双向链表的目标节点
  * 讲节点和对应前置节点与后置节点的`pre`和`next`属性进行修改
* **删除**：
  * 双向链表可以实现单个节点的自删除，直接遍历到需要删除的当前节点
  * 将当前节点前置节点的`next`属性指向当前节点的后置节点
  * 将当前节点后置节点的`pre`属性指向当前节点的前置节点
  * 处理之后，当前节点在链表中会挂空，也就是从链表中删除

### 4.2.2，双向链表代码演示

```java
package com.self.datastructure.linked;

import lombok.Data;

/**
 * 双向链表
 * @author LiYanBin
 * @create 2020-01-09 14:21
 **/
public class DoubleLinkedList {

    public static void main(String[] args) {
        DLinkedList dLinkedList = new DLinkedList();
//        dLinkedList.add(new DoubleNode(0, "名称", "昵称"));
//        dLinkedList.add(new DoubleNode(1, "名称", "昵称"));
//        dLinkedList.add(new DoubleNode(2, "名称", "昵称"));
//        dLinkedList.add(new DoubleNode(3, "名称", "昵称"));

        dLinkedList.addByOrder(new DoubleNode(3, "名称", "昵称"));
        dLinkedList.addByOrder(new DoubleNode(2, "名称", "昵称"));
        dLinkedList.addByOrder(new DoubleNode(4, "名称", "昵称"));
        dLinkedList.addByOrder(new DoubleNode(1, "名称", "昵称"));
        dLinkedList.showDetailsFromHead();
    }

}

class DLinkedList {

    private DoubleNode head = new DoubleNode(-1, "", "");

    private DoubleNode tail = new DoubleNode(-2, "", "");

    public DLinkedList() {
        head.setNext(tail);
        tail.setPre(head);
    }

    // 根据序列号添加
    public void addByOrder(DoubleNode doubleNode) {
        // 获取有效数据
        DoubleNode realData = head.getNext();
        for (;null != realData;) {
            // 遍历到尾节点, 直接添加
            if (realData == tail) {
                add(doubleNode);
                return;
            }
            // 编号大于当前编号, 则置于该编号之前
            if (realData.getNo() > doubleNode.getNo()) {
                DoubleNode pre = realData.getPre();
                pre.setNext(doubleNode);
                doubleNode.setPre(pre);
                doubleNode.setNext(realData);
                realData.setPre(doubleNode);
                return;
            }
            realData = realData.getNext();
        }
    }

    // 添加数据到链表中, 默认添加到链表尾部
    public void add(DoubleNode doubleNode) {
        // 获取尾节点的上一个节点
        DoubleNode preNode = tail.getPre();
        // 将上一个节点置为该节点的前置节点,
        preNode.setNext(doubleNode);
        // 添加该节点的前置节点和后置节点
        doubleNode.setPre(preNode);
        doubleNode.setNext(tail);
        // 将tail节点的前置节点改为该节点
        tail.setPre(doubleNode);
    }

    // 删除指定数据
    public void remove(int no) {
        DoubleNode realData = head.getNext();
        for (;null != realData && tail != realData;) {
            // 匹配到数据, 直接移除
            if (realData.getNo() == no) {
                // 获取前置节点
                DoubleNode pre = realData.getPre();
                // 获取后置节点
                DoubleNode next = realData.getNext();
                // 互为前置后置节点, 将当前节点挂空
                pre.setNext(next);
                next.setPre(pre);
                realData = null; // 辅助GC
                return;
            }
            realData = realData.getNext();
        }
    }

    // 打印, 从头打印
    public void showDetailsFromHead() {
        DoubleNode realData = head.getNext();
        for (;null != realData && tail != realData;) {
            System.out.println(realData);
            realData = realData.getNext();
        }
    }

    // 打印, 从尾部打印
    public void showDetailsFromTail() {
        DoubleNode realData = tail.getPre();
        for (;null != realData && realData != head;) {
            System.out.println(realData);
            realData = realData.getPre();
        }
    }

    public DoubleNode getHead() {
        return head;
    }

    public DoubleNode getTail() {
        return tail;
    }

}

@Data
class DoubleNode {

    private DoubleNode pre;

    private DoubleNode next;

    private int no;

    private String name;

    private String nickName;

    public DoubleNode(DoubleNode doubleNode) {
        this(doubleNode.getNo(), doubleNode.getName(), doubleNode.getNickName());
    }

    public DoubleNode(int no, String name, String nickName) {
        this(no, name, nickName, null, null);
    }

    public DoubleNode(int no, String name, String nickName, DoubleNode pre, DoubleNode next) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
        this.pre = pre;
        this.next = next;
    }

    @Override
    public String toString() {
        return "[DoubleNode: no = " + no + ", name = " + name + ", nickName = " + nickName + "]";
    }

}
```

## 4.3，单向环形链表：约瑟夫问题（Josephu）

### 4.3.1，Josephu（约瑟夫）问题

* 设编号为1，2 ... n的n个人围成一个圈，并约定编号为K（1<=K<=n）的人从1开始报数，数到M的那个人出列，他的下一位又从1开始数，依次类推，直到所有人出列
* 通过不带头节点的单向链表来处理

### 4.3.2，Josephu（约瑟夫）问题实现代码

```java
package com.self.datastructure.linked;

import lombok.Data;

import java.util.Scanner;

/**
 * 约瑟夫问题解决代码
 * @author LiYanBin
 * @create 2020-01-09 15:45
 **/
public class JosephuQuestion {

    public static void main(String[] args) {
        // 初始化游戏玩家
        JosephuLinkedList linkedList = new JosephuLinkedList();
        for (int i = 0; i < 10; i++) {
            linkedList.add(new Node(i));
        }
        linkedList.startGame();
    }

}

class JosephuLinkedList {

    // 该头节点表示有效节点
    private Node head = null;

    // 统计总数
    private int totalCount = 0;

    // 开始游戏
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        // 默认从头号玩家开始
        Node startNode = head;
        for(;;) {
            if (totalCount == 0) {
                System.out.println("编号全部移除, 游戏结束...");
                return;
            }
            System.out.println("当前游戏人数: " + totalCount);
            String input = scanner.nextLine();
            switch (input) {
                case "s": // show
                    showDetails();
                    break;
                case "r": // run
                    System.out.println("输入跳过的人数: M");
                    int runCount = scanner.nextInt();
                    // 移除数据
                    // 从1号开始数两位, 则移除3号, 下一次从4号开始玩
                    // 此处获取要移除数据的前一位
                    Node targetPreNode = startNode;
                    for (int i = 0; i < runCount - 1; i++, targetPreNode = targetPreNode.getNext());
                    // 记录, 获取移除数据的下一个数据, 此处需要两次next
                    startNode = targetPreNode.getNext().getNext();
                    System.out.println("移除完成..., 移除编号: " + targetPreNode.getNext().getNo() + ", 下次开始编号: " + startNode.getNo());
                    // 直接移除, 将该节点的next 指向目标节点的next, 将目标节点挂空
                    targetPreNode.setNext(targetPreNode.getNext().getNext());
                    totalCount--;
                    // 移除, 此处不需要调移除, 因为节点已经挂空
                    // remove(targetNode.getNo());
                    break;
                case "e": //exit
                    return;
                default:
                    break;
            }
        }
    }

    // 添加元素
    public void add(Node node) {
        // 设置为头结点
        if (null == head) {
            totalCount++;
            head = node;
            head.setNext(head);
            return;
        }
        // 与头节点进行比较
        Node temp = head;
        for (;null != temp;) {
            // 从头节点开始, 每次获取到下一个节点进行判断
            Node next = temp.getNext();
            if (head == next) { // 与头节点相等, 说明已经到有效链表末尾
                totalCount++;
                temp.setNext(node);
                node.setNext(head);
                return;
            }
            temp = temp.getNext();
        }
    }

    public void remove(int no) {
        if (null == head) return;
        // 头结点处理
        if (head.getNo() == no) {
            // 链表只有当前数据, 直接清空
            if (head == head.getNext()) {
                head = null;

            } else {
                // 链表存在其他数据, 移除头节点后遍历
                Node preHead = head;
                head = head.getNext();
                Node temp = head;
                for (; null != temp; ) {
                    // 再遇头节点
                    if (preHead == temp.getNext()) {
                        temp.setNext(head);
                        break;
                    }
                    temp = temp.getNext();
                }
            }
        } else { // 非头节点处理
            Node temp = head;
            for (;null != temp;) {
                Node next = temp.getNext();
                // 无论是否存在, 再遇头结点直接移除
                if (next == head) {
                    return;
                }
                // 存在, 直接移除
                if (next.getNo() == no) {
                    temp.setNext(next.getNext());
                    break;
                }
                temp = temp.getNext();
            }
        }
        totalCount--;
    }

    public void showDetails() {
        if (null == head) {
            System.out.println("数据为空...");
            return;
        }
        Node temp = head;
        boolean flag = false;
        for (;null != temp;) {
            if (flag && temp == head) {
                System.out.println("遍历完成...");
                return;
            }
            flag = true;
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    public Node getHead() {
        return head;
    }

}

@Data
class Node {

    // 编号
    private int no;

    // 下一个节点
    private Node next;

    public Node(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "[Node: no = " + no + "]";
    }

}
```

# 5，栈（Stack）

## 5.1，栈基本介绍

* 栈是一种先入后出的有序列表

* 栈限制线性表中**元素的插入和删除只能在线性表的同一端**进行的一种特殊线性表。允许插入和删除的一端，为变化的一端，称为栈顶，另一端为固定的一端，称为栈底

* 出栈（`pop`）入栈（`push`）原理图如下：

  ![1578638346672](E:\gitrepository\study\note\image\dataStructure\1578638346672.png)

## 5.2，栈模拟代码演示

* 数组模拟

```java
package com.self.datastructure.stack;

import lombok.Data;

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

    @Data
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
    }

}
```

* 链表模拟

```java
package com.self.datastructure.stack;

import lombok.Data;

/**
 * 通过单向链表模拟栈数据结构
 * @author LiYanBin
 * @create 2020-01-10 15:15
 **/
public class LinkedStackDemo {

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack();
        for (int i = 0; i < 10; i++, linkedStack.push(i));
        for (int i = 0; i < 10; i++)
            System.out.println(linkedStack.pop().getData());
    }


    private static class LinkedStack {

        // 头结点, 该几点不虚拟
        private Node head;

        // 添加链表节点到栈中
        public void push(int data) {
            Node newNode = new Node(data);
            if (null == head) {
                head = newNode;
            } else {
                Node temp = head;
                // 获取到最后一个有效节点
                for (;null != temp.getNext(); temp = temp.getNext());
                temp.setNext(newNode);
            }
        }

        public Node pop() {
            // 节点为空处理
            if (isEmpty()) {
                throw new IndexOutOfBoundsException("链表为空...");
            }
            // 只存在头节点处理
            Node temp = head;
            if (null == head.getNext()) {
                head = null;
                return temp;
            } else {
                // 获取到尾节点的上一个节点
                for (temp = head; temp.getNext().getNext() != null; temp = temp.getNext());
                // 获取的temp表示要获取节点的前置节点
                // 返回目标节点, 并将前置节点的next为空置空
                Node resultData = temp.getNext();
                temp.setNext(null);
                return resultData;
            }
        }

        public boolean isEmpty() {
            return head == null;
        }


    }

    /**
     * 自定义链表
     */
    @Data
    private static class Node {
        private int data;

        private Node next;

        public Node(int data) {
            this(data, null);
        }

        public Node(Node node) {
            this(node.getData(), null);
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

}
```

## 5.3，栈模拟计算器

### 5.3.1，中缀表达式模拟计算器

#### 5.3.1.1，基本思路

* 定义两个栈对象进行数据存储，分别为数字栈对象和符号栈对象
  * 数组栈对运算数据进行存储
  * 符号栈对操作符号进行存储
* 从左至右对数据依次入栈, 对应数据入对应栈
* 对操作服务进行优先级排序, 排序优先级为 `( > / > * > - > +`
* 符号栈数据入栈时, 判断栈顶数据优先级是否大于当前优先级
  * 如果小于或者等于当前优先级, 则数据依次入栈
  * 如果大于当前优先级, 则先从符号栈弹出一个符号, 并从数字栈弹出两个数字进行计算, 计算完成后, 数字入栈并继续该操作直到符合优先级小于
* 对于带括号数据, 直接拎出括号部分进行计算完成后并入栈
* 表达式遍历完成后, 依次弹出栈元素进行计算
* 全部基于整数操作，未考虑小数

#### 5.3.1.2，代码示例

```java
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
    private static String[] transformPermission(String permission) {
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
```

### 5.3.2，中缀表达式转后缀表达式

#### 5.3.2.1，转换规则

1. 初始化两个栈，运算符栈`s1`和数据元素栈`s2`

2. 从左至右扫描中缀表达式

3. 遇到数据元素时，直接压入到`s2`栈

4. 遇到运算符时，比较其与`s1`栈顶运算符的优先级

* 如果`s1`栈为空，或者栈顶运算符为左括号`(`，则直接将运算符入栈
* 如果当前运算符优先级比栈顶运算符优先级高，则将运算符直接压入`s1`
* 如果当前运算符优先级小于等于栈顶运算符，则将符号栈`s1`的栈顶运算符弹出并压入到数据元素栈`s2`中，并重复该动作比较下一个栈顶运算符

5. 遇到括号时

* 如果是左括号`(`，则直接压入`s1`，包括左括号处理及栈顶为左括号场景
* 如果是右括号`)`，则依次弹出`s1`栈顶的运算符，并压入到`s2`，直到遇到左括号为止，此时可以将这一对括号丢弃

6. 重复步骤2到步骤5，直到扫描到表达式尾部
7. 将`s1`中剩余的运算符依次弹出并压入`s2`
8. 逆序输出`s2`中的元素并拼接为表达式，即为中缀表达式的后缀表达式

#### 5.3.2.2，转换举例说明

* 中缀表达式`1+((2+3)*4)-5`转换为`123+4*+5-`

![1578897191548](E:\gitrepository\study\note\image\dataStructure\1578897191548.png)

#### 5.3.2.3，示例代码

```java
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
        System.out.println(transformPermission("10+((20+30)*40)-50"));;
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
            // 数字直接入栈
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
        return sb.toString();
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
```

### 5.3.3，后缀表达式模拟计算器

#### 5.3.3.1，思路分析

* 从左至右扫描后缀表达式（逆波兰表达式）
* 对扫描的数字位压栈处理，继续向后扫描
* 扫描到运算符时，从栈中弹出两个数字位，并根据运算符进行对应操作
* 计算完成后，将计算结果压栈并进行后续处理

#### 5.3.3.2，代码变现

```java
package com.self.datastructure.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 后缀表达式进行计算器计算
 * @author LiYanBin
 * @create 2020-01-13 14:40
 **/
public class SuffixCalculateDemo {

    public static void main(String[] args) {
        // 转换后的后缀表达式: 34+5*6-
        // 每一位之间添加中断符#
        String permission = TransformPermission.transformPermission("10+((20+30)*40)-50");
        System.out.println(calculate(permission));
    }

    // 后缀表达式计算
    public static int calculate(String suffixPermission) {
        // 对表达式进行list转换
        List<String> lstElement = Arrays.asList(suffixPermission.split("#", -1));
        Stack<Integer> numberStack = new Stack<>();
        for (String element : lstElement) {
            if (element.matches("^[+-]?[0-9]+$")) {
                numberStack.push(Integer.valueOf(element));
            } else if (element.matches("^[+\\-*/]$")) {
                doCalculate(numberStack, element);
            }
        }
        // 全部计算完成后, 弹出结果
        return numberStack.pop();
    }

    // 计算
    private static void doCalculate(Stack<Integer> numberStack, String operate) {
        if (numberStack.size() < 2) {
            throw new RuntimeException("表达式有误...");
        }
        // 栈后入先出, 所以应该用 number2 [operate] number1
        Integer number1 = numberStack.pop();
        Integer number2 = numberStack.pop();
        numberStack.push(getResult(number2, number1, operate));
    }

    private static Integer getResult(Integer firstNumber, Integer secondNumber, String operate) {
        switch (operate) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                return firstNumber / secondNumber;
        }
        throw new RuntimeException("操作符无效...");
    }

}
```

# 6，递归

## 6.1，递归需要遵守的重要规则

* 执行一个方法时，就创建一个新的受保护的独立空间（JVM栈）
* 方法的局部变量是独立的，不会相互影响
* 方法中使用的是引用类型变量，则会基于地址共享发生改变
* 递归必须向退出递归的条件逼近，否则就是无限递归，最终栈溢出`StackOverflowError`
* 当一个方法执行完毕，或者遇到return，就会返回，遵守谁调用，就将结果返回给谁，同时当结果返回时，该方法也执行完毕

## 6.2，迷宫问题

* 注意该路径不是最短路径！！！

```java
package com.self.datastructure.recursion;

/**
 * 递归_迷宫问题处理
 * 1, 迷宫二维地图初始化状态为0, 1表示墙壁, 2表示已经走过的路, 3表示死路
 * 2, 约定迷宫走路策略: 下 -> 右 -> 上 -> 左, 逆时针尝试走
 * @author LiYanBin
 * @create 2020-01-13 16:46
 **/
public class MazeDemo {

    public static void main(String[] args) {
        // 初始化迷宫, 初始化二维地图
        int[][] map = initMap(8, 8);
        // 初始化地图
        showDetails(map);
        // 从指定坐标开始找路, 抵达目的地
        boolean hasWay = runWay(map, 1, 1, 6, 6);
        showDetails(map);
    }

    /**
     * 迷宫走路
     * @param map 地图
     * @param currFirstIndex 当前位置的一维坐标
     * @param currSecondIndex 当前位置的二维坐标
     * @param targetFirstIndex 目标位置的一维坐标
     * @param targetSecondIndex 目标位置的二维坐标
     * @return
     */
    private static boolean runWay(int[][] map, int currFirstIndex, int currSecondIndex,
                                  int targetFirstIndex, int targetSecondIndex) {
        System.out.println("打印当前地图");
        showDetails(map);
        // 0表示初始化地图, 即未走
        // 1表示墙壁或者障碍, 走不通
        // 2表示已走
        // 3表示死路
        // 目标节点以走到, 则返回true, 并顺序返回
        if (currFirstIndex == targetFirstIndex && currSecondIndex == targetSecondIndex) {
            map[targetFirstIndex][targetSecondIndex] = 2;
            return true;
        } else { // 当前节点不是目标节点, 则继续迷宫探路
            // 为0表示未走过， 为2表示回溯
            if (map[currFirstIndex][currSecondIndex] == 0 || map[currFirstIndex][currSecondIndex] == 2) {
                // 修改为2, 表示已经走过
                map[currFirstIndex][currSecondIndex] = 2;
                // 先进行未知区域探索
                // 向下
                if (runUnkownArea(map, currFirstIndex + 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向右
                if (runUnkownArea(map, currFirstIndex, currSecondIndex + 1, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向上
                if (runUnkownArea(map, currFirstIndex - 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向下
                if (runUnkownArea(map, currFirstIndex, currSecondIndex - 1, targetFirstIndex, targetSecondIndex))
                    return true;
                // 未知区域探索完成, 无路可走, 当前节点置位无效
                map[currFirstIndex][currSecondIndex] = 3;
                // 进行节点回溯, 继续探索
                // 向下
                if (runkownArea(map, currFirstIndex + 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向右
                if (runkownArea(map, currFirstIndex, currSecondIndex + 1, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向上
                if (runkownArea(map, currFirstIndex - 1, currSecondIndex, targetFirstIndex, targetSecondIndex))
                    return true;
                // 向下
                if (runkownArea(map, currFirstIndex, currSecondIndex - 1, targetFirstIndex, targetSecondIndex))
                    return true;
            } else { // 不为0或者2, 表示是墙壁或者死路
                return false;
            }
        }
        return false;
    }

    // 区域回溯
    private static boolean runkownArea(int[][] map, int nextFirstIndex, int nextSecondIndex, int targetFirstIndex, int targetSecondIndex) {
        if (map[nextFirstIndex][nextSecondIndex] != 3
                && runWay(map, nextFirstIndex, nextSecondIndex, targetFirstIndex, targetSecondIndex)) {
            return true;
        }
        return false;
    }

    // 探索未知区域
    private static boolean runUnkownArea(int[][] map, int nextFirstIndex, int nextSecondIndex, int targetFirstIndex, int targetSecondIndex) {
        if (map[nextFirstIndex][nextSecondIndex] == 0
                && runWay(map, nextFirstIndex, nextSecondIndex, targetFirstIndex, targetSecondIndex)) {
            return true;
        }
        return false;
    }

    /**
     * 初始化地图
     * @param firstIndexCount 一位长度
     * @param secondIndexCount 二维长度
     * @return
     */
    private static int[][] initMap(int firstIndexCount, int secondIndexCount) {
        // 初始化地图, 此时地图全部状态为0
        int[][] map = new int[firstIndexCount][secondIndexCount];
        // 为地图设置围墙,
        // 设置上下,
        // [firstIndexCount - 1, 0], [0, secondIndexCount - 1]
        // [firstIndexCount - 1, secondIndexCount - 1]
        for (int i = 0; i < firstIndexCount; i++) {
            map[i][0] = 1;
            map[i][secondIndexCount - 1] = 1;
        }
        // 设置左右,
        // [0, 0] -> [0, secondIndexCount - 1],
        // [firstIndexCount - 1, 0] -> [firstIndexCount - 1, secondIndexCount - 1]
        for (int i = 0; i < secondIndexCount; i++) {
            map[0][i] = 1;
            map[firstIndexCount - 1][i] = 1;
        }
        // 设置障碍
        map[3][1] = 1;
        map[3][2] = 1;
        map[2][2] = 1;
        map[4][4] = 1;
        map[5][4] = 1;
        map[6][4] = 1;
        return map;
    }

    public static void showDetails(int[][] map) {
        for (int[] firstIndexData : map) {
            for (int secondIndexData : firstIndexData) {
                System.out.print(secondIndexData + " ");
            }
            System.out.println();
        }
    }

}
```

## 6.3，八皇后问题

![1578984111041](E:\gitrepository\study\note\image\dataStructure\1578984111041.png)

### 6.3.1，需求概述

* 在8*8的二维桌面上，每一行摆放一枚棋子作为一个皇后

* 任意两个皇后不能处于同一行，同一列或者同一斜线上

### 6.3.2，思路分析

* 第一个皇后放第一行第一列
* 第二个皇后放第二行第一列，然后判断该位置是否OK，否则继续放在第二列，第三列...，依次往后放，找到一个合适的位置
* 继续第三个皇后，依次类推，最终找到一个合适解
* 当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放在第一列的所有正确解，全部得到
* 之后继续将第一个皇后放到第二列，继续全步骤循环，理论上会有92种结果
* 皇后位置理论上应该用二维数组来表示，但是可以使用一维数组来实现，通过索引表示一维行号，索引上的值表示二维位置，来实现二维位置

### 6.3.2，代码

```java
package com.self.datastructure.recursion;

/**
 * 递归_八皇后问题处理
 * @author LiYanBin
 * @create 2020-01-14 14:40
 **/
public class QueueDemo {

    private final static int MAX_COUNT = 8;

    private final static int[] QUEUE_SORT = new int[MAX_COUNT];

    public static void main(String[] args) {
        run(0); // 从0开始摆放, 总共有92中结果
    }

    /**
     * 进行八皇后递归运算
     * @param n 行号
     */
    public static void run(int n) {
        // 序列从0开始, n为最大值说明皇后已经位置摆放完毕, 直接输出
        if (n == MAX_COUNT) {
            showDetails();
            return;
        }
        // 此处n表示行号, 因为摆放棋盘是 MAX_COUNT * MAX_COUNT 的二维棋盘
        // n表示行号, 即从该行开始摆放
        for (int i = 0; i < MAX_COUNT; i++) {
            // 此处表示把皇后摆在第 n 行的 第 i 个索引上
            // 初始化时候, 第一个皇后摆在[0, 0]的位置,
            QUEUE_SORT[n] = i;
            // 摆放完成后, 判断该位置是否与其他位置冲突
            if (judge(n)) { // 为true表示不冲突
                // 不冲突时, 继续进行下一个皇后摆放
                run(n + 1);
            }
            // 冲突, 把当前皇后位置后移一位, 通过i++表示
        }
    }

    // 判断当前摆放位置是否合适
    // 不能在同一行, 一维数组表示, 这部分不存在
    // 不能在同一列, 即数组中不能有重复的值
    // 不能在同一斜线, 即 -> |索引 - 索引| != |索引值 - 索引值|
    public static boolean judge(int n) {
        // 遍历数组, 只遍历当前数组存在元素的部分
        // 即 n 以前的元素
        for (int i = 0; i < n; i++) {
            // QUEUE_SORT[i] == QUEUE_SORT[n] 表示在同一列
            // Math.abs(i - n) == Math.abs(QUEUE_SORT[i] - QUEUE_SORT[n]) 表示在同一斜线
            // 斜线算法可以根据正方形对角线考虑
            if (QUEUE_SORT[i] == QUEUE_SORT[n] || Math.abs(i - n) == Math.abs(QUEUE_SORT[i] - QUEUE_SORT[n])) {
                return false;
            }
        }
        return true;
    }

    // 打印组合方式
    public static void showDetails() {
        for (int data : QUEUE_SORT) {
            System.out.print(data + "  ");
        }
        System.out.println();
    }

}
```





# 7，排序算法

## 7.1，常见排序分类

* 内部排序（内存排序）
  * 插入排序
    * 直接插入排序
    * 希尔排序
  * 选择排序
    * 简单选择排序
    * 堆排序
  * 交换排序
    * 冒泡排序
    * 快速排序
  * 归并排序
  * 基数排序
* 外部排序（内存和外存结合）

## 7.2，时间复杂度

* **时间频度**：一个算法花费的时间与算法中语句的执行次数成正比例，哪个算法中语句的执行次数多，它花费的时间就多。一个算法中语句的执行次数称为语句频度或者时间频度，记为T(n)。在数量及运算下，时间频度公式的常量项，低次方项，以及系数项都是可以忽略的，时间频度的悬殊差距由高次方决定

### 7.2.1，时间复杂度基本介绍

* 一般情况下，算法中的基本操作语句的重复执行次数是问题规模n的某个函数，用`T(n)`表示，若有某个辅助函数`f(n)`，使得当n趋近于无穷大的时候，`T(n)/f(n)`的极限值不等于零的情况，则称`f(n)`是`T(n)`的同数量级函数，记做`T(n)=O(f(n))`，称`O(f(n))`为算法的渐进时间复杂度，简称**时间复杂度**
* 时间复杂度算法
  * 用常数1代替运行时间中的所有加法常数，为了区别出`O(1)`
  * 修改后的运行次数函数中，只保留最高阶项
  * 去除最高阶项的系数

### 7.2.2，常见的时间复杂度

* 常数阶`O(1)`：无论代码执行了多少行，只要没有循环复杂结构，那么这个的时间复杂度就是`O(1)`

  ```java
  /**
   * O(1) 时间复杂度
   * 没有循环结构的顺序执行, 无论执行多少行, 时间复杂度均为O(1)
   */
  public static void o1() {
  	int i = 0;
  	int j = 0;
  	i++;
  	j++;
  	System.out.println(i + j);
  }
  ```

* 对数阶`O(log2n)`

  ```java
  /**
   * O(log2n) 时间复杂度
   * 此处 i 以二倍的速度增长, 也就是说到 2^n 后趋近于count, 整个过程执行log2n次
   */
  public static void log2n(int count) {
  	for (int i = 1; i <= count; i *= 2);
  }
  ```

* 线性阶`O(n)`

  ```java
  /**
   * O(n) 线性阶, 即代码循环次数随count的变化成线性变化
   */
  public static void n(int count) {
  	for (int i = 0; i < count; i++) {
  		System.out.println(i);
  	}
  }
  ```

* 线性对数阶`O(nlog2n)`：线性阶与对数阶的嵌套

  ```java
  /**
   * O(nlog2n) 线程对数阶, 线性阶与对数阶的嵌套
   */
  public static void nlog2n(int count) {
  	// 线性阶
  	for (int i = 0; i < count; i++) {
  		// 对数阶
  		int j = 0;
  		while (j < count) {
  			j *= 2;
  		}
  	}
  }
  ```

* 平方阶`O(n^2)`：双层线性循环嵌套

  ```java
  /**
   * O(n2) 平方阶, 就是双层线性循环嵌套
   */
  public static void n2(int count) {
  	// 线性阶
  	for (int i = 0; i < count; i++) {
  		// 线性阶
  		for (int j = 0; j < count; i++) {
  			System.out.println(i + j);
  		}
  	}
  }
  ```

* 立方阶`O(n^3)`：三层线性循环嵌套

  ```java
  /**
   * O(n3) 立方阶, 就是三层线性循环嵌套
   */
  public static void n3(int count) {
  	// 线性阶
  	for (int z = 0; z < count; z++) {
  		// 线性阶
  		for (int i = 0; i < count; i++) {
  			// 线性阶
  			for (int j = 0; j < count; j++) {
  				System.out.println(z + i + j);
  			}
  		}
  	}
  }
  ```

* K次方阶`O(n^k)`：参考二阶和三阶，即K次的线程循环嵌套

* 指数阶`O(2^n)`

* 算法复杂度的优先级顺序

  ```
  O(1) < O(log2n) < O(n) < O(nlog2n) < O(n^2) < O(n^3) < O(n^k) <  O(2^n)
  ```

### 7.2.3，平均时间复杂度和最坏时间复杂度

* **平均时间复杂度**是指所有可能的输入实例均以等概率出现的情况下，该算法的运行时间

* **最坏时间复杂度**是最快情况下的时间复杂度，也是一般讨论的时间复杂度。是指任何输入实例的运行时间上限，这样保证算法时间不会比最坏情况更长

* 平均时间复杂度与最坏时间复杂度是否一致，主要参考算法

  ![1579070052700](E:\gitrepository\study\note\image\dataStructure\1579070052700.png)

## 7.3，空间复杂度

* 空间复杂度是指一个算法所耗费的存储空间，也是问题规模n的函数
* 空间复杂度是对一个算法在运行过程中临时占用存储空间大小的量度。有的算法需要占用的临时工作单元数与解决问题的规模n有关，随着n的增大而增大，当n较大时，将占用较多的存储单元，比如快速排序和归并排序、基数排序等
* 在做算法分析时，主要讨论的是时间复杂度，**用空间换时间**



## 7.4，冒泡排序

### 7.4.1，基本介绍

* 冒泡排序是对数组的第一个元素开始，从前往后（从最小下标开始）依次比较相邻两个元素的值，若发现逆序则进行交换，使得较大的元素逐渐往后移动
* 优化：在排序过程中，可以设置标志位，如果存在某一趟比较没有发生元素的位置互换，则说明数组已经有序，直接退出，无需再进行后续比较
* 冒泡排序的时间复杂度为`O(n^2)`

### 7.4.2，代码演示

```java
package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 排序_冒泡排序
 */
public class BubbleSortDemo {

    public static void main(String[] args) {
        // int[] array = {9, -1, 4, -2, 3, -5, 8};
        // int[] array = {1, 2, 3, 4, 6, 5, 7, 8};
        // 10万个数测试，测试结果为20S
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        bubbleSort(array);
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 冒泡排序
    private static void bubbleSort(int[] array) {
        // 外循环表示跑的趟数, 总趟数为 (长度 - 1)
        for (int i = 0; i < array.length - 1; i++) {
            // 定义标志位, 如果已经有序排列, 不再执行后续逻辑
            boolean flag = true;
            // 内循环进行冒泡比较
            // 内循环从0开始, 循环 (array.length - i - 1) 次
            // 外循环每跑一趟, 固定一个最大数到右侧, 内循环比较不会再次比较最大数
            // 内循环比较实用当前数与后一个数进行比较, 所以需要额外减1, 保证数组边界
            // 用当前数和后一个数进行比较, 如果当前数大于后一个数, 则进行交换
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // 存在变更, 设置标志位为false
                    flag = false;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            // 已经顺序化, 直接退出
            if (flag) {
                break;
            }
        }
    }

}
```

## 7.5，选择排序

### 7.5.1，基本介绍

* 选择排序也属于内部排序，是从欲排序的数据中，按指定的规则选出某一元素，再依规定交换位置已达到排序的目的
* 选择排序的基本思想是：从第一个数开始，依次与后面的数进行比较，选出最小的数并进行位置交换，之后进行第二个和后续所有数据的比较和交换，以此类推。通过`length - 1`次后，获取到一个从小到大排列的有序数组
* 选择排序的时间复杂度为`O(n^2)`。大数据量下，相对于冒泡排序性能会好很多，主要是因为选择排序比冒泡排序内部交换更少

![1579158369247](E:\gitrepository\study\note\image\dataStructure\1579158369247.png)

### 7.5.2，代码演示

```java
package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 选择排序
 * @author PJ_ZHANG
 * @create 2020-01-15 14:36
 **/
public class SelectSort {

    public static void main(String[] args) {
        // int[] array = {10, 8, 3, 9, 2, 6, -1};
        // 10万个数测试，测试结果为5S
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        selectSort(array);
        // System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 选择排序处理
    // 从一个数开始, 依次与后面所有的数比较, 并将当前数替换为最小数, 并在头部固定
    // 全部处理完成后, 会形成一个顺序数组
    private static void selectSort(int[] array) {
        // 从第一个元素开始比较, 比较到倒数第二个元素, 最后一个元素无需自比较
        for (int i = 0; i < array.length - 1; i++) {
            int temp = array[i]; // 默认取当前值为最小值
            int minIndex = i; // 最小值索引
            // 内循环从外循环的后一个元素开始算起, 进行元素比较, 一直比较到最后一个元素
            for (int j = i + 1; j < array.length; j++) {
                // 比较获取到最小值
                if (temp > array[j]) {
                    temp = array[j]; // 最小值
                    minIndex = j; // 最小值索引
                }
            }
            // 循环完成后, 如果存在小于该元素的索引值, 则进行替换
            if (minIndex != i) {
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }
    }

}
```

## 7.6，插入排序

### 7.6.1，基本介绍

* 插入排序属于内部排序法，是对欲排序的元素以插入的方式找到合适的位置，以达到排序的目的
* 基本思想：将一个数组在物理上划分为有序数组部分和无序数组部分；开始时有序数组部分只包括第一个元素，从第二个元素开始，依次与前一个元素进行比较并确定其位置。第二个元素排序完成后，此时有序数组部分有两个元素（第一，二个元素），无序数组部分是其他元素；继续从第三个元素开始，依次类推，直到所有元素比较完毕，注意元素插入到有序数据部分时，有序数组内部位于该元素后续位置的元素需要统一后移；
* 插入排序算法复杂度为`O(n^2)`

![1579167493138](E:\gitrepository\study\note\image\dataStructure\1579167493138.png)

### 7.6.2，代码演示

```java
package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 排序_插入排序
 * @author PJ_ZHANG
 * @create 2020-01-16 15:20
 **/
public class InsertionSort {

    public static void main(String[] args) {
        // int[] array = {10, 8, 3, 9, 2, 6, -1};
        // 10万个数测试, 2S
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        insertionSort(array);
        // System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 插入排序
    private static void insertionSort(int[] array) {
        // 固定第一个元素, 从后续元素开始于第一个元素比较
        for (int i = 1; i < array.length; i++) {
            int temp = array[i]; // 保存当前操作数据
            int currIndex = i;
            // 如果后续元素小于固定的最后一个元素, 则进行位置交换, 并以此类推知道顺序位置
            for (;currIndex > 0 && temp < array[currIndex - 1];) {
                array[currIndex] = array[currIndex - 1];
                currIndex--;
            }
            array[currIndex] = temp;
        }
    }

}
```

## 7.7，希尔排序

### 7.7.1，希尔排序介绍

* 希尔排序也是一种插入排序，是简单拆入排序经过改进之后的一个更高效的版本，也称为缩小增量排序

### 7.7.2，希尔排序的基本思想及示意图

* 对数组元素进行两两分组, 分为 (N / 2) 组, 且一组的两个数据间步长为 (N / 2)

* 对各组数据进行插入排序, 使各组数据有序
* 对上一步有序的数据, 继续分为 (N / 2 / 2) 组, 每组数据步长为 (N / 2 / 2)
* 继续对该组数进行插入排序, 使各组数据有序
* 以此类推, 直到 (N / 2 / ... / 2 = 1) 时, 不能继续分组, 最后进行插入排序, 使有序

![1583402266296](E:\gitrepository\study\note\image\dataStructure\1583402266296.png)

![1583402276669](E:\gitrepository\study\note\image\dataStructure\1583402276669.png)

### 7.7.3，代码示例

```java
package com.self.datastructure.sort;

import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * 希尔排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-05 14:26
 **/
public class ShellSort {

    public static void main(String[] args) {
        int[] array = {4, 6, 7, 8, 1, 3, 5, 2, 9, 0};
        // 10万个数测试
//        int[] array = new int[100000];
//        for (int i = 0; i < 100000; i++) {
//            array[i] = (int) (Math.random() * 8000000);
//        }
//        long startTime = System.currentTimeMillis();
        sortAndMove(array);
        System.out.println(Arrays.toString(array));
//        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    // 希尔排序_交换法排序
    // * 对数组元素进行两两分组, 分为 (N / 2) 组, 且一组的两个数据间步长为 (N / 2)
    // * 对各组数据进行插入排序, 使各组数据有序
    // * 对上一步有序的数据, 继续分为 (N / 2 / 2) 组, 每组数据步长为 (N / 2 / 2)
    // * 继续对该组数进行插入排序, 使各组数据有序
    // * 以此类推, 直到 (N / 2 / ... / 2 = 1) 时, 不能继续分组, 最后进行插入排序, 使有序
    // * 10W数据 -> 14330ms
    public static void sortAndChange(int[] array) {
        // 初始化分组
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            // 从每一组的第二个数据开始进行比较
            for (int i = gap; i < array.length; i++) {
                // 依次与前面的数据进行比较
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如果前一个数量大于后一个数量
                    // 则依次循环向前替换
                    if (array[j] > array[j + gap]) {
                        int temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    }
                }
            }
        }
    }

    // 希尔排序_移位法排序
    // 10W数据: 40ms
    public static void sortAndMove(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int j = i; // 从当前索引开始处理
                int temp = array[i]; // 存储当前索引位置值进行比较
                for (;j - gap >= 0 && temp < array[j - gap]; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

}
```

## 7.8，快速排序

### 7.8.1，快速排序法介绍

* 快速排序是对冒泡排序的一种改进，通过一趟排序将要排序的数据分为独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小；然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，通过多次递归，达到整个数组为有序数组。
* 快速排序基本思想如下：
  * 先从数组中随机取一个参考值
  * 分别从数组两边(left, right)开始取数据进行比较
  * 如果left取到的数据大于基准数据, right取到的数据小于基准数据, 则进行交换
  * 交换完成后, 对两侧数据分别与参考值比较, 如果与参考值相等, 则对侧进1
  * 一次遍历完成后, 以参考值为中点, 左侧数据小于该值, 右侧数据大于该值
  * 继续递归左右两边进行同样处理, 直到左右两侧数据数量足够小, 则数组有序

### 7.8.2，快速排序示意图

![1583822882468](E:\gitrepository\study\note\image\dataStructure\1583822882468.png)

### 7.8.3，代码实现

```java
package com.self.datastructure.sort;

/**
 * 快速排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-06 15:00
 **/
public class QuickSort {

    private static int count = 0;

    public static void main(String[] args) {
//        int[] array = {6, 8, 9, 1, 4, 3, 5, 6, 8};
        // 10万个数测试, 44ms
        // 100万测试, 193ms
        // 1000万测试, 2224ms
        int[] array = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        quickSort(array, 0, array.length - 1);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
        System.out.println("调用次数: " + count);
    }

    // 快速排序
    // 先从数组中随机取一个参考值,
    // 分别从数组两边(left, right)开始取数据进行比较
    // 如果left取到的数据大于基准数据, right取到的数据小于基准数据, 则进行交换
    // 交换完成后, 对两侧数据分别与参考值比较, 如果与参考值相等, 则对侧进1
    // 一次遍历完成后, 以参考值为中点, 左侧数据小于该值, 右侧数据大于该值
    // 继续递归左右两边进行同样处理, 知道左右两侧数据数量足够下, 则数组有序
    private static void quickSort(int[] array, int left, int right) {
        count++;
        int l = left;
        int r = right;
        // 取一个基本值
        int baseData = array[l];
        // 从两边开始进行判断
        while (l < r) {
            // 取左侧大于等于基本值的数据
            while (array[l] < baseData) {
                l++;
            }
            // 取右侧小于等于基本值的数据
            while (array[r] > baseData) {
                r--;
            }
            // 如果此时l大于等于r, 说明一趟已经比较完成, 直接退出
            if (l >= r) {
                break;
            }
            // 进行数据交换
            int temp = array[l];
            array[l] = array[r];
            array[r] = temp;
            // 因为上面已经进行过交换
            // 如果l侧数据与基础数据相等,则r测数据一定大于基础数据, r--
            if (array[l] == baseData) {
                r--;
            }
            // 如果r侧数据与基础数据相等,则l测数据一定小于基础数据, l++
            if (array[r] == baseData) {
                l++;
            }
        }
        // 出循环后, 说明一组基于基础值的数据已经比较完毕, 此时如果l = r, 则错开数据
        // 两侧分别进1
        // 如果不添加该部分, 可能会栈溢出
        if (l == r) {
            l++;
            r--;
        }
        // 以当前基准值为中点, 左侧为小于该值的数据, 右侧为大于该值的数据, 递归进行两侧处理, 直到数据有序
        if (left < r) {
            quickSort(array, left, r);
        }
        if (l < right) {
            quickSort(array, l, right);
        }
    }

}

```

## 7.9，归并排序

### 7.9.1，归并排序概述

* 归并排序就是利用归并的思想实现的排序方式，采用了经典的分治策略；分治法就是先将问题分成一些小的问题然后递归求解，治阶段就是将分阶段得到的各答案补在一起，即分而治之；归并排序处理次数 = 元素个数 - 1
* 归并排序基本思想
  * 归并排序为分和治两个部分，其中分部分是对数组元素完全拆分，拆无可拆时开始治；治就是对已经拆散的数据按顺序依次重组起来；此外，归并排序需要一个额外空间进行有序数据重组
  * 首先拆，归并排序拆的目的是将数组中的每一 元素都拆分出来
  * 拆完之后治，治是对拆开的每一组数据依次排序，并最终递归到全数据有序
  * 治过程中，需要对数组中的相邻两部分进行排序，在每一次排序过程中，首先对两部分数组中交叉重叠的部分依次有序添加到临时数组中；其次，对两部分数组中存在剩余数据数组的剩余数据依次添加到临时数组中；最后，用临时数组的元素，依次替换到原数组中参与治的两部分数组的元素，即（left - right部分）

### 7.9.2，归并排序示意图

* 分-治示意图

![1583831080333](E:\gitrepository\study\note\image\dataStructure\1583831080333.png)

* 合并子序列示意图

![1583831132995](E:\gitrepository\study\note\image\dataStructure\1583831132995.png)

### 7.9.3，代码实现

```java
package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-10 15:11
 **/
public class MergeSort {

    public static void main(String[] args) {
        //        int[] array = {6, 8, 9, 1, 4, 3, 5, 6, 8};
        // 10万个数测试, 29ms
        // 100万测试, 270ms
        // 1000万测试, 2480ms
        int[] array = new int[10000000];
        int[] tempArray = new int[array.length];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        mergeSort(array, 0, array.length - 1, tempArray);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 归并排序
     * @param array 原数组
     * @param left 左侧索引
     * @param right 右侧索引
     * @param tempArray 临时数组
     */
    private static void mergeSort(int[] array, int left, int right, int[] tempArray) {
        int middle = (left + right) / 2;
        // 先拆分, 拆分到单个数据
        if (left < right) {
            // 向左拆分
            mergeSort(array, left, middle, tempArray);
            // 向右拆分
            mergeSort(array, middle + 1, right, tempArray);
            // 再进行合并
            merge(array, left, right, middle, tempArray);
        }
    }

    /**
     * 合并数据
     * @param array 原始数组
     * @param left 左侧索引
     * @param right 右侧索引
     * @param middle 中间位置索引, 即要合并数据的中间索引
     * @param tempArray 临时数组
     */
    private static void merge(int[] array, int left, int right, int middle, int[] tempArray) {
        int tempIndex = 0;
        // 左部分起始索引
        int leftIndex = left;
        // 右部分起始索引
        int rightIndex = middle + 1;
        // 先对两部分数据重叠部分比较入组排序
        // 直到一边的数据处理完成即止, 到下一步继续处理
        while (leftIndex <= middle && rightIndex <= right) {
            if (array[leftIndex] > array[rightIndex]) {
                tempArray[tempIndex++] = array[rightIndex++];
            } else {
                tempArray[tempIndex++] = array[leftIndex++];
            }
        }

        // 分别对两部分数据多余部分直接入组排序
        while (leftIndex <= middle) {
            tempArray[tempIndex++] = array[leftIndex++];
        }
        while (rightIndex <= right) {
            tempArray[tempIndex++] = array[rightIndex++];
        }
        // 复制临时组数据到原数组
        tempIndex = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            array[tempLeft++] = tempArray[tempIndex++];
        }
    }

}

```



## 7.10，基数排序

### 7.10.1，技术排序基本介绍

* 基数排序（Radix Sort）属于**分配式排序**，又称**桶子法**或者**Bin Sort**，它是通过键值的各个位的值，将要排序的数组分配到对应桶中，达到排序的左右
* 基数排序属于稳定性排序，同时也是效率较高的稳定性排序，基数排序是对桶排序的扩展
* 基数排序基本思想：将所有待比较的数值统一为同样的数位长度（长度不足前位补零）；然后，从低位开始，依次进行一次排序。按照基本排序规则，等所有位数全部比较完成后，数据就是一个有序数列
* 基数排序基本流程：
  * 首先初始化一个二维数组，第一维表示桶的个数，从0-9共有10个桶，第二维表示落到桶中的数据
  * 其次初始化一个长度为10的一维数组，索引表示0-9的9个桶，值表示落到桶中的数据数量，用于计数
  * 从这一步开始真正进行基数排序处理，对要处理的数组元素依次截取个位数，并按照个位数数字对应落到二维数组初始化的0-9的对应桶中，并用一维数据进行计数
  * 这一轮处理完成后，依次从二维数组中获取所有数据，对原数组进行覆盖，此时即完成第一轮循环，**这一步处理完成后记得对一维数据组清零**
  * 依次类推，个位数处理完成后处理十位，百位，直到处理到数组中最大元素的最高位，最高位处理完成后，覆盖原数组，此时数组有序
* <font color=red>**注意**：*基数排序理论上来讲只能对正数进行排序；存在负数的数组中，可以先对整个数组`+`最小负数，此时数组最小为0，然后再对该数组进行基数排序，排序完成后再对全数组`-`最小负数，则完成对数组排序*</font>

### 7.10.2，基数排序示意图

* 将数组`{53，3，542，748，14，214}`进行基数排序，因为最大数 748 的最高位数为三位，则需要进行三轮处理，流程如下：
* 第一轮处理：对个位数进行处理

![1584081679642](E:\gitrepository\study\note\image\dataStructure\1584081679642.png)

* 第二轮处理：对十位数进行处理

![1584081847534](E:\gitrepository\study\note\image\dataStructure\1584081847534.png)

* 第三轮处理：对百位数进行处理

![1584081856931](E:\gitrepository\study\note\image\dataStructure\1584081856931.png)

### 7.10.3，基数排序代码实现

```java
package com.self.datastructure.sort;

/**
 * 基数排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 10:24
 **/
public class RadixSort {

    public static void main(String[] args) {
//        int[] array = {45, 832, 456, 76, 32, 17, 89, 456, 56};
        // 10万个数测试, 52ms
        // 100万测试, 208ms
        // 1000万测试, 1265ms
        // 1E测试, 8609ms, -Xmx9000m(4096M没够用)
        int[] array = new int[100000000];
        for (int i = 0; i < 100000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        radixSort(array);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 基数排序基本流程
     * * 初始化一个二维数组, 第一维表示0-9的10个元素桶, 第二维表示落到桶中的数据
     * * 初始化一个一维数组, 下标表示0-9是个数字, 值表示落到桶中数据的数量
     * * 对要排序的数组依次从个位开始截取处理, 按个位数据落到对应的二维桶中, 并用一维数组进行计数
     * * 一轮位数处理完成后, 从二维数据中依次取出所有数据, 对原数据进行覆盖
     * * 每一轮处理完成后, 记得对一维数据进行置空
     * @param array
     */
    private static void radixSort(int[] array) {

        // 二维数组存储数据
        int[][] dataArray = new int[10][array.length];
        // 一维数据计数
        int[] countArray = new int[10];

        int maxCount = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxCount) {
                maxCount = array[i];
            }
        }

        // round表示当前取的是哪一位, 用位数的整值表示, 比如十位是10, 百位是100
        // 基数排序, 按照最大数的最高位数进行循环
        for (int i = 0, round = 1; i < (maxCount + "").length(); i++, round *= 10) {
            // 将数据放入到对应的桶中, 即二维数组中
            for (int j = 0; j < array.length; j++) {
                // 获取位数值
                int data = array[j] / round  % 10;
                // 存储当前值到二维数据
                // 并对一维数据数据统计值递增
                dataArray[data][countArray[data]++] = array[j];
            }
            int index = 0;
            // 先从一维数据中获取到存在有效数据的二维数据部分
            for (int countIndex = 0; countIndex < countArray.length; countIndex++) {
                // 为0, 表示当前桶中不存在数据
                if (countArray[countIndex] == 0) continue;
                // 从二维数据获取到有效数据, 存储到原数组中
                for (int dataIndex = 0; dataIndex < countArray[countIndex]; dataIndex++) {
                    array[index++] = dataArray[countIndex][dataIndex];
                }
                // 统计数组处理完成后, 对统计数量置空
                countArray[countIndex] = 0;
            }
        }

    }

}
```



## 7.11，常用排序总结和对比

## 7.11.1，排序算法比较图

![1584083501789](E:\gitrepository\study\note\image\dataStructure\1584083501789.png)

## 7.11.2，相关术语解释

* 稳定：如果元素a原本在元素b前面，且a=b，排序后，a应该仍然在b前面
* 不稳定：如果a原本在b前面，且a=b，排序后，b可能在a前面
* 内排序：所有排序都在内存中完成
* 外排序：由于数据太大，因此把数据放在硬盘中，通过磁盘和内存数据传输完成
* 时间复杂度：一个算法执行所耗费的时间
* 空间复杂度：运行完成一个程序所需要的内存空间
* n：数据规模
* k：桶的个数，只在桶排序相关中出现
* In-place：不占用额外内存，不需要额外定义数组
* Out-place：占用额外内存



# 8，查找算法

## 8.1，线性查找

### 8.1.1，线性查找基本介绍

* 线性查找就是基本的循环查找，遍历每一个元素进行比对，返回匹配结果

### 8.1.2， 代码实现

```java
package com.self.datastructure.search;

/**
 * 线性查找
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 15:18
 **/
public class SeqSearch {

    public static void main(String[] args) {
        int[] array = {45, 832, 456, 76, 32, 17, 89, 456, 56};
        System.out.println(seqSearch(array, 56));
    }

    public static int seqSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

}
```

## 8.2，二分查找/折半查找

### 8.2.1，二分查找基本介绍

* 二分查找的前提条件是目标数组为有序数组
* 在进行数据查找时，首先确定数组的中间下标`（left + right）/ 2`
* 用数组中间下标时与目标数据进行匹配，如果匹配到直接返回；如果中间值大于目标值，则以中间下标的左侧数组作为新数组再次进行二分查找；如果中间值小于目标值，则以中间下标的右侧数据作为新数据进行二分查找；**倒序数组相反**
* 再二分递归查找时，如果找到元素，可以直接退出；如果没有找到元素，如果 left 值大于 right 值，则说明没有找到元素，直接退出

### 8.2.2，二分查找代码实现

```java
package com.self.datastructure.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 15:37
 **/
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = {1, 12, 55, 55, 55, 78, 156, 765, 873, 987};
        System.out.println(binarySearchWitAll(array, 0, array.length - 1, 55));
    }

    public static List<Integer> binarySearchWitAll(int[] array, int left, int right, int target) {
        if (left > right) {
            return null;
        }
        int middle = (left + right) / 2;
        if (target > array[middle]) {
            return binarySearchWitAll(array, middle + 1, right, target);
        } else if (target < array[middle]) {
            return binarySearchWitAll(array, left, middle - 1, target);
        } else {
            List<Integer> lstIndex = new ArrayList<>(10);
            // 获取到目标数据
            lstIndex.add(middle);
            // 左右扫描是因为可能存在相同数据
            // 向右扫描所有数据
            for (int i = middle + 1; i < array.length; i++) {
                if (array[i] == target) {
                    lstIndex.add(i);
                } else {
                    break;
                }
            }
            // 向左扫描所有数据
            for (int i = middle - 1; i >= 0; i--) {
                if (array[i] == target) {
                    lstIndex.add(i);
                } else {
                    break;
                }
            }
            return lstIndex;
        }
    }

    /**
     * 二分查找获取到对应值索引
     * @param array 目标数组
     * @param left 左索引
     * @param right 右索引
     * @param target 目标值
     * @return
     */
    public static int binarySearch(int[] array, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        // 二分, 获取到中间索引
        int middle = (left + right) / 2;
        // 大于 向右查找
        if (target > array[middle]) {
            return binarySearch(array, middle + 1, right, target);
        } else if (target < array[middle]) { // 小于, 向左查找
            return binarySearch(array, left, middle - 1, target);
        } else {
            return middle;
        }
    }

}
```

## 8.3，插值查找

### 8.3.1，插值查找基本介绍

* 插值查找的前提条件是目标数组为有序数组

* 插值查找类似于二分查找，不同的是插值查找每次从自适应middle索引开始查找

* 插值查找其实就是对二分查找middle索引求值的优化，求值公式为：

  ```
  int middle = left + (right - left) * (target - arr[left]) / (arr[right] - arr[left])
  ```

* 二分查找到插值查找的公式演进如下：

  ![1584093888334](E:\gitrepository\study\note\image\dataStructure\1584093888334.png)

* 举例说明：如果存在一个长度为20， 值为1-20的顺序一维数组，需要查找到1所在的索引。二分查找基本需要查找4次才能查找到；而通过插值查找索引在0位置的数据，只需要一次，即:

  ```
  middle = 0 + (19 - 0) * (1 - 1) / (20 - 1) = 0;
  arr[0] = 1;
  ```

### 8.3.2，代码实现

```java
package com.self.datastructure.search;


/**
 * 插入查找
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 15:37
 **/
public class InsertValueSearch {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(insertValueSearch(array, 0, array.length - 1, 10));
    }

    /**
     * 插入查找获取到对应值索引
     * @param array 目标数组
     * @param left 左索引
     * @param right 右索引
     * @param target 目标值
     * @return
     */
    public static int insertValueSearch(int[] array, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        // 插入查找, 获取到自适应middle索引
        int middle = left + (right - left) * (target - array[left]) / (array[right] - array[left]);
        System.out.println("middle: " + middle);
        // 大于 向右查找
        if (target > array[middle]) {
            return insertValueSearch(array, middle + 1, right, target);
        } else if (target < array[middle]) { // 小于, 向左查找
            return insertValueSearch(array, left, middle - 1, target);
        } else {
            return middle;
        }
    }

}
```

### 8.3.3，注意事项

* 对于数据量较大，关键字分布比较均匀的查找表来说，采用插值查找，速度较快
* 关键字不均匀的情况下，该方法不一定比二分查找速度快



## 8.4，斐波那契(黄金分割)查找

### 8,4,1，斐波那契查找基本介绍

* 黄金分割点：是把一条线段分为两部分，使得一部分与全程之比等于另一部分跟这部分之比，比例近似于0.618，称为黄金分割点

* 斐波那契数列`{1, 1, 2, 3, 5, 8 ... n, m, m + n}`发现两个相邻数的比例，无限接近于0.618

* 斐波那契查找，依旧基于数组是有序数组，并使数组长度与斐波那契数组元素相匹配，之后类似于二分查找方式，以斐波那契数组的数组特性`f[k] = f[k - 1] + f[k - 2]`，对目标数组取中值`middle = low+ f[k - 1] - 1`后再进行分段查找

  ![1595054240908](E:\gitrepository\study\note\image\dataStructure\1595054240908.png)

* 与二分查找和插值相比，重点依旧是取中值部分求值方式不同；另外效果与二分查找基本一致，网上有分析称斐波那契查找是加减运算，速度会高于二分查找

* 斐波拉契查找流程分析
  * 首先初始化一个斐波拉契数组，数组长度可以初始化为20，尽量支持扩容
  * 用原数组长度去匹配到斐波拉契数组的元素值，获取到`f[k] - 1 < array.length`最近的数据作为原始数组在斐波拉契查找法中的真实长度，具体可参考示意图
  * 对原数组进行拷贝，拷贝目标数组的长度为`f[k] - 1`，并将多与原数组长度的新加数组部分数据值修改为原数组最大值，此时数组构建完成，可以开始查找
  * 斐波拉契查找中值公式：`middle = low + f[k - 1] - 1`；其中`low`表示左侧最小索引，`f[k - 1] - 1`表示middle值的左侧数组长度
  * 公式推导如下：数组长度 `f[k] - 1 = f[k - 1] + f[k - 2] - 1`，即`f[k] - 1 = f[k - 1] - 1 + 1 + f[k - 2] - 1`；此时可以将`f[k - 1] - 1`理解为数组中值的左侧部分数组，`f[k - 2] - 1`理解为数组中值的右侧部分数组，中间的`1`即表示middle索引部分，也就是中值数据
  * 获取到中值索引后，对中值数据与查找数据进行比对，如果小于该数据，则向左查找；如果大于该数据，则向右查找；如果相等，则根据当前中值索引是否大于原数组的最大索引判断返回当前索引还是最大索引
  * 向左查找：此时中值数据左侧数组部分长度为`f[k - 1] - 1`，继续推导公式`f[k - 1] - 1 = f[k - 2] - 1 + 1 + f[K - 3] - 1`，以`f[k - 1] - 1`继续为完整数组来看，则中值的左侧部分应该为`f[k - 2] - 1`，即`f[k - 1 - 1] - 1`，进入下一轮循环，此时`k--`
  * 向右查找：同上一步，中值的右侧部分应该为`f[k - 3] - 1`，即`f[k - 2 - 1] - 1`，此时`k -= 2`

### 8.4.2，斐波那契查找原理示意图

* 初始化数组：int[] array = {1, 2, 3, 4, 5, 6};

* 初始化斐波拉契数组：int[] fbl = {1, 1, 2, 3, 5, 8};
* 拷贝原数组后：array = {1, 2, 3, 4, 5, 6, 6}，(array.length = fbl[k] - 1)， 数组长度为fbl[k] - 1，此时k=5，则array长度应该为7
* 则数组的的各部分组成如下

![1584284822377](E:\gitrepository\study\note\image\dataStructure\1584284822377.png)

![1584285402400](E:\gitrepository\study\note\image\dataStructure\1584285402400.png)

* 此时查找数据，如果落于middle左侧，则`k--`；落于middle右侧，则`k-=2`

### 8.4.3，代码实现

```java
package com.self.datastructure.search;

import java.util.Arrays;

/**
 * 斐波拉契查找法
 *
 * @author pj_zhang
 * @create 2020-03-14 21:50
 **/
public class FibratcheSearch {

    public static void main(String[] args) {
        int[] array = {1, 12, 23, 34, 55, 78, 156, 765, 873, 987};
        System.out.println(fibratcheSearch(array, 874));
    }

    /**
     * 斐波拉契查找流程
     * * 先初始一个长度为20的斐波拉契数组备用, 数组长度如果超过fbl[19], 可以进行扩容
     * * 用数组长度去匹配合适的斐波拉契数值值, 作为原始数组斐波拉契查找法中的真实长度
     * * 对原始数组进行拷贝, 拷贝后的长度 = (斐波拉契的数值值 - 1), 多余原始数组部分初始化为0
     * * 对多余部分初始化为0进行修改, 修改为数组的最大值, 保证数组有序
     * * 数据初始化完成后, 继续进行数据查询, 数据查找与二分法基本一致, 只是middle的取值方式不一致
     * * 斐波拉契查找中: middle = left + F[k - 1] - 1;
     * * 其中F数组表示斐波拉契数组, k表示数据匹配到的斐波拉契数组下标, k对应长度即原始数组拷贝后的长度
     * * 根据斐波拉契查找算法补全位后, 原数组长度为 f[k] - 1
     * * 因为 f[k] = f[k - 1] + f[k - 2]
     * * 所以 f[k] - 1 = f[k - 1] + f[k - 2] - 1
     * * 即 f[k] - 1 = f[k - 1] - 1 + 1 + f[k - 2] - 1
     * * f[k - 1] - 1: 表示middle左侧数组长度
     * * 1: 表示middle所在位置
     * * f[k - 2] - 1: 表示middle右侧数组长度
     *
     * @param array 原数组
     * @param target 需要查找的值
     * @return 返回索引
     */
    public static int fibratcheSearch(int[] array, int target) {
        int left = 0; // 左索引
        int right = array.length - 1; // 右索引
        // 数组长度匹配到的斐波拉契数组下标
        // 该下标对应值为拷贝后的数组长度
        int k = 0;
        // 初始化斐波拉契数组
        int[] fbl = initFbl(20);
        // 用数组长度匹配到斐波拉契数组的对应元素, 比如数组长度为7, 则匹配8; 为10, 匹配13; 依次类推
        // 简单斐波拉契数据: {1, 1, 2, 3, 5, 8, 13, 21}, 即从1开始, 后一个数为前两个数之和
        for (;fbl[k] - 1 < array.length;) {
            // 这部分可以添加扩容逻辑,
            // 20表示初始化长度, 如果k为20依旧小于, 则进行扩容,
            // 扩容可以以array长度进行算法匹配, 求出大概索引位置进行扩容
            // 也可以类似于集合扩容, 进行1.5或者2倍扩容
            k++;
        }
        // 拷贝原数组为斐波拉契查找需要的长度
        int[] temp = Arrays.copyOf(array, fbl[k] - 1);
        // 数组长度增加后, 增加部分数据值初始化为0, 修改值统一为最大值, 保证数组有序
        for (int i = right + 1; i < temp.length; i++) {
            temp[i] = temp[right];
        }

        // 原数组和斐波拉契数组全部初始化完成后, 可以进行数据查找
        // 获取到middle值: middle = left + F[k - 1] - 1;
        for (;left <= right;) {
            // fbl[k]表示当前数组的长度, 如果已经循环多次, 依旧表示查找区间的数组长度
            // 例: 数组长度为13, fbl[k]=13, k=6, left=0, 则middle=7,
            //     此时向左继续查找, 则right=6, k=5, fbl[k]=7;
            // 对于斐波拉契查找法, 中值索引的选择就是以斐波拉契数组的前一个数为基本参考
            // 因此, 此时 midlle 取值就是以fbl[k - 1]作为基本参考
            // 以斐波拉契数组的组成方式,
            //  * middle左侧的数组长度是fbl[k - 1] - 1, 中值索引参考为fbl[k - 1 - 1]
            //  * middle右侧的数组长度是fbl[k - 2] - 1, 中值索引参考为fbl[k - 2 - 1]
            int middle = left + fbl[k - 1] - 1;
            if (temp[middle] > target) { // 向左继续找
                // 如果中值索引对应值小于目标值, 则向左侧继续寻找
                // 此时右侧索引变成中值索引的左侧索引
                right = middle - 1;
                // 当前循环没有匹配到, 且匹配到左侧, 需要进行下一轮循环继续匹配
                // 则下一轮循环的middle就是以fbl[k - 1]为完整数据进行求中值处理
                // 则对于左侧 middle 的参考系为fbl[k - 1 - 1]
                // 所以此时k应该减1
                k--;
            } else if (temp[middle] < target) { // 向右继续找
                // 如果中值索引对应值大于目标值, 则向侧继续寻找
                // 此时左侧索引变为中值索引的右侧索引
                left = middle + 1;
                // 当前没有匹配到, 且匹配到右侧, 需要进行下一轮循环匹配
                // 此时右侧数组的长度为fbl[k - 2]
                // 对于右侧数组来说, 中值索引参考应为fbl[k - 2]的前一个数即fbl[k - 1 - 2]
                // 此时k应该减2
                k -= 2;
            } else { // 相等, 返回索引
                return middle > right ? right : middle;
            }
        }
        return -1;
    }

    /**
     * 初始化斐波拉契数组
     *
     * @return
     */
    private static int[] initFbl(int size) {
        int[] array = new int[size];
        array[0] = 1;
        array[1] = 1;
        for (int i = 2; i < size; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array;
    }

}
```



# 9，哈希表（散列）

## 9.1，哈希表基本介绍

* 散列表（HashTable，也叫哈希表），是根据关键码值（Key Value）而进行访问的数据结构。也就是说，通过关键码值映射到表中的一个位置来访问记录，以加快查找的速度。这个映射函数叫散列函数，存放记录的数组叫做散列表
* 基本数据结构为 数组 + 链表；通过键值获取到数组索引位置，存储到数组中，数组中该索引位置如果已经存在数据，则在该索引位置上构造链表。

## 9.2，哈希表基本示意图

![1584672228416](E:\gitrepository\study\note\image\dataStructure\1584672228416.png)

## 9.3，哈希表代码实现

```java
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

```



# 10，树结构

## 10.1，数据结构优缺点分析——引入数据结构

### 10.1.1，数组存储方式分析

* **优点**：通过下标方式访问元素，速度快。对于有序数组还可以使用相关查找算法提高速度
* **缺点**：需要删除或者增加某个值时，需要基于数组拷贝，效率较低

### 10.1.2，链表存储方式分析

* **优点**：删除或者增加快，只需要将操作的节点，插入到链表中即可
* **缺点**：在进行检索时，效率较低，需要从节点开始检索

### 10.1.3，树存储方式分析

* 能同时提升数据的存储和读取效率

### 10.1.4，树基本示意图及常用术语

![1584804381124](E:\gitrepository\study\note\image\dataStructure\1584804381124.png)

* **节点**：同结点，表示书上的每一个元素点
* **根节点**：即上图的A节点，树的顶层节点
* **父节点**：只存在左右子节点的节点
* **子节点**：与父节点相对应，除过根节点，所有节点都是其他节点的子节点
* **叶子节点**：没有子节点的节点
* **节点的权**：也就是节点的值
* **路径**：从root节点找到该节点的路径节点
* **层**：root节点表示第一层，依次往下层数递增
* **树高度**：指当前树的最大层数



## 10.2，二叉树（此处以二叉排序树演示）

### 10.2.1，二叉树基本概念

* 树分为很多种，其中每一个节点最多有两个节点的树形式称之为二叉树

* 二叉树的子节点分为左节点和父节点；对于一个父节点来说，可以单独存在左子节点或者右子节点，也可以同时存在左右子节点

  ![1584804957454](E:\gitrepository\study\note\image\dataStructure\1584804957454.png)

* 如果二叉树的所有叶子节点都在最后一层，并且`节点总数 = 2 ^ n - 1`，n为最大层数，则该二叉树可以称之为**满二叉树**

  ![1584805027306](E:\gitrepository\study\note\image\dataStructure\1584805027306.png)

* 如果二叉树的所有叶子节点都在最后一层或者倒数第二层，且最后一层的叶子节点在左边连续，倒数第二层的叶子节点在右边连续，则称之为**完全二叉树**

  ![1584805039750](E:\gitrepository\study\note\image\dataStructure\1584805039750.png)

### 10.2.2，二叉树遍历

* **前序遍历**
  * 先输出当前节点（初始为叶子节点）
  * 如果左子节点不为空，再递归前序遍历输出左子节点
  * 如果右子节点不为空，最后递归前序遍历输出右子节点
* 中序遍历
  * 如果左子节点不为空，先递归中序遍历输出左子节点
  * 再输出当前节点
  * 如果右子节点不为空，最后递归中序遍历输出右子节点
* **后续遍历**
  * 如果左子节点不为空，先递归后序遍历输出左子节点
  * 如果右子节点不为空，再递归后序遍历输出右子节点
  * 最后输出当前节点
* **遍历代码**

```java
package com.self.datastructure.tree;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树
 *
 * @author pj_zhang
 * @create 2020-03-21 22:02
 **/
public class BinaryTree {

    public static void main(String[] args) {
        MyBinaryTree binaryTree = new MyBinaryTree();
        binaryTree.addNode(5);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(6);
        binaryTree.addNode(3);
        binaryTree.addNode(2);
        binaryTree.addNode(7);
        binaryTree.addNode(8);
        binaryTree.addNode(8);
        binaryTree.postShowDetails(binaryTree.getNode());
    }

    static class MyBinaryTree {

        private Node node;

        // 添加二叉树节点
        public void addNode(Integer data) {
            if (null == node) {
                node = new Node(data);
            } else {
                addNode(data, node);
            }
        }

        private void addNode(Integer data, Node node) {
            if (null == node) {
                throw new RuntimeException("Node 节点为空");
            }
            if (data > node.getData()) {
                Node rightNode = node.getRightNode();
                if (null == rightNode) {
                    node.setRightNode(new Node(data));
                } else {
                    addNode(data, node.getRightNode());
                }
            } else if (data < node.getData()) {
                Node leftNode = node.getLeftNode();
                if (null == leftNode) {
                    node.setLeftNode(new Node(data));
                } else {
                    addNode(data, node.getLeftNode());
                }
            } else {
                System.out.println("数据节点已经存在");
            }
        }

        // 获取整体树节点
        public Node getNode() {
            return node;
        }

        // 前序遍历,
        // 先输出当前节点值
        // 再输出左侧节点值
        // 最后输出右侧节点值
        public void preShowDetails() {
            doPreShowDetails(node);
        }

        private void doPreShowDetails(Node node) {
            if (null == node) {
                return;
            }
            System.out.println("Node: " + node.getData());
            if (null != node.getLeftNode()) {
                doPreShowDetails(node.getLeftNode());
            }
            if (null != node.getRightNode()) {
                doPreShowDetails(node.getRightNode());
            }
        }

        // 中序输入
        // 先输出左侧节点值
        // 再输出当前节点值
        // 最后输出中间节点值
        // 中序输出结果为有序数组
        public void middleShowDetails() {
            doMiddleShowDetails(node);
        }

        public void doMiddleShowDetails(Node node) {
            if (null == node) {
                return;
            }
            if (null != node.getLeftNode()) {
                doMiddleShowDetails(node.getLeftNode());
            }
            System.out.println("Node: " + node.getData());
            if (null != node.getRightNode()) {
                doMiddleShowDetails(node.getRightNode());
            }
        }

        // 后续输出
        // 先输出左侧数据
        // 再输出右侧数据
        // 最后输出当前数据
        public void postShowDetails() {
            doPostShowDetails(node);
        }

        public void doPostShowDetails(Node node) {
            if (null == node) {
                return;
            }
            if (null != node.getLeftNode()) {
                doPostShowDetails(node.getLeftNode());
            }
            if (null != node.getRightNode()) {
                doPostShowDetails(node.getRightNode());
            }
            System.out.println("Node: " + node.getData());
        }

    }

    @Data
    @ToString
    static class Node {

        private Integer data;

        private Node leftNode;

        private Node rightNode;

        public Node() {}

        public Node(Integer data) {
            this(data, null, null);
        }

        public Node(Integer data, Node leftNode, Node rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

    }
}

```

### 10.2.3，二叉树查找

* 二叉树查找与二叉树遍历的前中后序逻辑基本一致

* **前序查找**

  * 先比较当前节点，当前节点匹配到直接返回
  * 再匹配左侧节点，并递归前序查找进行匹配，匹配到直接返回
  * 最后匹配右侧节点，并递归前序查找进行匹配，匹配到直接返回
  * 以上几步没有匹配到，则返回null，表示没有匹配到

* **中序查找**

  * 先匹配左侧节点，并递归中序查找进行匹配，匹配到直接返回
  * 再比较当前节点，当前节点匹配到直接返回
  * 最后匹配右侧节点，并递归中序查找进行匹配，匹配到直接返回
  * 以上几步没有匹配到，则返回null，表示没有匹配到

* **后序查找**

  * 先匹配左侧节点，并递归后序查找进行匹配，匹配到直接返回
  * 再匹配右侧节点，并递归后序查找进行匹配，匹配到直接返回
  * 再比较当前节点，当前节点匹配到直接返回
  * 以上几步没有匹配到，则返回null，表示没有匹配到

* 代码演示基于二叉树插入的左小右大原则进行匹配查找

* **代码演示**，此处有序二叉树演示

  ```java
  package com.self.datastructure.tree;
  
  import lombok.Data;
  import lombok.ToString;
  
  import java.util.ArrayList;
  import java.util.List;
  
  /**
   * 二叉树
   *
   * @author pj_zhang
   * @create 2020-03-21 22:02
   **/
  public class BinaryTree {
  
      public static void main(String[] args) {
          MyBinaryTree binaryTree = new MyBinaryTree();
          binaryTree.addNode(5);
          binaryTree.addNode(1);
          binaryTree.addNode(4);
          binaryTree.addNode(6);
          binaryTree.addNode(3);
          binaryTree.addNode(2);
          binaryTree.addNode(7);
          binaryTree.addNode(8);
          System.out.println(binaryTree.preFindNode(50));
      }
  
      static class MyBinaryTree {
  
          private Node node;
  
          // 添加二叉树节点
          public void addNode(Integer data) {
              if (null == node) {
                  node = new Node(data);
              } else {
                  addNode(data, node);
              }
          }
  
          private void addNode(Integer data, Node node) {
              if (null == node) {
                  throw new RuntimeException("Node 节点为空");
              }
              if (data > node.getData()) {
                  Node rightNode = node.getRightNode();
                  if (null == rightNode) {
                      node.setRightNode(new Node(data));
                  } else {
                      addNode(data, node.getRightNode());
                  }
              } else if (data < node.getData()) {
                  Node leftNode = node.getLeftNode();
                  if (null == leftNode) {
                      node.setLeftNode(new Node(data));
                  } else {
                      addNode(data, node.getLeftNode());
                  }
              } else {
                  System.out.println("数据节点已经存在");
              }
          }
  
          // 前序查找
          public Integer preFindNode(Integer targetData) {
              return doPreFindNode(targetData, node);
          }
  
          public Integer doPreFindNode(Integer targetData, Node node) {
              if (null == node) {
                  return null;
              }
              if (targetData == node.getData()) {
                  return node.getData();
              } else if (targetData < node.getData()) {
                  return doPreFindNode(targetData, node.getLeftNode());
              } else if (targetData > node.getData()) {
                  return doPreFindNode(targetData, node.getRightNode());
              }
              return null;
          }
  
          // 中序查找
          public Integer middleFindNode(Integer targetData) {
              return doMiddleFindNode(targetData, node);
          }
  
          public Integer doMiddleFindNode(Integer targetData, Node node) {
              if (null == node) {
                  return null;
              }
              if (targetData < node.getData()) {
                  return doMiddleFindNode(targetData, node.getLeftNode());
              } else if (targetData == node.getData()) {
                  return node.getData();
              } else if (targetData > node.getData()) {
                  return doMiddleFindNode(targetData, node.getRightNode());
              }
              return null;
          }
  
          // 后序查找
          public Integer postFindNode(Integer targetData) {
              return doPostFindNode(targetData, node);
          }
  
          public Integer doPostFindNode(Integer targetData, Node node) {
              if (null == node) {
                  return null;
              }
              if (targetData < node.getData()) {
                  return doPostFindNode(targetData, node.getLeftNode());
              } else if (targetData > node.getData()) {
                  return doPostFindNode(targetData, node.getRightNode());
              } else if (targetData == node.getData()) {
                  return node.getData();
              }
              return null;
          }
  
      @Data
      @ToString
      static class Node {
  
          private Integer data;
  
          private Node leftNode;
  
          private Node rightNode;
  
          public Node() {}
  
          public Node(Integer data) {
              this(data, null, null);
          }
  
          public Node(Integer data, Node leftNode, Node rightNode) {
              this.data = data;
              this.leftNode = leftNode;
              this.rightNode = rightNode;
          }
  
      }
  }
  
  ```

### 10.2.4，二叉树删除

* **删除规则**

  * 如果删除的节点是叶子节点，则直接删除
  * 如果删除的是非叶子节点，则需要对删除节点的子节点进行处理
  * 用右侧最左节点代替该节点，并删除右侧最左节点
  
* **代码实现**

  ```java
  package com.self.datastructure.tree;
  
  import lombok.Data;
  import lombok.ToString;
  
  import java.util.ArrayList;
  import java.util.List;
  
  /**
   * 二叉树
   *
   * @author pj_zhang
   * @create 2020-03-21 22:02
   **/
  public class BinaryTree {
  
      public static void main(String[] args) {
          MyBinaryTree binaryTree = new MyBinaryTree();
          binaryTree.addNode(5);
          binaryTree.addNode(2);
          binaryTree.addNode(1);
          binaryTree.addNode(4);
          binaryTree.addNode(3);
          binaryTree.addNode(8);
          binaryTree.addNode(6);
          binaryTree.addNode(9);
          binaryTree.addNode(10);
          binaryTree.middleShowDetails();
          System.out.println(binaryTree.delNode(1));;
          binaryTree.middleShowDetails();
      }
  
      static class MyBinaryTree {
  
          private Node node;
  
          // 添加二叉树节点
          public void addNode(Integer data) {
              if (null == node) {
                  node = new Node(data);
              } else {
                  addNode(data, node);
              }
          }
  
          private void addNode(Integer data, Node node) {
              if (null == node) {
                  throw new RuntimeException("Node 节点为空");
              }
              if (data > node.getData()) {
                  Node rightNode = node.getRightNode();
                  if (null == rightNode) {
                      node.setRightNode(new Node(data));
                  } else {
                      addNode(data, node.getRightNode());
                  }
              } else if (data < node.getData()) {
                  Node leftNode = node.getLeftNode();
                  if (null == leftNode) {
                      node.setLeftNode(new Node(data));
                  } else {
                      addNode(data, node.getLeftNode());
                  }
              } else {
                  System.out.println("数据节点已经存在");
              }
          }
  
          /**
           * 二叉树节点删除
           * * 如果删除节点为叶子节点, 则直接删除
           * * 如果删除节点为非叶子节点, 且只有左节点或者右节点其中一个节点, 将子节点设置为该节点
           * * 如果删除节点为非叶子节点, 且子节点完整, 则让右子节点代替该节点, 左子节点按顺序挂在右子节点的左侧位置
           *
           * * 带完整子节点的节点删除，可以直接将左子节点的最右侧节点权值与删除节点替换，并删除该叶子节点
           * * 同样，也可以将右子节点的最左侧节点权值与删除节点替换，并删除该叶子节点，不需要进行旋转
           * @param targetData
           * @return
           */
          public boolean delNode(Integer targetData) {
              if (null == node) {
                  return false;
              }
              // 根节点为目标节点, 直接右旋处理
              if (targetData == node.getData()) {
                  Node leftNode = node.getLeftNode();
                  node = node.getRightNode();
                  if (null == node) {
                      node = leftNode;
                      return true;
                  }
                  fillLeftNode(node, leftNode);
                  return true;
              }
              return doDelNode(targetData, node);
          }
  
          public boolean doDelNode(Integer targetData, Node parentNode) {
              if (null == node) {
                  return false;
              }
              if (targetData < parentNode.getData()) {
                  Node leftNode = parentNode.getLeftNode();
                  // 为空说明没有找到
                  if (null == leftNode) {
                      return false;
                  }
                  // 匹配到, 则删除该节点, 同时旋转子节点
                  if (targetData == leftNode.getData()) {
                      leftRevolve(parentNode, leftNode);
                      return true;
                  } else {
                      return doDelNode(targetData, leftNode);
                  }
              } else if (targetData > parentNode.getData()) {
                  Node rightNode = parentNode.getRightNode();
                  if (null == rightNode) {
                      return false;
                  }
                  if (targetData == rightNode.getData()) {
                      leftRevolve(parentNode, rightNode);
                      return true;
                  } else {
                      return doDelNode(targetData, rightNode);
                  }
              }
              return false;
          }
  
          /**
           * 左旋
           * 删除当前节点
           * @param node 根节点表示根节点, 其他节点表示删除节点的父节点
           * @param delNode 要删除的节点
           */
          private void leftRevolve(Node parentNode, Node delNode) {
              if (delNode == parentNode.getLeftNode()) {
                  // 删除节点的右节点为空, 直接用左节点代替原来位置
                  if (null == delNode.getRightNode()) {
                      parentNode.setLeftNode(delNode.getLeftNode());
                      return;
                  }
                  parentNode.setLeftNode(delNode.getRightNode());
              } else if (delNode == parentNode.getRightNode()) {
                  if (null == delNode.getRightNode()) {
                      parentNode.setRightNode(delNode.getLeftNode());
                      return;
                  }
                  parentNode.setRightNode(delNode.getRightNode());
              }
              // 重新放置删除节点的左侧节点, 到右侧节点的左侧
              // 如果右侧节点存在左侧节点, 则一直遍历到最后, 然后挂在最左侧
              fillLeftNode(delNode.getRightNode(), delNode.getLeftNode());
          }
  
          /**
           * 填充左侧节点
           * @param node 右旋上来的节点
           * @param leftNode 左子节点
           */
          private void fillLeftNode(Node node, Node leftNode) {
              if (null == leftNode) {
                  return;
              }
              // 删除节点右侧节点的左侧节点不为空, 则一直遍历到最后
              // 将删除节点的左侧节点挂到最后
              for (;null != node.getLeftNode();) {
                  node = node.getLeftNode();
              }
              node.setLeftNode(leftNode);
          }
  
          // 中序输出
          // 先输出左侧节点值
          // 再输出当前节点值
          // 最后输出中间节点值
          // 中序输出结果为有序数组
          public void middleShowDetails() {
              doMiddleShowDetails(node);
          }
  
          public void doMiddleShowDetails(Node node) {
              if (null == node) {
                  return;
              }
              if (null != node.getLeftNode()) {
                  doMiddleShowDetails(node.getLeftNode());
              }
              System.out.println("Node: " + node.getData());
              if (null != node.getRightNode()) {
                  doMiddleShowDetails(node.getRightNode());
              }
          }
  
      }
  
      @Data
      @ToString
      static class Node {
  
          private Integer data;
  
          private Node leftNode;
  
          private Node rightNode;
  
          public Node() {}
  
          public Node(Integer data) {
              this(data, null, null);
          }
  
          public Node(Integer data, Node leftNode, Node rightNode) {
              this.data = data;
              this.leftNode = leftNode;
              this.rightNode = rightNode;
          }
  
      }
  }
  
  ```

### 10.2.5，顺序存储二叉树

* ***顺序存储二叉树是堆排序的基本思想***

* 从数据存储来看，数组存储方式和树的存储方式可以相互转换，即数组可以转换为树，树也可以转换为数组，如下图所示

  ![1584875608743](E:\gitrepository\study\note\image\dataStructure\1584875608743.png)

* 顺序存储二叉树**只考虑完全二叉树**，并且元素间存在函数对应关系

  * 第n个元素的左子节点为：`index = 2 * n + 1`
  * 第n个元素的右子节点为：`index = 2 * n + 2`
  * 第n个元素的父节点为：`index = （n - 1）/ 2`
  * 其中n表示在完全二叉树中的第几个元素，同时也表示数组中的索引下标，**从0开始**

* 通过上面的函数关系，可以直接通过数组，实现数组转换为树后的前序，中序，后续遍历，具体代码如下

  ```java
  package com.self.datastructure.tree;
  
  /**
   * 顺序存储二叉树
   * 只对完全二叉树有效
   *
   * @author pj_zhang
   * @create 2020-03-22 18:40
   **/
  public class ArrayBinaryTree {
  
      private static int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  
      /**
       * 讲一个二叉树的节点, 以数组的顺序按层依次存放, 则该二叉树肯定是一个完全二叉树
       * 在生成的完全二叉树中:
       * 第n个元素的左子节点 index = 2 * n + 1
       * 第n个元素的右子节点 index = 2 * n + 2
       * 第n个元素的父节点为 index = (n - 1) / 2
       * @param args
       */
      public static void main(String[] args) {
          System.out.println("前序输出");
          preShowDetails(0);
          System.out.println("中序输出");
          middleShowDetails(0);
          System.out.println("后序输出");
          postShowDetails(0);
      }
  
      /**
       * 前序遍历
       * 输出结果: 0, 1, 3, 7, 8, 5, 9, 2, 5, 6
       *
       * @param index 开始索引
       */
      public static void preShowDetails(int index) {
          // 先输出当前节点
          System.out.println("前序输出: " + array[index]);
          // 再输出左侧节点
          if ((index * 2 + 1) < array.length) {
              preShowDetails(index * 2 + 1);
          }
          // 再输出右侧节点
          if ((index * 2 + 2) < array.length) {
              preShowDetails(index * 2 + 2);
          }
      }
  
      /**
       * 中序遍历
       * 输出结果: 7, 3, 8, 1, 9, 4, 0, 5, 2, 6
       *
       * @param index 开始索引
       */
      public static void middleShowDetails(int index) {
          // 先输出左侧节点
          if ((index * 2 + 1) < array.length) {
              middleShowDetails(index * 2 + 1);
          }
          // 再输出当前节点
          System.out.println("中序输出: " + array[index]);
          // 最后输出右侧节点
          if ((index * 2 + 2) < array.length) {
              middleShowDetails(index * 2 + 2);
          }
      }
  
      /**
       * 后序遍历
       * 输出结构: 7, 8, 3, 9, 4, 1, 5, 6, 2, 0
       *
       * @param index 开始索引
       */
      public static void postShowDetails(int index) {
          // 先输出左侧节点
          if ((index * 2 + 1) < array.length) {
              postShowDetails(index * 2 + 1);
          }
          // 再输出右侧节点
          if ((index * 2 + 2) < array.length) {
              postShowDetails(index * 2 + 2);
          }
          // 最后输出当前节点
          System.out.println("后序输出: " + array[index]);
      }
  
  }
  
  ```

## 10.3，线索化二叉树

### 10.3.1，线索化二叉树概述

* 线索化二叉树是对普通二叉树的扩展，对于普通二叉树而言，一个节点会包含一个数据域以及指向左右子节点的位置索引；但是对于叶子节点来讲，左右子节点的位置索引并没有被利用到，线索二叉树充分利用该部分节点，普通二叉树如下：

![1585020436548](E:\gitrepository\study\note\image\dataStructure\1585020436548.png)

* 在n个节点的二叉树中总共含有`n - 1`（*类似5指向2表示一个位置指向*）个位置指向，即`2n`（*每一个节点有左右两个指针域*）个指针域，这样对于一个二叉树来讲，共有`2n - (n - 1) = n + 1`个空指针域。利用二叉树的空指针域，根据前/中/后序不同遍历方式存储前驱节点（左子节点）和后继节点（右子节点），这种附加的指针称为线索，二叉树转线索化二叉树，后续会分别具体分析：
* 根据线索性质的不同，线索二叉树可以分为前序线索二叉树，中序线索二叉树，后续线索二叉树；分别对应前序遍历方式，中序遍历方式，后续遍历方式，部分方式的线索二叉树必须对应不同的遍历方式，不然会遍历失败，甚至栈溢出
* 二叉树转换为线索化二叉树后，`leftNode`指向的可能是左子树，也可能是前驱节点；同样，`rightNode`指向的可能是右子树，也可能是后继节点；具体区分，会在`Node`节点对象中分别维护`leftFlag`和`rightFlag`属性，对应不同的标志位标识是线索节点还是真实节点

### 10.3.2，二叉树--线索化二叉树转换规则

- 线索化二叉树是在原二叉树的基础上，将每一个节点抽象为存在上下指针的节点
- 在将二叉树转换为线索化二叉树时，
- 如果节点的左右节点存在，则该节点不变，
- 如果节点的左侧节点为空, 则将左侧节点填充为前驱节点；中序方式的遍历方式为：**左中右**，可以通过不同位置对应前驱节点
- 如果节点的右侧节点为空，则将右侧节点填充为后继节点，选择同上
- 另外，在Node节点中除过`leftNode`，`rightNode`左右节点数据外，另外维护`leftFlag`，`rightFlag`两个标志位，说明当前左/右侧数据指示的是树数据，还是后继节点数据
- 对于前序/中序/后序方式生成的线索化二叉树，必须对应的使用前序/中序/后序方式进行遍历
- **遍历结果的第一个元素和最后一个元素，分别没有前驱节点和后继节点**，所以在线索化二叉树中对应的指向Node为空，但Flag指示为前驱/后继节点

### 10.3.3，中序线索化二叉树

#### 10.3.3.1，中序线索化二叉树转换规则

* 中序输出规则为先输出左侧节点，再输出当前节点，最后输出右侧节点
* 如果左侧节点为空，递归左侧进行处理
* 处理当前节点
  * 如果左侧节点为空，填充为前驱节点，即 preNode，此时如果该节点是第一个需要遍历的节点，则`preNode`为空，单`leftFlag`会被修改为前驱节点
  * 如果右侧节点为空，填充为后继节点，同样，如果该节点是最后一个节点，则值为空，标志位为后继节点
  * 因为当前节点拿不到下一个树节点，所以填充右侧后继节点需要遍历到下一个节点后进行处理，等到遍历到下一个节点时, 此时当前节点为 preNode
  * 当前节点(preNode)的后继节点即下一个节点，也就是当前遍历到的节点，此时设置后继节点, 即把当前遍历到的节点设置为preNode的右侧节点
* 如果右侧节点不为空，递归右侧进行处理

#### 10.3.3.2，中序线索化二叉树示意图

![1585035725868](E:\gitrepository\study\note\image\dataStructure\1585035725868.png)

* 上图表示二叉树转换为线索化二叉树后的节点关系，即前驱后继节点关联关系
* 其中蓝色数字表示数节点，红色数组表示前驱/后继节点
* 实线表示左右节点的连线关系，虚线表示节点的前驱后继关联关系
* ***9节点旁边的8颜色标错了，应该是红色***

#### 10.3.3.3，中序线索化二叉树遍历规则

* 首先一直向左循环拿到`leftFlag`标志为前驱节点的节点，表示最左侧节点，也就是中序遍历需要遍历到的第一个节点
* 中序遍历方式的节点打印顺序为：**左中右**
* 此时先打印该节点，表示遍历到的第一个节点
* 打印完成后，循环向右找后继节点的右侧节点并顺序打印
* 直接循环到有效树节点，则以该节点为顶层节点，继续第一步处理

#### 10.3.3.4，线索化二叉树代码示例

```java
package com.self.datastructure.tree.binarytree;

import lombok.Data;
import lombok.ToString;

/**
 * 线索化二叉树
 *
 * @author pj_zhang
 * @create 2020-03-23 22:12
 **/
public class ClueBinaryTree {

    public static void main(String[] args) {
        MyBinaryTree binaryTree = new MyBinaryTree();
        binaryTree.addNode(5);
        binaryTree.addNode(2);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        binaryTree.addNode(6);
        binaryTree.addNode(9);
        binaryTree.addNode(7);
        // 中序生成线索化二叉树
        System.out.println("中序生成线索化二叉树...");
        binaryTree.middleClueBinaryTree();
        System.out.println("\r\n中序遍历线索化二叉树...");
        binaryTree.middleShowDetails();
        binaryTree.postShowDetails();
    }

    static class MyBinaryTree {

        private Node node;

        /**
         * 指向前一个节点, 用于下一个节点时的上一个节点操作
         * 上一个节点的右节点为空时, 需要指向下一个节点,
         * 此时设置该节点的右节点信息, 需要等操作到下一个节点, 用preNode节点作为该节点设置
         */
        private Node preNode;

        // 中序生成线索二叉树
        public void middleClueBinaryTree() {
            doMiddleClueBinaryTree(node);
        }

        public void doMiddleClueBinaryTree(Node node) {
            if (null == node) {
                return;
            }
            // 左侧递归处理
            if (node.getLeftFlag() == 0) {
                doMiddleClueBinaryTree(node.getLeftNode());
            }

            // 直接输出当前节点
            System.out.print(node.getData() + "  ");
            // 填充左侧节点
            // 左侧节点为空, 填充为前驱节点
            if (null == node.getLeftNode()) {
                // 中序: 第一个输出的节点的左侧节点必定为空
                node.setLeftNode(preNode);
                node.setLeftFlag(1);
            }
            // 填充右侧节点
            // 右侧节点为空, 填充为后继节点
            // 填充下一个节点是, 需要遍历到下一个节点进行填充
            // 则此时当前节点表示为上一个节点, 即preNode
            if (null != preNode && null == preNode.getRightNode()) {
                preNode.setRightNode(node);
                preNode.setRightFlag(1);
            }
            // 将当前节点设置为上一个节点
            preNode = node;

            // 右侧递归处理
            if (node.getRightFlag() == 0) {
                doMiddleClueBinaryTree(node.getRightNode());
            }
        }

        // 中序遍历线索二叉树
        public void middleShowDetails() {
            doMiddleShowDetails(node);
        }

        public void doMiddleShowDetails(Node node) {
            for (;null != node;) {
                // 首先循环找到leftFlag为1的节点
                // 表示左侧的叶子节点
                for (;node.getLeftFlag() == 0;) {
                    node = node.getLeftNode();
                }
                // 先打印该节点
                System.out.print(node.getData() + "  ");
                // 右侧节点状态为1, 说明是下一个节点, 直接打印
                for (;node.getRightFlag() == 1;) {
                    node = node.getRightNode();
                    System.out.print(node.getData() + "  ");
                }
                // 走到此处说明找到有效的右侧节点, 替换掉该节点
                node = node.getRightNode();
            }
        }

    }

    @Data
    @ToString
    static class Node {

        private Integer data;

        private Node leftNode;

        private Node rightNode;

        /**
         * 左侧节点标志位,
         * 0表示存在左侧节点, 1表示左侧节点为前继节点
         */
        private int leftFlag;

        /**
         * 右侧节点标志位
         * 0表示存在右侧节点, 1表示右侧节点为后续节点
         */
        private int rightFlag;

        public Node() {}

        public Node(Integer data) {
            this(data, null, null);
        }

        public Node(Integer data, Node leftNode, Node rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

    }

}

```

### 10.3.4，前序线索化二叉树

#### 10.3.4.1，前序线索化二叉树转换规则

* 前序相对于中序来讲相对比较简单
* 前序输出规则为：先输出当前节点，再输出左侧节点，最后输出右侧节点
* 处理当前节点
  *  填充规则与中序完全一致
  * 左侧节点为空，填充`preNode`节点为前驱节点
  * 右侧节点为空，填充下一个遍历到的节点为后继节点
* 再递归处理左侧节点，此时注意左侧节点如果为前驱节点则不处理
* 最后递归处理右侧节点，右侧节点为后继节点则不处理

#### 10.3.4.2，前序线索化二叉树示意图

![1585060221832](E:\gitrepository\study\note\image\dataStructure\1585060221832.png)

#### 10.3.4.3，前序线索化二叉树遍历规则

* 前序遍历相对于中序遍历稍微简单
* 前序遍历首先从顶层节点向左遍历，如果左侧节点为有效树节点，则输出后继续向左遍历
* 最终遍历到一个节点的左侧节点为前驱节点，则获取该节点的右侧节点继续进行遍历
* 此时右侧节点可能为后继节点，也可能为有效的树节点，但在前序中区分的意义不大，可以直接遍历打印，直到遍历到最后一个节点，其右侧几点即后继节点为null

#### 10.3.4.4，前序线索化二叉树代码示例

```java
package com.self.datastructure.tree.binarytree;

import lombok.Data;
import lombok.ToString;

/**
 * 线索化二叉树
 * * 线索化二叉树是在原二叉树的基础上, 将每一个节点抽象为存在上下指针的节点
 * * 在将二叉树转换为线索化二叉树时
 * * 如果节点的左右节点存在, 则该节点不变
 * * 如果节点的左侧节点为空, 则将左侧节点填充为上一个节点,
 *   上一个节点选择根据前序, 中序, 后序不同变化
 * * 如果节点的右侧节点为空, 则将右侧节点填充为下一个节点, 选择同上
 * * 另外, 在Node节点中除过leftNode, rightNode左右节点数据外,
 *   另外维护leftFlag, rightFlag两个标志位, 说明当前左/右侧数据指示的是树数据, 还是下一个节点数据
 * * 对于前序/中序/后序方式生成的线索化二叉树, 必须对应的使用前序/中序/后序方式进行遍历
 * * 遍历结果的第一个元素和最后一个元素, 分别没有前一个元素和后一个元素,
 *   所以在线索化二叉树中对应的指向Node为空, 但Flag指示为上/下一个节点
 *
 * @author pj_zhang
 * @create 2020-03-23 22:12
 **/
public class ClueBinaryTree {

    public static void main(String[] args) {
        MyBinaryTree binaryTree = new MyBinaryTree();
        binaryTree.addNode(5);
        binaryTree.addNode(2);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        binaryTree.addNode(6);
        binaryTree.addNode(9);
        binaryTree.addNode(7);
        // 前序生成线索二叉树
        System.out.println("\r\n前序生成线索化二叉树");
        binaryTree.preClueBinaryTree();
        System.out.println("\r\n前序遍历线索化二叉树");
        binaryTree.preShowDetails();
    }

    static class MyBinaryTree {

        private Node node;

        /**
         * 指向前一个节点, 用于下一个节点时的上一个节点操作
         * 上一个节点的右节点为空时, 需要指向下一个节点,
         * 此时设置该节点的右节点信息, 需要等操作到下一个节点, 用preNode节点作为该节点设置
         */
        private Node preNode;

        // 前序生成线索化二叉树
        // 规则参考中序
        public void preClueBinaryTree() {
            doPreClueBinaryTree(node);
        }

        public void doPreClueBinaryTree(Node node) {
            if (null == node) {
                return;
            }
            // 先处理当前节点
            // 先输出当前节点
            System.out.print(node.getData() + "  ");
            // 左侧节点为空, 填充为上一个节点
            if (null == node.getLeftNode()) {
                node.setLeftNode(preNode);
                node.setLeftFlag(1);
            }
            // 右侧节点为空, 填充为下一个节点
            if (null != preNode && null == preNode.getRightNode()) {
                preNode.setRightNode(node);
                preNode.setRightFlag(1);
            }
            preNode = node;

            // 再处理左侧节点
            // 注意一定要加leftFlag判断, 不然容易死递归
            if (node.getLeftFlag() == 0) {
                doPreClueBinaryTree(node.getLeftNode());
            }

            // 最后处理右侧节点
            if (node.getRightFlag() == 0) {
                doPreClueBinaryTree(node.getRightNode());
            }
        }

        /**
         * 前序遍历
         */
        public void preShowDetails() {
            doPreShowDetails(node);
        }

        public void doPreShowDetails(Node node) {
            for (;null != node;) {
                // 左侧节点为有效节点, 直接输出
                for (;0 == node.getLeftFlag();) {
                    System.out.print(node.getData() + "  ");
                    node = node.getLeftNode();
                }
                // 输出最后一个左侧有效节点
                System.out.print(node.getData() + "  ");
                // 该节点右侧节点指向下一个节点
                node = node.getRightNode();
            }
        }

        // 添加二叉树节点
        public void addNode(Integer data) {
            if (null == node) {
                node = new Node(data);
            } else {
                addNode(data, node);
            }
        }

        private void addNode(Integer data, Node node) {
            if (null == node) {
                throw new RuntimeException("Node 节点为空");
            }
            if (data > node.getData()) {
                Node rightNode = node.getRightNode();
                if (null == rightNode) {
                    node.setRightNode(new Node(data));
                } else {
                    addNode(data, node.getRightNode());
                }
            } else if (data < node.getData()) {
                Node leftNode = node.getLeftNode();
                if (null == leftNode) {
                    node.setLeftNode(new Node(data));
                } else {
                    addNode(data, node.getLeftNode());
                }
            } else {
                System.out.println("数据节点已经存在");
            }
        }

    }

    @Data
    @ToString
    static class Node {

        private Integer data;

        private Node leftNode;

        private Node rightNode;

        /**
         * 左侧节点标志位,
         * 0表示存在左侧节点, 1表示左侧节点为前继节点
         */
        private int leftFlag;

        /**
         * 右侧节点标志位
         * 0表示存在右侧节点, 1表示右侧节点为后续节点
         */
        private int rightFlag;

        public Node() {}

        public Node(Integer data) {
            this(data, null, null);
        }

        public Node(Integer data, Node leftNode, Node rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

    }

}

```



### 10.3.5，后序线索化二叉树

#### 10.3.5.1，后序线索化二叉树转换规则

* 后续输出的顺序为：先输出左侧节点，再输出右侧节点，最后输出当前节点
* 所以在后续转换时，先递归处理左侧节点
* 再递归处理右侧节点
* 最后处理当前节点，当前节点的前驱和后继节点填充与之前完成一致
* **重点**：后续输出顺序为**左右中**，所以对于一个完整子树来说，左右侧输出完成后，右侧的后继节点为它的父节点，即中间节点；此时中间节点如果为左侧节点，输出后需要再次输出其对应的右侧节点，也就是`parentNode.rightNode`，以之前前序中序的转换和遍历法则肯定不足以满足，需要在`Node`实体类中添加`parentNode`属性
* 添加完`parentNode`属性后，在二叉树转换线索化二叉树时，可直接对该属性进行维护

#### 10.3.5.2，后序线索化二叉树示意图

![1585061524924](E:\gitrepository\study\note\image\dataStructure\1585061524924.png)

#### 10.3.5.3，后序线索化二叉树遍历规则

* 后续遍历与前序和中序有所不同，并且相对复杂，后续遍历注意添加了一个新属性：`parentNode`
* 因为后续遍历的打印顺序为：左右中，所以首先还是获取最左侧节点，即`leftFlag`为0的最左侧节点，
* 首先判断右侧节点是否为后继节点，如果右侧节点为后继节点，则打印该节点，并循环继续判断下一个右侧节点，直接非后继节点为止
* 获取到非后继节点后，判断当前节点的右侧节点与上一个处理节点是否一致，或者在当前节点的右侧节点为后继节点时，当前节点的左侧节点与上一个处理节点是否一致
* 如果上一步能匹配到，说明以当前节点为顶层节点的子树已经遍历完成， 继续以该节点的父节点进行上一步判断，并以此类推
* 如果上一步没有匹配到，则说明上一个处理节点为当前节点的左侧节点，需要继续遍历左树进行处理，则对`node`重新赋值左树处理

#### 10.3.5.4，后序线索化二叉树代码示例

```java
package com.self.datastructure.tree.binarytree;

import lombok.Data;
import lombok.ToString;

/**
 * 线索化二叉树
 * * 线索化二叉树是在原二叉树的基础上, 将每一个节点抽象为存在上下指针的节点
 * * 在将二叉树转换为线索化二叉树时
 * * 如果节点的左右节点存在, 则该节点不变
 * * 如果节点的左侧节点为空, 则将左侧节点填充为上一个节点,
 *   上一个节点选择根据前序, 中序, 后序不同变化
 * * 如果节点的右侧节点为空, 则将右侧节点填充为下一个节点, 选择同上
 * * 另外, 在Node节点中除过leftNode, rightNode左右节点数据外,
 *   另外维护leftFlag, rightFlag两个标志位, 说明当前左/右侧数据指示的是树数据, 还是下一个节点数据
 * * 对于前序/中序/后序方式生成的线索化二叉树, 必须对应的使用前序/中序/后序方式进行遍历
 * * 遍历结果的第一个元素和最后一个元素, 分别没有前一个元素和后一个元素,
 *   所以在线索化二叉树中对应的指向Node为空, 但Flag指示为上/下一个节点
 *
 * @author pj_zhang
 * @create 2020-03-23 22:12
 **/
public class ClueBinaryTree {

    public static void main(String[] args) {
        MyBinaryTree binaryTree = new MyBinaryTree();
        binaryTree.addNode(5);
        binaryTree.addNode(2);
        binaryTree.addNode(1);
        binaryTree.addNode(4);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        binaryTree.addNode(6);
        binaryTree.addNode(9);
        binaryTree.addNode(7);
        // 后续生成线索二叉树
        System.out.println("\r\n后续生成线索化二叉树");
        binaryTree.postClueBinaryTree();
        System.out.println("\r\n后续遍历线索化二叉树");
        binaryTree.postShowDetails();
    }

    static class MyBinaryTree {

        private Node node;

        /**
         * 指向前一个节点, 用于下一个节点时的上一个节点操作
         * 上一个节点的右节点为空时, 需要指向下一个节点,
         * 此时设置该节点的右节点信息, 需要等操作到下一个节点, 用preNode节点作为该节点设置
         */
        private Node preNode;

        /**
         * 后续生成线索化二叉树
         */
        public void postClueBinaryTree() {
            doPostClueBinaryTree(node, null);
        }

        public void doPostClueBinaryTree(Node node, Node parentNode) {
            if (null == node) {
                return;
            }
            // 先处理左侧节点
            doPostClueBinaryTree(node.getLeftNode(), node);

            // 在处理右侧节点
            doPostClueBinaryTree(node.getRightNode(), node);

            // 最后处理当前节点
            // 先输出当前节点
            System.out.print(node.getData() + "  ");
            // 左侧节点为空, 填充为上一个节点
            if (null == node.getLeftNode()) {
                node.setLeftNode(preNode);
                node.setLeftFlag(1);
            }
            // 右侧节点为空, 填充为下一个节点
            if (null != preNode && null == preNode.getRightNode()) {
                preNode.setRightNode(node);
                preNode.setRightFlag(1);
            }
			// 后序注意填充父节点
            node.setParentNode(parentNode);
            preNode = node;
        }

        /**
         * 后续遍历线索化二叉树
         */
        public void postShowDetails() {
            doPostShowDetails(node);
        }

        public void doPostShowDetails(Node node) {
            Node preNode = null;
            for (;null != node;) {
                // 获取到最左侧数据
                for (;0 == node.getLeftFlag();) {
                    node = node.getLeftNode();
                }
                // 首先判断右侧节点是否是后继节点
                // 右侧节点为后继节点, 直接打印该节点
                for (;1 == node.getRightFlag();) {
                    System.out.print(node.getData() + "  ");
                    // 设置上一个节点为当前节点
                    preNode = node;
                    // 并将遍历节点指向后继节点
                    node = node.getRightNode();
                }

                // 能走到这一步说明右侧节点不是后继节点
                // 并且上一个操作的节点一定是当前节点的子节点(无论是单左子节点还是单右子节点, 或者左右子节点都有, 都会最终指向该节点)
                // 此时对上一个操作节点进行判断:
                // 如果上一个节点是当前节点的右子节点, 说明以该节点为顶点的子树已经遍历完成, 打印该节点后, 继续回退到父节点进行处理
                // 或者说如果上一个节点是当前节点的左子节点, 但当前节点不存在右子节点, 依旧回退到父节点进行继续处理
                // 如果上一个节点是当前节点的左子节点且存在右子节点, 则直接继续处理右子树
                for (;preNode == node.getRightNode() || (1 == node.getRightFlag() && preNode == node.getLeftNode());) {
                    System.out.print(node.getData() + "  ");
                    // 如果当前节点是根节点, 直接退出
                    if (this.node == node) {
                        return;
                    }
                    // 当前节点不是根节点, 继续往下走
                    preNode = node;
                    node = node.getParentNode();
                }
                // 上一个节点不是右侧节点
                // 则必定是左侧节点,
                node = node.getRightNode();

            }
        }

        // 添加二叉树节点
        public void addNode(Integer data) {
            if (null == node) {
                node = new Node(data);
            } else {
                addNode(data, node);
            }
        }

        private void addNode(Integer data, Node node) {
            if (null == node) {
                throw new RuntimeException("Node 节点为空");
            }
            if (data > node.getData()) {
                Node rightNode = node.getRightNode();
                if (null == rightNode) {
                    node.setRightNode(new Node(data));
                } else {
                    addNode(data, node.getRightNode());
                }
            } else if (data < node.getData()) {
                Node leftNode = node.getLeftNode();
                if (null == leftNode) {
                    node.setLeftNode(new Node(data));
                } else {
                    addNode(data, node.getLeftNode());
                }
            } else {
                System.out.println("数据节点已经存在");
            }
        }

    }

    @Data
    @ToString
    static class Node {

        private Integer data;

        private Node leftNode;

        private Node rightNode;

        /**
         * 后续序列化使用
         */
        private Node parentNode;

        /**
         * 左侧节点标志位,
         * 0表示存在左侧节点, 1表示左侧节点为前继节点
         */
        private int leftFlag;

        /**
         * 右侧节点标志位
         * 0表示存在右侧节点, 1表示右侧节点为后续节点
         */
        private int rightFlag;

        public Node() {}

        public Node(Integer data) {
            this(data, null, null);
        }

        public Node(Integer data, Node leftNode, Node rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

    }

}

```

## 10.4，堆排序

* 堆排序的基本思想是是[顺序存储二叉树](#10.2.5，顺序存储二叉树)

### 10.4.1，堆排序基本介绍

* 堆排序是利用堆这种数据结构设计的一种排序算法，类似与选择排序，它的最慢，最好，平均时间复杂度都是`O(nlogn)`，是不稳定排序

* 堆是具有以下性质的完全二叉树：每个节点的值都大于或者等于它的左右子节点的值，称为**大顶堆**；每个节点的值都小于或者等于左右的值，称为**小顶堆**；***注意此处没有要求左右节点的顺序关系***

  ![1585129142116](E:\gitrepository\study\note\image\dataStructure\1585129142116.png)

![1585129151165](E:\gitrepository\study\note\image\dataStructure\1585129151165.png)

* 一般升序使用大顶堆，降序使用小顶堆

### 10.4.2，堆排序基本思想

* 首先根据大顶堆的基本格式，将无序数组转换为符合大顶堆规则的数组
  * 此处转换先根据算法获取到最后一个非叶子节点`index = arr.length / 2 - 1`，以该节点为起点，向前依次遍历非叶子节点，并与左右子节点进行递归比较，依次保证以该节点为顶节点的自身大顶堆化
  * 上一步循环处理完成后，保证整个无序数组转换为大顶堆化的数组
* 数组完成大顶堆化转换完成后，此时顶层节点一定是该部分数组的最大数据，将该数据与处理部分数组的最后一个元素进行替换，即类似选择排序，将最大元素放在数组末尾，并用前部分数组继续进行判断
* 第一次进行最大元素转换后，此时将小元素转换到大顶堆二叉树的顶部， 该元素非最大元素，但除该元素外的其他部分都符合大顶堆规则，此时只需要对该元素下沉到合适位置，并将大元素上浮，上浮到顶层的元素即为剩余数组部分的最大元素
* 重复第二步操作直到整个数组完成排序

### 10.4.3，堆排序图解说明

* 首先，初始化一个数组 `{4，6，8，5，9}`，并将其转换为顺序存储二叉树

  ![1585141686773](E:\gitrepository\study\note\image\dataStructure\1585141686773.png)

* 然后，找到它的最后一个叶子节点索引`index = length / 2 - 1 = 4 / 2 - 1 = 1`，并以该索引为数据与它的左右节点进行比较，如果左右节点存在大于它的数据，则下沉交换；此处可以看到，`6 < 9`交换位置，此处注意，6到9的位置后，如果还存在子节点，则需要递归处理，紧跟着会看到

  ![1585141798323](E:\gitrepository\study\note\image\dataStructure\1585141798323.png)

* 索引1处理完成后，继续往前找，找到下一个非叶子节点索引0，用值4和值9比较，肯定`9 > 4`，继续替换；此时替换后注意，节点4存在两个子节点5和6，而4小于子节点，不满足大顶堆

  ![1585141998776](E:\gitrepository\study\note\image\dataStructure\1585141998776.png)

* 因为以4为顶点的子树不满足大顶堆，则递归进行处理，让4下沉

  ![1585142045584](E:\gitrepository\study\note\image\dataStructure\1585142045584.png)

* 到此为止，由无序数组转为大顶堆数组已经构建完成，*例子简单但可以说明问题*

* 现在可以开始进行排序了，构造成大顶堆数据后，root节点即0索引位置数据肯定是最大数据，与选择排序算法基本一致，将该数据与最后一个数据互换位置

  ![1585142144943](E:\gitrepository\study\note\image\dataStructure\1585142144943.png)

* 互换后可以发现，将数组分为了左侧数据和右侧数据两部分，左侧数据为待排数组，右侧数据为有序数组，当左侧数组全部归到右侧后，则整个排序完成，那继续往下走；下一步需要排序的数组，就只需要对左侧数组排序，然后替换底层节点，依次类推

* 交换位置后，最下层的节点4取代了最上层的节点9的位置，此时大顶堆树混乱；但需要注意的是，此时的混乱是在规则基础上的混乱，也就是只存在顶层节点这一个点是混乱，只需要将该点下沉到合适的位置，并在下沉过程中，将较大的值上浮，等有序后，顶层节点依旧为该数据部分的最大值，则再次与原数组的倒数第二个值替换

  ![1585142401755](E:\gitrepository\study\note\image\dataStructure\1585142401755.png)

  ![1585142417877](E:\gitrepository\study\note\image\dataStructure\1585142417877.png)

* 按照此逻辑继续，直到数组有序

  ![1585142445486](E:\gitrepository\study\note\image\dataStructure\1585142445486.png)

### 10.4.4，代码实现

```java
package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 堆排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-25 9:39
 **/
public class HeapSort {

    public static void main(String[] args) {
        // int array[] = {4, 6, 8, 5, 9, -1, 3, 1, 20, 2, 7, 30, 5, 8, 6, 3, 1};
        // 10万个数测试,  23ms
        // 100万, 291ms
        // 1000万, 3691ms
        int[] array = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        heapSort(array);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 堆排序:
     * 堆排序基本思想为顺序存储二叉树
     * 对某一个存在字节点的节点来说, index从0开始
     *  * k = 2 * index + 1
     *  * rightIndex = 2 * index + 2
     * 同样, 对某一个存在父节点的节点来说
     *  * parentIndex = (index - 1) / 2
     * 在整个顺序存储二叉树中, 最后一个非叶子节点的索引为
     *  * index = arr.length / 2 - 1
     * 以上几组公式是堆排序的基础
     *
     * 堆排序基本规则
     * * 先将数组转换为大顶堆或者小顶堆的格式, 以大顶堆为例
     * * 转换大小顶堆时, 需要从最后一个非叶子节点向前依次比较, 保证父节点大于叶子节点, 直到root节点
     * * 此时基本的大顶堆已经转换完成, 转换完成基本大顶堆是后续处理的基础
     * * 此时root节点肯定是数组中的最大元素, 将root元素与数组的最后一个元素替换
     * * 将原数组长度改为length - 1, 用剩余部分继续重组大顶堆
     * * 因为基本顶堆已经形成, 此时大顶堆只是顶层元素冲突, 只需要对顶层元素持续下沉到合适的位置, 并将大数据上升即可
     * * 以此类推, 直到所以数组元素移到右侧, 则对排序完成
     *
     * @param array
     */
    public static void heapSort(int[] array) {
        // 构造初始化的大顶堆
        // int i = array.length / 2 - 1: 表示拿到最后一个有效非子节点
        // 处理完成后，向前一直获取非子节点进行大顶堆构造，直到获取到顶层节点
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            // 大顶堆构造完成后, 此时顶层元素, 即第一个元素为该数组端最大值, 与最后一个值交换
            int max = array[0];
            array[0] = array[i];
            array[i] = max;
            // 此时基本大顶堆结构没乱, 但是root节点值为较小值, 只需要对root节点下沉到合适的位置
            // 数组长度为i
            adjustHeap(array, 0, i);
        }
    }

    /**
     * 构造大顶堆
     * @param array 原始数组
     * @param index 需要处理的数据索引
     * @param length 需要处理的数组长度
     */
    public static void adjustHeap(int[] array, int index, int length) {
        // 存储临时值, 进行最后值替换
        int temp = array[index];

        // 根据index节点索引获取到元素的左侧节点索引
        // 一次处理完成后, 如果存在子节点大于该节点, 则将该位置修改为子节点的位置
        // k = (k * 2 + 1) 即将k替换为左侧节点, 继续下沉判断
        for (int k = index * 2 + 1; k < length; k = (k * 2 + 1)) {
            // 此处找到左右节点较大的元素
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            // 元素大于目标值, 直接将目标值换位较大的节点
            if (array[k] > temp) {
                // 此处替换后, 当前节点与子节点的值一致, 为之前子节点的值, 被覆盖的值在temp中存储
                array[index] = array[k];
                // 将传递的index参数继续往下推, 与较大节点的子节点继续进行匹配, 判断是否继续下推
                // 此处注意, 持续覆盖值后, index的位置一致被k值修改下推, 最后值就是最初指定的数据需要下沉的位置
                index = k;
            } else {
                break;
            }
        }
        // 在循环里面处理完成后, 将temp下沉到合适的位置
        array[index] = temp;
    }

}

```

## 10.5，赫夫曼树

### 10.5.1，赫夫曼树基本介绍及相关概念

* 给定n个权值作为n个叶子节点，构造一颗二叉树，若该树的**带权路径长度(WPL)**达到最小，称这样的的二叉树为最优二叉树，也称为赫夫曼树，或者哈夫曼树、霍夫曼树

* 赫夫曼树是带权路径长度最短的数，权值较大的节点离根较近

* **路径和路径长度**：在一棵树中，从一个节点往下可以达到的孩子和孙子节点之间的通路，称为路径；通路中分支的数量称为路径长度；若规定根节点的层数为1，则从根节点到第L层节点的路径长度为`L - 1`

* **节点的权及带权路径长度**：若将树中的节点赋给一个有意义的值，则该值称为节点的权；从根节点到该节点的路径长度与该权值的乘积称为该节点的带权路径长度

* **树的带权路径长度**：树的带权路径长度规定为*所有**叶子节点**的带权路径长度之和*，记为**WPL（Weighted path length）**，权值越大的节点离根节点越近的二叉树才是最优二叉树，如图：

  ![1585396018711](E:\gitrepository\study\note\image\dataStructure\1585396018711.png)

### 10.5.2，赫夫曼树基本思想及示意图

* 首先对要处理的数组从小到大进行排序，每一个数据都可以转换为一个节点，每一个节点都可以看为一个最简单的二叉树（不带左右子节点的二叉树）

* 从转换好的有序节点集合中，取出两个最小的节点组成一颗二叉树

* 在这颗新二叉树中，左右子节点分别为取出的两个节点，父节点为这两个子节点的带权路径和

* 二叉树生成完成后，从节点集合中移除两个最小子节点，并将生成的二叉树父节点加入到集合中

* 之后再次对集合排序并重复以上操作，直到集合中只有一个元素，这样说明赫夫曼树已经生成完成，可以对该树进行输出查看

* 对一个数组`{13，7，8，3，29，6，1}`生成的赫夫曼树如图：

  ![1585396300684](E:\gitrepository\study\note\image\dataStructure\1585396300684.png)

  * 先对数组进行排序：`{1，3，6，7，8，13，29}`
  * 取出前两个元素：`{1，3}`，生成一个新的二叉树，父节点为两个节点的带权路径和即：`data = 1 * 1 + 3 * 1 = 4`
  * 将两个节点从数组中移除，并添加父节点到数组中后重新排序，则新的数组元素为：`{4，6，7，8，13，29}`，其中节点4存在`{1，3}`两个子节点
  * 以此类推，最终会生成上面的赫夫曼树

### 10.5.3，赫夫曼树代码实现

```java
package com.self.datastructure.tree.huffman;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树
 *
 * @author PJ_ZHANG
 * @create 2020-03-26 10:36
 **/
public class HuffmanTree {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        Node root = huffmanTree(array);
        preShowDetails(root);
    }

    /**
     * 生成赫夫曼树基本规则:
     * * 将数组的每个元素封装为Node, 并添加到集合中
     * * 对集合元素进行排序, 注意此处需要实现Comparable类, 并重写compareTo()
     * * 取前两个元素作为一个最简单的二叉树(不带子节点的)
     * * 以这两个元素作为一节点的左右两个子元素, 且该节点的权为这两个节点带权路径长度之和
     * * 将该父节点添加到集合中, 并对集合重新排序, 继续循环执行, 依次类推
     *
     * @param array
     */
    public static Node huffmanTree(int[] array) {
        // 转换为一个list
        List<Node> lstData = new ArrayList<>(10);
        for (int data : array) {
            lstData.add(new Node(data));
        }
        // 循环处理
        for (;lstData.size() > 1;) {
            // 对数组进行排序
            Collections.sort(lstData);
            // 获取前两个节点
            Node leftNode = lstData.get(0);
            Node rightNode = lstData.get(1);
            // 根据前两个节点带权路径构建第三个节点
            Node parentNode = new Node(leftNode.getData() + rightNode.getData());
            // 构建左右节点
            parentNode.setLeftNode(leftNode);
            parentNode.setRightNode(rightNode);
            // 移除前两个节点
            lstData.remove(leftNode);
            lstData.remove(rightNode);
            // 添加新构建的节点到集合中
            lstData.add(parentNode);
        }
        return lstData.get(0);
    }

    public static void preShowDetails(Node node) {
        if (null == node) {
            return;
        }
        System.out.print(node.getData() + "  ");
        if (null != node.getLeftNode()) {
            preShowDetails(node.getLeftNode());
        }
        if (null != node.getRightNode()) {
            preShowDetails(node.getRightNode());
        }
    }

    @Data
    static class Node implements Comparable<Node> {

        private int data;

        private Node leftNode;

        private Node rightNode;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node: [data = " + data;
        }

        /**
         * 进行数据比较, 满足数据升序排序
         * @param node
         * @return
         */
        @Override
        public int compareTo(Node node) {
            return this.data - node.getData();
        }
    }

}

```

## 10.6，赫夫曼编码

### 10.6.1，基本介绍

* 赫夫曼编码也称为霍夫曼编码，是一种编码方式，属于一种程序算法
* 赫夫曼编码是赫夫曼树在电讯通讯中的经典应用之一
* 赫夫曼树广泛的应用于数据文件压缩，压缩率在20%~90%之间
* 赫夫曼编码是可变字长编码（VLC）的一种，Huffman与1952年提出的一种编码方法，称为最佳编码

### 10.6.2，基本原理剖析

* 定长编码

  * 定长编码是将传递文本首先按照ASCII码转换为数字类型，再对数字类型二进制化后进行传递，传递的信息即为原始文本直译，流程如下：

  * 如有原始文本：

    ```java
    i like like like java do you like a java
    ```

  * 原始文本转换为ASCII码后如下：

    ```java
    105 32 108 105 107 101 32 108 105 107 101 32 108 105 107 101 32 106 97 118 97 32 100 111 32 121 111 117 32 108 105 107 101 32 97 32 106 97 118 97
    ```

  * ASCII码二进制化后如下：

    ```java
    01101001 00100000 01101100 01101001 01101011 01100101 00100000 01101100 01101001 01101011 01100101 00100000 01101100 01101001 01101011 01100101 00100000 01101010 01100001 01110110 01100001 00100000 01100100 01101111 00100000 01111001 01101111 01110101 00100000 01101100 01101001 01101011 01100101 00100000 01100001 00100000 01101010 01100001 01110110 01100001
    ```

  * 按照二进制传递消息，传递消息长度为 359（包括空格）

* 变长编码

  * 定长编码是对原始文本的直译，传递信息较大，因此可以用变长编码进行重组

  * 同样一串原始文本，变长编码会对文本内容进行统计，统计各个字符的个数：

    ```java
    // 字符统计
    d:1 y:1 u:1 j:2  v:2  o:2  l:4  k:4  e:4 i:5  a:5   :9
    // 二进制赋值
    0=  ,  1=a, 10=i, 11=e, 100=k, 101=l, 110=o, 111=v, 1000=j, 1001=u, 1010=y, 1011=d
    ```

  * 字符统计完成后，根据字符所占个数，从大到小以二进制进行递增赋值。如空格出现次数最多，则赋值为0，其次是a，赋值01，再次i，赋值10，依次类推

  * 按照上面的方式，会对传递的编码进行组合，最终组合的传递文本肯定远小于定长编码

  * 但是变长编码存在问题：前缀重合。即存在部分字符的二进制表达式是其他字符的前缀，在解析中可能会存在混乱，如01和0101，不能确定是两个01还是一个完整的0101

  * 根据上面问题，提出了前缀编码概念，即字符的编码不能是其他字符编码的前缀，赫夫曼编码就是一种前缀编码

* 赫夫曼编码

  * 同变长编码，赫夫曼编码同样会统计字符所占个数，并以该个数作为节点的权值构建赫夫曼树

  * 则按照字符统计频次：`d:1 y:1 u:1 j:2  v:2  o:2  l:4  k:4  e:4 i:5  a:5   :9`构建的赫夫曼树如下：

    ![1585477747459](E:\gitrepository\study\note\image\dataStructure\1585477747459.png)

  * 同时，将父节点左侧分叉定义为0，右侧分叉定义为1，每一个叶子节点所对应的赫夫曼编码为节点路径对应值的组合：

    ```java
    o: 1000   u: 10010  d: 100110  y: 100111  i: 101
    a : 110     k: 1110    e: 1111       j: 0000       v: 0001
    l: 001          : 01
    ```

  * 按照上面的赫夫曼编码，原始文本字符串对应的编译后的编码应该为：

    ```java
    1010100110111101111010011011110111101001101111011110100001100001110011001111000011001111000100100100110111101111011100100001100001110
    ```

  * 字符长度为 133，直译字符长度为359，压缩比例超过60%

  * **同时，生成赫夫曼树后，每一个原始数组有效节点在赫夫曼树都以叶子节点存在，所以前缀不可能重复**

  * **最后需要注意一个问题：赫夫曼树根据排序方式不同，虽然赫夫曼树的WPL完成相等，但生成的赫夫曼树会有些许差别。比如对于权值一样的节点排序，如果存在新生成父节点与原始节点权值相等，再排序时，将父节点放在前面与放在后面生成的赫夫曼树是不一致的；以上树为例，存在另外一种赫夫曼树组合方式**

    ![1585478270431](E:\gitrepository\study\note\image\dataStructure\1585478270431.png)

### 10.6.3，赫夫曼编码-数据压缩

#### 10.6.3.1，赫夫曼树压缩基本步骤

* 先对需要传递的文本进行字符统计，并以单个字符出现的频次进行排序
* 对排序生成的数组构建赫夫曼树，**此处注意排序方式不同，构建的数不同，但树的WPL是一致的**
* 对赫夫曼树的每一个叶子节点，即有效数据节点进行路径统计，左侧节点路径代表为0，右侧节点路径代表为1，从根节点开始，每一个叶子节点都有唯一的路径表达，且符合前缀表达式
* 将传递文本根据ASCII码进行拆分，并将每一个字符转换为对应的路径，最终生成一个二进制数字串
* 此时生成的二进制串是远大于需要传递的文本长度的，需要对需要传递的串每八位进行截取，并生成一个`byte`类型的数字，最终转换成为一个`byte[]`，此时这个字节数组以及上上一步生成的路径映射Map是真正需要传递出去的数据
* 代码在解压部分一块附上

### 10.6.4，赫夫曼编码-数据解压

#### 10.6.4.1，赫夫曼树解压基本步骤

* 对于解压部分来说，解压就是顺着压缩的步骤反向走一遍
* 首先，解压初始化入参是能接受到压缩后传递的`byte[]`数组和路径映射Map
* 先对`byte[]`数组二进制化，转换为二进制数字串，也就是每一个ASCII字符在赫夫曼树中对应路径组成的二进制数字串
* 然后对路径映射Map进行反转，传递的是`字符 -> 路径`的映射，转换为`路径 -> 字符`的映射，转换完成后，可以直接通过路径获取目标字符，重组数据串
* 因为二进数数组串满足前缀表达式，所以按二进制数字串的每一个字符依次向后移动，从反转后的Map中根据截取路径获取有效字符
  * 获取到后，拼接到结果串中，并以下一个字符作为起点，继续向后截取
  * 如果获取不到，则继续扩入一个字符进行获取
  * 依次类推，直接截取到二进制数字串结尾，获取到所有有效数据

#### 10.6.4.3，压缩解压代码

```java
package com.self.datastructure.tree.huffmancode;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 霍夫曼编码
 *
 * @author PJ_ZHANG
 * @create 2020-04-07 15:16
 **/
public class HuffmanCode {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        // 数据压缩
        // 先将传递字符串转换为byte数组, 并对每一种ASCII码出现频率进行统计
        // 根据频率大小构造赫夫曼树, 并将每一个叶子节点对应的编码值进行Map映射
        // 将原始字符串转换为赫夫曼编码字符串, 此时转换为一串二进制数字
        // 将这一串二进制数字转换为byte数组, 并准备进行传递
        // 最终传递需要赫夫曼树的Map映射和二进制数子串的byte数组
        // 路径映射,
        Map<Byte, String> pathMap = new HashMap<>(16);
        // 最终获取到的结果如下
        // [-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
        byte[] encodeBytes = encode(content, pathMap);
        System.out.println("最终生成的十进制传输数据: " + Arrays.toString(encodeBytes));
        // 数据解压
        // 将传递的byte[]数组, 转换为赫夫曼编码的二进制数字串
        // 将二进制数组串, 对照赫夫曼编码字典, 重新转换为字符串
        byte[] decodeBytes = decode(encodeBytes, pathMap);
        System.out.println("解析内容完成: " + new String(decodeBytes));
    }

    /**
     * 反编译文本内容
     * 反编译文本内容与编译文本内容相反
     *
     * @param encodeBytes 传递的十进制数字串
     * @param pathMap 字符到频次映射
     * @return
     */
    private static byte[] decode(byte[] encodeBytes, Map<Byte, String> pathMap) {
        // 首先转换十进制传递数组为二进制数字串
        // 反编译的二进制串: 1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100
        String binaryStr = decodeBinaryStr(encodeBytes);
        // 转换二进制数字串为原始字符的字节数组
        byte[] bytes = decodeContent(binaryStr, pathMap);
        return bytes;
    }

    /**
     * 反编译二进制数字串成功后, 开始进行截取映射字典, 生成byte数组, 以便后续进行文本解析
     *
     * @param binaryStr 二进制数字串
     * @param pathMap 字符和路径映射
     * @return
     */
    private static byte[] decodeContent(String binaryStr, Map<Byte, String> pathMap) {
        // 反转字符和路径映射, 处理为路径映射字符
        Map<String, Byte> path2ByteMap = reverseMap(pathMap);
        // 根据路径一段段截取二进制数字串, 并拼凑为有效的byte码
        byte[] resultBytes = doDecodeContent(binaryStr, path2ByteMap);
        return resultBytes;
    }

    /**
     * 反编译为最终需要的字节码
     * @param binaryStr 二进制自己串
     * @param path2ByteMap 路径到字符的映射
     * @return
     */
    private static byte[] doDecodeContent(String binaryStr, Map<String, Byte> path2ByteMap) {
        // 截取的每一个数字, 添加到集合中

        List<Byte> lstBytes = new ArrayList<>(10);
        for (int i = 0; i < binaryStr.length();) {
            int count = 1;
            for (;;) {
                // 以count作为一个标识位, 一直向后移动, 多括进一个字符
                // 如果路径到字符映射中, 包含该路径, 则匹配成功, 并添加该字符到集合
                String currStr = binaryStr.substring(i, i + count);
                if (null != path2ByteMap.get(currStr)) {
                    // 添加字符到集合中
                    lstBytes.add(path2ByteMap.get(currStr));
                    break;
                }
                count++;
            }
            // 匹配成功后, i直接进count位, 进行下一组数据处理
            i += count;
        }
        // 转换集合为数组
        byte[] bytes = new byte[lstBytes.size()];
        int index = 0;
        for (Byte currByte : lstBytes) {
            bytes[index++] = currByte;
        }
        return bytes;
    }

    /**
     * 反转字符串, 反转为<value, key>形式
     *
     * @param pathMap
     * @return
     */
    private static Map<String,Byte> reverseMap(Map<Byte, String> pathMap) {
        Map<String, Byte> path2ByteMap = new HashMap<>(16);
        for (Map.Entry<Byte, String> entry : pathMap.entrySet()) {
            path2ByteMap.put(entry.getValue(), entry.getKey());
        }
        return path2ByteMap;
    }

    /**
     * 反编译为二进制数字串
     * @param encodeBytes 十进制字符
     * @return 二进制数字串
     */
    private static String decodeBinaryStr(byte[] encodeBytes) {
        StringBuilder sb = new StringBuilder();
        boolean isNeedSub = true;
        for (int i = 0; i < encodeBytes.length; i++) {
            if (i == encodeBytes.length - 1 && encodeBytes[i] > 0) {
                isNeedSub = false;
            }
            sb.append(decodeDecimal(isNeedSub, encodeBytes[i]));
        }
        return sb.toString();
    }

    /**
     * 转换
     * @param isNeedSub 是否需要截取
     * @param encodeByte 当前需要转换的数据
     * @return
     */
    private static String decodeDecimal(boolean isNeedSub, int encodeByte) {
        String str = "";
        // 此处负数通过二进制转换会转换为标准的32位, 但是正数不会补0
        // 所以需要对数据转换后再截取, 转换方式为与256进行或运算
        // 256的二进制为: 1 0000 0000, 无论任务数字与256进行或运算后, 绝对能保证第九位的1, 则后八位有效
        // 转换完成后, 截取后八位作为有效数据
        // 注意: 最后一位需要处理的数据不一定满8位, 所以不满八位的情况下一定为正数, 需要原样处理
        // 满八位后, 可能为负数, 需要进行判断是否截取, 在调用方法中已经加标识位判断
        if (isNeedSub) {
            encodeByte |= 256;
            str = Integer.toBinaryString(encodeByte);
            str = str.substring(str.length() - 8);
        } else {
            str = Integer.toBinaryString(encodeByte);
        }
        return str;
    }

    /**
     * 编译文本内容
     *
     * @param content 文本内容
     * @param pathMap 字符Byte到赫夫曼码的映射
     * @return
     */
    private static byte[] encode(String content, Map<Byte, String> pathMap) {
        // 获取到字节码
        byte[] bytes = content.getBytes();
        // 统计频次, 以频次作为构建赫夫曼节点的权值
        Map<Byte, Integer> timeMap = new HashMap<>(16);
        statisticsTime(bytes, timeMap);
        // 转换频次映射Map为List
        List<Node> lstNode = transformMap2List(timeMap);
        // 转换为赫夫曼树
        Node huffmanTree = encodeHuffmanTree(lstNode);
        // 根据赫夫曼树, 生成字符的映射路径
        encodeByte2Path(huffmanTree, pathMap);
        // 根据传递内容, 拼接赫夫曼编码的二进制串, 按照上面传递的字符, 长度应该为133
        // 另外不同方式方式构建的赫夫曼树, 获得的串不一致
        // 比如形同time值的不同数据, 放在list的不同位置, 拼出来的树不一样, 但带权路径一样
        String binaryStr = encodeBinaryStr(bytes, pathMap);
        // 构建完成二进制串后, 对二进制串每8位生成一个十进制数据进行传递, 并转换为byte
        // 此处主要为了减少传递数据
        byte[] resultData = encodeResultData(binaryStr);
        return resultData;
    }

    /**
     * 对二进制数字串, 每8位构造一个十进制数据, 并传递出去,
     * 这一步构造的数据, 是真正需要传递出去的数据
     *
     * @param binaryStr
     * @return
     */
    private static byte[] encodeResultData(String binaryStr) {
        // 获取长度
        int length = (binaryStr.length() + 7) / 8;
        int count = 0;
        int index = 0;
        byte[] bytes = new byte[length];
        // 截取长度进行处理
        for (int i = 0; i < length; i++) {
            String currStr = "";
            if (i == length - 1) {
                currStr = binaryStr.substring(count);
            } else {
                currStr = binaryStr.substring(count, count + 8);
                count += 8;
            }
            // 截取完成后, 转为byte型
            byte currData = (byte) Integer.parseInt(currStr, 2);
            bytes[index++] = currData;
        }
        return bytes;
    }

    /**
     * 拼接二进制数字串
     * @param bytes 传递字符串转换后的byte
     * @param pathMap byte到二进制路径的映射
     * @return
     */
    private static String encodeBinaryStr(byte[] bytes, Map<Byte, String> pathMap) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(pathMap.get(b));
        }
        return sb.toString();
    }

    /**
     * 根据赫夫曼树, 构建路径映射
     *
     * @param huffmanTree 赫夫曼树
     * @param pathMap 路径映射
     */
    private static void encodeByte2Path(Node huffmanTree, Map<Byte, String> pathMap) {
        StringBuilder sb = new StringBuilder();
        if (null != huffmanTree.getLeftNode()) {
            // 左侧拼接0
            appendPath(huffmanTree.getLeftNode(), "0", sb, pathMap);
        }
        if (null != huffmanTree.getRightNode()) {
            // 右侧拼接1
            appendPath(huffmanTree.getRightNode(), "1", sb, pathMap);
        }
    }

    /**
     * 拼接路径
     *
     * @param node 当前节点
     * @param pathCode 路径值
     * @param sb 拼接字符
     * @param pathMap 映射字符
     */
    private static void appendPath(Node node, String pathCode, StringBuilder sb, Map<Byte, String> pathMap) {
        StringBuilder newSB = new StringBuilder(sb);
        newSB.append(pathCode);
        if (null != node.getLeftNode()) {
            appendPath(node.getLeftNode(), "0", newSB, pathMap);
        }
        if (null != node.getRightNode()) {
            appendPath(node.getRightNode(), "1", newSB, pathMap);
        }
        // 遍历只处理叶子节点, 生成的虚拟父节点, data值为null
        if (null != node.getData()) {
            pathMap.put(node.getData(), newSB.toString());
        }
    }

    /**
     * 转换为赫夫曼树
     *
     * @param lstNode 构造的字符节点集合
     * @return 赫夫曼树根节点
     */
    private static Node encodeHuffmanTree(List<Node> lstNode) {
        for (;CollectionUtils.isNotEmpty(lstNode) && lstNode.size() > 1;) {
            // 每一次循环排序一次, 保证取到的前两个二叉树为最小数据
            Collections.sort(lstNode);
            // 构造父节点, 并设置左右子节点
            Node leftNode = lstNode.get(0);
            Node rightNode = lstNode.get(1);
            Node parentNode = new Node();
            parentNode.setTime((byte) (leftNode.getTime() + rightNode.getTime()));
            parentNode.setLeftNode(leftNode);
            parentNode.setRightNode(rightNode);
            // 从集合中移除前两个节点
            lstNode.remove(leftNode);
            lstNode.remove(rightNode);
            // 添加新节点
            lstNode.add(parentNode);
        }
        return lstNode.get(0);
    }

    /**
     * 转换映射频次为List, 方便后续赫夫曼树转换
     *
     * @param timeMap
     * @return
     */
    private static List<Node> transformMap2List(Map<Byte, Integer> timeMap) {
        List<Node> lstNode = new ArrayList<>(10);
        for (Map.Entry<Byte, Integer> entry : timeMap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            lstNode.add(node);
        }
        return lstNode;
    }

    /**
     * 统计每一个字符出现的频次
     *
     * @param bytes
     * @param pathMap
     */
    private static void statisticsTime(byte[] bytes, Map<Byte, Integer> timeMap) {
        for (byte currByte : bytes) {
            Integer time = timeMap.get(currByte);
            time = null == time ? 1 : ++time;
            timeMap.put(currByte, time);
        }
    }

    @Data
    static class Node implements Comparable<HuffmanCode.Node> {

        private Byte data; // 字符

        private int time; // 字符频次

        private Node leftNode;

        private Node rightNode;

        public Node(Byte data, int time) {
            this.data = data;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Node: [data = " + data + ", time = " + time + "] ";
        }

        /**
         * 进行数据比较, 满足数据升序排序
         * @param node
         * @return
         */
        @Override
        public int compareTo(Node node) {
            return this.getTime() - node.getTime();
        }
    }

}

```

### 10.6.5，赫夫曼编码-文件压缩

#### 10.6.5.1，文件压缩基本步骤

* 赫夫曼编码是基于字节数组进行编码，编码为二进制数字串后再转换为字节数组进行输出
* 因为所有文件都可以通过输入流转换为字节数组，所以在理论上是都可以通过赫夫曼编码进行压缩的
* 将原始文件读到内存中经过赫夫曼编码逻辑生成路径映射Map及赫夫曼编码的字节数组，并将这两个对象写入目标文件即为压缩后的文件
* 代码在解压中附上

### 10.6.6，赫夫曼编码-文件解压

#### 10.6.6.1，文件解压基本步骤

* 接文件压缩，文件压缩将路径映射Map及赫夫曼编码的字节数组写入压缩文件，再文件解压时需要读取文件内这两部分内容
* 读取到两个对象后，通过赫夫曼编码解压部分代码进行内容解析，解析生成一个原始文件对应的`byte[]`
* 将这个`byte[]`对象直接通过输出流输出为一个新文件，即文件解压

#### 10.6.6.2，代码实现

```java
package com.self.datastructure.tree.huffmancode;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件压缩_赫夫曼编码
 *
 * @author PJ_ZHANG
 * @create 2020-04-08 11:24
 **/
public class FileHuffmanCode {

    public static void main(String[] args) {
        // 文件压缩
        // 首先压缩方式与文本基本一致, 读取到文件的所有字节码后
        // 构造赫夫曼树, 生成路径映射
        // 构造二进制数字串之后转为byte[]数组
        // 写出byte[]数组和路径映射到文件中, 该文件即为压缩文件
        compress("F:\\123.bmp", "F:\\123.zip");
        System.out.println("压缩完成...");

        // 文件解压
        // 解压是先从第一布的压缩文件路径中读取到写出的byte[]数组和路径映射
        // 之后对byte[]数组进行二进制数字串转换再多最后的源文件字节数组转换
        // 最后写出字节数组到目标文件中, 视为对压缩文件的解压
        decompress("F:\\123.zip", "F:\\1234.bmp");
        System.out.println("解压完成...");
    }

    /**
     * 文件解压
     *
     * @param srcFilePath
     * @param desFilePath
     */
    private static void decompress(String srcFilePath, String desFilePath) {
        FileInputStream is = null;
        ObjectInputStream ois = null;
        FileOutputStream os = null;

        try {
            is = new FileInputStream(srcFilePath);
            ois = new ObjectInputStream(is);
            // 按顺序读取赫夫曼码映射和赫夫曼编码转换后的字节数组
            Map<Byte, String> pathMap = (Map<Byte, String>) ois.readObject();
            byte[] bytes = (byte[]) ois.readObject();
            // 解压为真是的字节数组
            byte[] needBytes = ContentHuffmanCode.decode(bytes, pathMap);
            // 写出去
            os = new FileOutputStream(desFilePath);
            os.write(needBytes);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                ois.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件压缩
     *
     * @param srcFilePath 文件源路径
     * @param desFilePath 文件目标路径
     */
    private static void compress(String srcFilePath, String desFilePath) {
        // 初始化输入输出流
        FileInputStream is = null;
        FileOutputStream os = null;
        ObjectOutputStream oos = null;

        try {
            // 读取文件字节码
            is = new FileInputStream(srcFilePath);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            // 构造赫夫曼编码路径映射及转换后的字节码
            Map<Byte, String> pathMap = new HashMap<>(16);
            byte[] huffmanBytes = ContentHuffmanCode.encode(bytes, pathMap);
            // 写数据到目标文件中, 作为压缩文件
            os = new FileOutputStream(desFilePath);
            oos = new ObjectOutputStream(os);
            oos.writeObject(pathMap);
            oos.writeObject(huffmanBytes);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

```

## 10.7，[顺序二叉树](https://www.cs.usfca.edu/~galles/visualization/BST.html)

* 参考[10.2，二叉树（此处以二叉排序树演示）](#10.2，二叉树（此处以二叉排序树演示）)

## 10.8，[平衡二叉树](https://www.cs.usfca.edu/~galles/visualization/AVLtree.html)

### 10.8.1，二叉排序树问题

* 对于一个有序数组`{1, 2, 3, 4, 5}`，其生成的二叉排序树如下；由图可见，最终形成一个类似单链表形式的二叉树，对插入速度没有影响，但是对于查询速度明显减低，不能通过BST进行查询，时间复杂度为`O(n)`；需要对二叉排序树这种现象进行优化，则引入平衡二叉树(AVL)

  ![1586595505421](E:\gitrepository\study\note\image\dataStructure\1586595505421.png)

### 10.8.2，基本介绍

* 平衡二叉树也叫平衡二叉搜索树(self-balancing binary search tree)，又被称为AVL树，可以保证较高的二分查找效率

* 平衡二叉树特点：是一颗空树或者左右子树的高度差绝对值小于1的树，并且左右子树都是平衡二叉树；平衡二叉树的实现有红黑树，AVL，替罪羊树，Treap，伸展树等

* 平衡二叉树的平衡保证通过单旋转（左旋转，右旋转）和双旋转完成

* 左旋流程：

  * 左旋触发条件：当前节点的左树高度与右树高度差值大于1，进行左旋，重新保证树的平衡

  * 将当前节点重新初始化为一个新节点`newNode`

  * 将`newNode`的右侧节点，设置为当前节点的右侧节点

  * 将`newNode`的左侧节点，设置为当前节点左侧节点的右侧节点

  * 将当前节点的权值设置为当前节点左侧节点的权值，**此时完成左侧节点上浮**

  * 将当前节点的左侧节点设置为左侧节点的左侧节点，**当前节点的原始左侧节点已经上浮，挂空后等待GC回收**

  * 将当前节点的右侧节点设置为`newNode`

  * 此时完成左旋，示意图如下，节点40表示当前节点

    ![1586597439611](E:\gitrepository\study\note\image\dataStructure\1586597439611.png)

* 右旋流程：

  - 右旋触发条件：当前节点的右树高度与左树高度差值大于1，进行右旋，重新保证树的平衡

  - 将当前节点重新初始化为一个新节点`newNode`

  - 将`newNode`的左侧节点，设置为当前节点的左侧节点

  - 将`newNode`的右侧节点，设置为当前节点右侧节点的左侧节点

  - 将当前节点的权值设置为当前节点右侧节点的权值，**此时完成右侧节点上浮**

  - 将当前节点的右侧节点设置为右侧节点的右侧节点，**当前节点的原始右侧节点已经上浮，挂空后等待GC回收**

  - 将当前节点的左侧节点设置为`newNode`

  - 此时完成右旋，示意图如下，节点40表示当前节点

    ![1586597375283](E:\gitrepository\study\note\image\dataStructure\1586597375283.png)

* 双旋转流程：

  * 双旋转触发条件：双旋转是先左旋转，后右旋转（或者相反）。以基本旋转为右旋转分析，左旋转相反

  * 当前节点为根节点的二叉树已经满足右旋转标准，但是其左侧子节点的右侧子树高度高于左侧子树；因为在右旋时需要将左侧子节点的右侧子树挂到新建节点的左侧，直接右旋完成后，会发现高度差依然是2，不过是右侧比左侧高2，又符合左旋标准，进入死循环，如图：

    ![1586598189044](E:\gitrepository\study\note\image\dataStructure\1586598189044.png)

  * 因为左侧子节点在右旋时高度会升高1，其左侧节点如果比其右侧节点高度高1，则旋转后叶子节点高度相同；如果其左侧节点比其右侧节点高度少1，则旋转后，其左侧节点再高一层，右侧高度不变，高差依旧为2，又需要左旋，进入死循环

  * 此时处理办法是，先对子树进行旋转；基本旋转为右旋时，如果当前节点左侧子节点的右子树高度高于左子树，则以该左侧节点为根节点，首先进行一次左旋，保证在该子树上，左子树节点高度高于右子树节点；

    ![1586598859909](E:\gitrepository\study\note\image\dataStructure\1586598859909.png)

  * 此时再进行基本的右侧旋转，则最终获取到的AVL树就是标准的AVL树

    ![1586598939950](E:\gitrepository\study\note\image\dataStructure\1586598939950.png)

### 10.8.3，AVL树代码实现

```java
package com.self.datastructure.tree.binarytree;

import lombok.Data;

/**
 * 平衡二叉树
 *
 * @author PJ_ZHANG
 * @create 2020-04-09 9:11
 **/
public class AVLTree {

    public static void main(String[] args) {
        MyAVLTree myAVLTree = new MyAVLTree();
        // 增加节点
        // 删除节点
        // 遍历树
    }

    static class MyAVLTree {
        // 根节点
        private Node root;

        /**
         * 获取根节点
         *
         * @return 根节点
         */
        public Node getRoot() {
            return root;
        }

        /**
         * 删除节点
         * 删除节点是在
         *
         * @param value
         */
        public void delNode(int value) {
            if (null == root) {
                return;
            }
            // 删除根节点
            if (root.getValue() == value && null == root.getLeftNode() && null == root.getRightNode()) {
                root = null;
                return;
            }
            doDelNode(null, root, value);
        }

        private void doDelNode(Node parentNode, Node node, int value) {
            // 删除节点
            deleteNode(parentNode, node, value);
            // 节点删除完成后, 刷新AVL树
            // 删除不同于添加, 添加肯定添加到叶子节点, 所以可以直接进行树旋转处理
            // 删除可能在中间节点删除, 需要重新构造一次, 从根节点开始构造
            refreshAVLTree(root);
        }

        /**
         * 重构AVL树
         * @param node 当前递归到的节点
         */
        private void refreshAVLTree(Node node) {
            if (null == node) {
                return;
            }
            // 先处理左边
            refreshAVLTree(node.getLeftNode());
            // 再处理右边
            refreshAVLTree(node.getRightNode());
            // 进行旋转
            rotate(node);
        }

        /**
         * 删除节点
         *
         * @param parentNode 父节点
         * @param node 当前递归到的节点
         * @param value 要删除的值
         */
        private void deleteNode(Node parentNode, Node node, int value) {
            if (node.getValue() < value) {
                deleteNode(node, node.getRightNode(), value);
            } else if (node.getValue() > value) {
                deleteNode(node, node.getLeftNode(), value);
            } else {
                // 找到节点, 进行节点删除
                // 对当前节点的权值进行替换, 用左侧节点的最右侧节点进行替换
                if (null == node.getLeftNode() && null == node.getRightNode()) {
                    // 当前节点为叶子节点
                    if (parentNode.getRightNode() == node) {
                        parentNode.setRightNode(null);
                    } else if (parentNode.getLeftNode() == node) {
                        parentNode.setLeftNode(null);
                    }
                    node = parentNode;
                } else if (null == node.getLeftNode() || null == node.getRightNode()) {
                    // 当前节点为父节点, 但只有单子节点
                    // 因为AVL树的平衡属性, 节点如果只有单子节点, 则该子节点下不可能再有子节点, 如果往这部分加节点, 则会触发旋转
                    // 如果左侧节点为空, 则将右侧节点的权值赋给该节点, 并将该节点的右侧节点断开
                    // 如果右侧节点为空, 则将左侧节点的权值赋给该节点, 并将该节点的左侧节点断开
                    if (null == node.getLeftNode()) {
                        node.setValue(node.getRightNode().getValue());
                        node.setRightNode(null);
                    } else if (null == node.getRightNode()) {
                        node.setValue(node.getLeftNode().getValue());
                        node.setLeftNode(null);
                    }
                } else {
                    // 当前节点为父节点, 并且直接子节点完整
                    // 取左子节点的最右侧节点替换该节点
                    // 取左侧节点为临时节点
                    // 取当前节点为父节点
                    Node tmpNode = node.getLeftNode();
                    parentNode = node;
                    // 一直取左侧节点的右侧节点, 直到右侧节点为空, 说明已经获取到左树的最大值
                    for (;null != tmpNode.getRightNode();) {
                        parentNode = tmpNode;
                        tmpNode = tmpNode.getRightNode();
                    }
                    // 将Node的值设置为左树最大节点值
                    node.setValue(tmpNode.getValue());
                    // 接着需要将该节点断开
                    // 该节点可能是父节点的左侧节点, 也可能是右侧节点, 需要分支处理
                    // 同样, 该节点可能存在左侧节点, 需要重新连接
                    // 如果节点是左侧节点, 并且存在左侧节点, 则直接将该节点的左侧节点设置为父节点的左侧节点
                    // 如果节点是右侧节点, 并且存在左侧节点, 则直接将该节点的左侧节点设置为父节点的右侧节点
                    if (tmpNode == parentNode.getLeftNode()) {
                        parentNode.setLeftNode(tmpNode.getLeftNode());
                    } else if (tmpNode == parentNode.getRightNode()) {
                        parentNode.setRightNode(tmpNode.getLeftNode());
                    }
                }
            }
        }

        /**
         * 添加节点
         *
         * @param value 要添加的节点
         */
        public void addNode(int value) {
            if (null == root) {
                root = new Node(value);
                return;
            }
            doAddNode(root, value);
        }

        private void doAddNode(Node parentNode, int value) {
            if (null == parentNode) {
                return;
            }
            // 添加节点
            if (parentNode.getValue() < value) {
                if (null == parentNode.getRightNode()) {
                    parentNode.setRightNode(new Node(value));
                } else {
                    doAddNode(parentNode.getRightNode(), value);
                }
            } else if (parentNode.getValue() > value) {
                if (null == parentNode.getLeftNode()) {
                    parentNode.setLeftNode(new Node(value));
                } else {
                    doAddNode(parentNode.getLeftNode(), value);
                }
            } // 等于不添加

            // 节点添加完成后, 进行左旋右旋处理
            // 因为添加节点是递归加的, 所以对于添加节点路径上的每一个节点都会进行该步操作
            // 节点旋转, 构建平衡树
            rotate(parentNode);
        }

        /**
         * 进行左旋右旋处理,
         * 添加和删除节点都涉及该步
         * @param currNode
         */
        private void rotate(Node currNode) {
            // 如果左侧树比右侧树的高度差大于1, 则右旋
            if (getLeftHeight(currNode) - getRightHeight(currNode) > 1) {
                // 如果左侧树的右侧节点层数比左侧树的左侧节点层数高, 则先进行一次左旋
                if (null != currNode.getLeftNode() && getLeftHeight(currNode.getLeftNode()) < getRightHeight(currNode.getLeftNode())) {
                    leftRotate(currNode.getLeftNode());
                }
                rightRotate(currNode);
            }
            // 如果右侧树比左侧树的高度小于1, 则左旋
            else if (getRightHeight(currNode) - getLeftHeight(currNode) > 1) {
                // 如果右侧数的左侧子节点层数比右侧子节点层数大, 则先进行一次右旋
                if (null != currNode.getRightNode() && getRightHeight(currNode.getRightNode()) < getLeftHeight(currNode.getRightNode())) {
                    rightRotate(currNode.getLeftNode());
                }
                leftRotate(currNode);
            }
        }

        /**
         * 右旋
         * 重新构造当前节点为新节点
         * 将新节点的右侧节点设置为当前节点的右侧节点
         * 将新节点的左侧节点设置为当前节点的左侧节点的右侧节点
         * 将当前节点的权值设置为左侧节点的权值
         * 将当前节点的左侧节点设置为左侧节点的左侧节点
         * 将当前节点的右侧节点设置为新节点
         *
         * @param node
         */
        public void rightRotate(Node node) {
            Node newNode = new Node(node.getValue());
            newNode.setRightNode(node.getRightNode());
            newNode.setLeftNode(node.getLeftNode().getRightNode());
            node.setValue(node.getLeftNode().getValue());
            node.setLeftNode(node.getLeftNode().getLeftNode());
            node.setRightNode(newNode);
        }

        /**
         * 左旋
         * 重新构造当前节点为新节点
         * 将新节点的左侧节点设置为当前节点的左侧节点
         * 将新节点的右侧节点设置为当前节点的右侧节点的左侧节点
         * 将当前节点的权值设置为右侧节点的权值
         * 将当前节点的右侧节点设置为右侧节点的右侧节点
         * 将当前节点的左侧节点设置为新节点
         * @param node 需要处理的子树根节点
         */
        public void leftRotate(Node node) {
            Node newNode = new Node(node.getValue());
            newNode.setLeftNode(node.getLeftNode());
            newNode.setRightNode(node.getRightNode().getLeftNode());
            node.setValue(node.getRightNode().getValue());
            node.setRightNode(node.getRightNode().getRightNode());
            node.setLeftNode(newNode);
        }

        /**
         * 获取右侧树高度
         * @param node
         * @return
         */
        public int getRightHeight(Node node) {
            if (null == node.getRightNode()) {
                return 0;
            }
            return getHeight(node.getRightNode());
        }

        /**
         * 获取左侧树高度
         * @param node
         * @return
         */
        private int getLeftHeight(Node node) {
            if (null == node.getLeftNode()) {
                return 0;
            }
            return getHeight(node.getLeftNode());
        }

        /**
         * 获取树高度
         * @param node
         * @return
         */
        public int getHeight(Node node) {
            int height = 0;
            int leftHeight = 0;
            int rightHeight = 0;
            if (null != node.getLeftNode()) {
                leftHeight += getHeight(node.getLeftNode());
            }
            if (null != node.getRightNode()) {
                rightHeight = getHeight(node.getRightNode());
            }
            height = Math.max(leftHeight, rightHeight);
            return height + 1;
        }

        /**
         * 中序遍历
         */
        public void middleShowDetails() {
            doMiddleShowDetails(root);
        }

        private void doMiddleShowDetails(Node node) {
            if (null == node) {
                return;
            }
            doMiddleShowDetails(node.getLeftNode());
            System.out.println(node);
            doMiddleShowDetails(node.getRightNode());
        }

    }

    @Data
    static class Node {

        // 节点权值
        private int value;

        // 左节点
        private Node leftNode;

        // 右节点
        private Node rightNode;

        public Node() {}

        public Node(int value) {
            this.value = value;
        }

        public String toString() {
            return "Node: [value = " + value + "]";
        }

    }

}
```



## 10.9，多路查找树

### 10.9.1，二叉树问题分析

* 二叉树添加到内存中后，如果二叉树的节点少，那没有什么问题。但是如果二叉树的节点很多，在构建二叉树时就需要进行多次I/O操作，同时也会造成二叉树的高度很大，降低操作速度

### 10.9.2，B树（2-3树，2-3-4树）

#### 10.9.2.1，B树的基本介绍

* B树通过重新组织节点，降低树的高度，从而提高操作效率

* 文件系统及数据库系统的设计者利用磁盘预读原理，将一个节点的大小设置为页的倍数（4K的倍数，MySQL一个节点为4页），这样每一个节点只需要一次I/O就可以完全载入

* 将树的度M设置为1024，600E元素最多只需要4次IO就可以读取到想要的元素

  > 节点的度：节点有几个子节点，即表示节点的度为几
  >
  > 树的度：该树中最大的节点度表示树的度

#### 10.9.2.2，2-3树插入规则

* 2-3树的所有叶子节点都在同一层（只要是B树都满足这个条件）

* 有两个子节点的节点叫二节点，二节点要么没有子节点（叶子节点），要么有两个子节点

* 有三个子节点的节点要三节点，三节点要么没有子节点，要么有三个子节点

* 当按照规则插入一个数到某个节点中，如果不能满足以上三个要求，就需要拆，先向上拆处理父节点，再向下拆处理子节点

* 2-3树数据排序依然遵守BST规则

* 2-3-4树与2-3树概念基本一致

  ![1587030068416](E:\gitrepository\study\note\image\dataStructure\1587030068416.png)

#### 10.9.2.3，代码实现-目前只实现了插入

```java
package com.self.datastructure.tree.multiwaytree;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * B树
 *
 * @author PJ_ZHANG
 * @create 2020-04-16 9:57
 **/
public class BTree {

    public static void main(String[] args) {
        SelfBTree selfBTree = new SelfBTree(2);
        // 插入数据
    }


    static class SelfBTree {

        // 定义根节点
        private Node root = null;

        // 定义树类型, 默认用2-3树
        private int maxElementCount = 2;

        SelfBTree(int maxElementCount) {
            this.maxElementCount = maxElementCount;
        }

        // 添加节点
        void add(int value) {
            // 树为空, 直接构造树
            if (null == root) {
                root = new Node(maxElementCount);
                root.add(value);
                return;
            }
            // 开始寻找有效的节点添加该数据
            add(root, null, value);
        }

        /**
         * 添加数据
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         * @param value 要添加的数据
         */
        private void add(Node currNode, Node parentNode, int value) {
            if (null == currNode) {
                return;
            }
            // 先添加节点
            // 如果当前节点不存在子节点, 说明已经到了叶子节点层, 直接添加
            if (CollectionUtils.isEmpty(currNode.getLstChildrenNode())) {
                currNode.add(value);
            } else { // 在中间节点层时, 比较大小, 挑选合适的路径进行添加
                List<Integer> lstNodeData = currNode.getLstNodeData();
                Node childNode = null;
                for (int i = 0; i < lstNodeData.size(); i++) {
                    // 根据值比较, 获取到对应的子节点列表索引
                    if (value < lstNodeData.get(i)) {
                        childNode = currNode.getLstChildrenNode().get(i);
                        break;
                    }
                    // 已经到最后一个元素了
                    if (i == lstNodeData.size() - 1) {
                        childNode = currNode.getLstChildrenNode().get(i + 1);
                    }
                }
                add(childNode, currNode, value);
            }
            // 节点元素数量等于最大允许元素数量, 则需要向上抽取, 保证叶子节点永远在同一层, 且为满叉树
            if (currNode.getLstNodeData().size() > currNode.getMaxElementCount()) {
                // 获取需要向上抽取的元素
                Integer upData = currNode.getLstNodeData().get(1);

                // 先向上处理父节点
                // 将向上抽取的元素, 添加到父节点中, 并返回添加到父节点的索引
                if (null == parentNode) {
                    parentNode = new Node(maxElementCount);
                    root = parentNode;
                }
                int index = parentNode.add(upData);
                // 遍历当前节点, 将当前节点按索引1进行所有拆分为左节点和右节点,
                List<Integer> lstLeftData = new ArrayList<>(10);
                List<Integer> lstRightData = new ArrayList<>(10);
                for (int i = 0; i < currNode.getLstNodeData().size(); i++) {
                    if (i < 1) {
                        lstLeftData.add(currNode.getLstNodeData().get(i));
                    } else if (i > 1) {
                        lstRightData.add(currNode.getLstNodeData().get(i));
                    }
                }
                Node leftNode = new Node(maxElementCount);
                leftNode.setLstNodeData(lstLeftData);
                Node rightNode = new Node(maxElementCount);
                rightNode.setLstNodeData(lstRightData);
                // 比如对于2-3树, 如果当前父节点有2个数据, 同时有三个子节点
                // 此时中间的子节点(index=1), 添加了一个数据, 节点数据数量为3, 需要提升一个数据到父节点
                // 则父节点的节点数据数量此时为3(暂不提升), 提升上来的数据索引为1(中间节点), 并且对应的子节点应该有四个,
                // 此时对应的子节点数量是3个, 需要对中间子节点进行拆分, 索引0和索引2的子节点不变
                // 索引1的子节点提升了内部索引为1的数据到父节点, 则对该子节点内部按索引1进行拆分为两个节点
                // 将这两个节点放在父节点的子节点索引1和索引2的位置, 原索引0位置不变, 索引2位置后移一位
                List<Node> lstParentChildrenNode = parentNode.getLstChildrenNode();
                // 循环之后, 会直接将currNode挂空, 等待GC回收
                if (CollectionUtils.isEmpty(lstParentChildrenNode)) {
                    lstParentChildrenNode.add(leftNode);
                    lstParentChildrenNode.add(rightNode);
                } else {
                    List<Node> lstNewChildrenNode = new ArrayList<>(10);
                    for (int i = 0; i < lstParentChildrenNode.size(); i++) {
                        if (i == index) {
                            lstNewChildrenNode.add(leftNode);
                            lstNewChildrenNode.add(rightNode);
                            continue;
                        }
                        lstNewChildrenNode.add(lstParentChildrenNode.get(i));
                    }
                    parentNode.setLstChildrenNode(lstNewChildrenNode);
                }

                // 再向下处理子节点
                // 此时当前节点已经被拆分为leftNode和rightNode两个节点,
                // 其中leftNode只包含原索引为1的元素, rightNode包含索引2以及之后的所有元素
                // 此时子节点数量为maxElementCount + 2, 即2-3树此时会有4个子节点
                // 将索引0和索引1的子节点分给左侧节点, 其他子节点分给右侧节点
                List<Node> lstChildChildrenNode = currNode.getLstChildrenNode();
                for (int i = 0; i < lstChildChildrenNode.size(); i++) {
                    if (i <= 1) {
                        leftNode.getLstChildrenNode().add(lstChildChildrenNode.get(i));
                    } else {
                        rightNode.getLstChildrenNode().add(lstChildChildrenNode.get(i));
                    }
                }
            }
        }

    }

    @Data
    static class Node {

        // 节点最大元素数量, 默认表示2-3树
        private int maxElementCount = 2;

        // 节点元素列表
        private List<Integer> lstNodeData;

        // 子节点列表
        private List<Node> lstChildrenNode;

        Node(int maxElementCount) {
            this(maxElementCount, null);
        }

        Node(int maxElementCount, Integer value) {
            this.maxElementCount = maxElementCount;
            lstNodeData = new ArrayList<>(10);
            lstChildrenNode = new ArrayList<>(10);
            if (null != value) {
                add(value);
            }
        }

        int add(Integer value) {
            int index = 0;
            if (CollectionUtils.isEmpty(lstNodeData)) {
                lstNodeData.add(value);
            } else {
                for (index = 0; index < lstNodeData.size(); index++) {
                    if (value < lstNodeData.get(index)) {
                        break;
                    }
                }
                lstNodeData.add(index, value);
            }
            return index;
        }

    }
}

```


### 10.9.3，B+树

#### 10.9.3.1，B+树基本介绍

* B+树是B树的变体，也是一种多路搜索树

* B+树的搜索与B树基本相同，区别在于B树的中间节点存储数据，B+树的中间节点只存储主键索引，并将所有数据存在在叶子节点。同时，相邻的两个叶子节点之间相连接，方便范围查找

* 非叶子节点相当于叶子节点的索引，叶子节点相当于存储数据的数据层，更适合使用在文件索引系统中

  ![1587030339461](E:\gitrepository\study\note\image\dataStructure\1587030339461.png)

### 10.9.4，B*树

* B*树是B+树的变体，在B+树的非根和非叶子节点再增加指针指向

* B*树定义了非叶子节点关键字个数至少为`2 / 3 * M`，即块的最低使用率为三分之二，而B+树块的最低使用率为二分之一

* B*树分配新节点的概率比B+树要低，空间使用率更高

  ![1587030905743](E:\gitrepository\study\note\image\dataStructure\1587030905743.png)

## 10.11，红黑树

* 红黑树部分关于叶子节点的描述都指的是没有子节点的节点，而不是红黑树的Nil节点

### 10.11.1，红黑树引入

* 红黑树是对AVL树的补充。AVL树要求整个树的高度差不能超过1，超过后需要进行左旋或者右旋操作再次对树进行平衡，虽然这样能够解决二叉树退化为链表的缺点，将查询的时间复杂度控制在`O(logN)`，但却不是最佳的；因为AVL树对高度差的控制太严，在需要频繁进行插入/删除的场景中，AVL需要频繁进行树平衡调整，影响整体性能，为了解决这个问题，引入**红黑树**

### 10.11.2，红黑树性质

* 性质1：每个节点要么是黑色，要么是红色

* 性质2：根节点是黑色

* 性质3：每个叶子节点(NIL)是黑色，NIL表示虚拟节点

* 性质4：每个红色节点的两个子节点一定都是黑色，不能有两个红色节点相连

* 性质5：任意一个节点到每个叶子节点的路径都包含数量相同的黑节点，俗称：**黑高**

* 从性质5可以推出，如果一个节点存在黑子节点，那么该节点肯定有两个子节点

* 最后，红黑树并不是一颗完美平衡二叉树，从下图可以看出，根节点的左侧节点明显高出右侧节点两个高度；但是左子树和右子树的黑节点层数是相等的，即任意一个节点到每个叶子节点的路径都包含同样数量的黑色节点，所以红黑树的这种平衡也叫做黑色完美平衡

  ![1587090435851](E:\gitrepository\study\note\image\dataStructure\1587090435851.png)

### 10.11.3，红黑树的基本操作

* **变色**：节点的颜色从红变黑，或者从黑变红

* **左旋**：与AVL树一致

  ![1587090254791](E:\gitrepository\study\note\image\dataStructure\1587090254791.png)

* **右旋**：与AVL树一致

  ![1587090278070](E:\gitrepository\study\note\image\dataStructure\1587090278070.png)

* **查找**：与二分查找完全一致

* **插入**：

  * ***先查找位置并插入，再进行插入后自平衡***

  * 红黑树插入总是以**红色**节点进行插入；如果以黑色节点插入，则会直接破坏红黑树的黑色平衡，每一次插入都要进行自平衡

  * 插入完成节点后，再进行节点进行红黑变色处理及自平衡处理

  * 插入节点概念约定

    ![1587091688046](E:\gitrepository\study\note\image\dataStructure\1587091688046.png)


### 10.11.4，红黑树节点插入情景分析

#### 10.11.4.1，红黑树是空树：

* 这是最简单的一种场景，直接将该节点插入为根节点即可
* 根据**红黑树性质2**：根节点必须是黑色；则将该节点变色为黑色（插入总是是以红色节点进行插入）

#### 10.11.4.2，插入节点的KEY已经存在：直接更新节点值

![1587091847717](E:\gitrepository\study\note\image\dataStructure\1587091847717.png)

#### 10.11.4.3，插入节点的父节点是黑节点

* 节点插入总是以红色节点进行插入，如果父节点为黑节点，则直接插入，不会影响完美黑平衡

  ![1587091949461](E:\gitrepository\study\note\image\dataStructure\1587091949461.png)

#### 10.11.4.4，插入节点的父节点红色

* 根据**红黑树性质4：每一个红色节点的两个子节点一定是黑色节点，两个红色节点不能相连**，此时插入节点的父节点是红色节点，则肯定存在祖父节点为黑色节点，并且，父节点不一定存在兄弟节点；此时对于插入节点，需要分情况进行分析：

* 如果存在叔叔节点，且叔叔节点为红色，则直接进行节点变色，将插入后的初始颜色：**黑红红**修改为**红黑黑**，如果祖父节点为根节点，则最后需要将祖父节点变黑

  ![1587092408254](E:\gitrepository\study\note\image\dataStructure\1587092408254.png)

* 如果不存在叔叔节点或者叔叔节点为黑色，并且插入节点的父节点是祖父节点的左子节点

  * 此时直接插入后节点颜色为**祖父节点：黑，父节点：红，插入节点：红**

  * 如果插入节点是父节点的左子节点，即LL双红

    * 首先进行变色，将父节点设置为黑色，将祖父设置为红色
    * 再进行右旋，将父节点上浮，祖父节点下沉为父节点的右子节点，成为插入节点的兄弟节点

    ![1587092815448](E:\gitrepository\study\note\image\dataStructure\1587092815448.png)

  * 如果插入节点是父节点的右子节点，即LR双红

    * 首先对父节点进行左旋，将以祖父节点为根节点的整颗子树旋转为LL双红型
    * 再进行一次LL双红处理，即变色 -> 右旋

    ![1587093625439](E:\gitrepository\study\note\image\dataStructure\1587093625439.png)

* 如果不存在叔叔节点或者叔叔节点为黑色，并且父节点是祖父节点的右子节点

  * 此时直接插入后节点颜色为**祖父节点：黑，父节点：红，插入节点：红**

  * 如果插入节点是父节点的右子节点，即RR双红

    * 首先进行变色，将父节点设置为黑色，祖父节点设置为红色
    * 再进行左旋，将父节点上浮，祖父节点下沉为父节点的左子节点，成为插入节点的兄弟节点

    ![1587094574892](E:\gitrepository\study\note\image\dataStructure\1587094574892.png)

  * 如果插入节点是父节点的左子节点，即RL双红

    * 首先对父节点进行右旋，将以祖父节点为根节点子树调整为RR双红
    * 再进行一次RR双红处理，变色 -> 左旋

    ![1587104277014](E:\gitrepository\study\note\image\dataStructure\1587104277014.png)

* 一次完整的插入场景如下：

  * 插入节点 7 为 红色叶子节点 8 的左子节点，此时构双红，且存在叔叔节点为红：将祖父节点染红，父节点和叔叔节点染黑
  * 祖父节点染红后，以祖父节点作为当前节点，其父节点为红，且存在叔叔节点为黑，并且父节点为祖父节点的左子节点，当前节点为父节点的右子节点，此时构成LR双红
  * 此时进行一次LR双红处理即可
  
  ![1587105935400](E:\gitrepository\study\note\image\dataStructure\1587105935400.png)

### 10.11.5，红黑树删除情景分析

#### 10.11.5.1，删除操作宏观层面分析

* 节点删除，可对删除节点是否有子节点进行分类讨论
* 对于没有子节点的节点，可以直接删除
* 对于存在一个子节点的节点，可以删除该节点，并让其子节点进行补位
* 对于存在两个子节点的节点，根据树的节点删除逻辑，可以取该节点的前驱节点或者后继节点进行补位，然后删除其前驱或者后继节点，即会转换为删除单子节点或者无子节点
* 节点删除完成后，根据删除节点的颜色，需要进行进一步调整；根据红黑树的黑高原则，<font color=red>如果删除节点是一个黑色节点（此处指的是调整后的删除），则会直接破坏红黑树的黑高原则，需要进行失黑修复</font>

#### 10.11.5.2，删除操作涉及子树关系分析

* 删除黑色节点后，需要对红黑树进行失黑修复，即对树当前结构进行调整，大致可分为三种情况进行调整
* 下面情况分析基于上一步调整后，即删除的节点是单子节点或者无子节点
* **删除节点是红色节点**：<font color=red>此时直接删除，无需进行失黑修复，因为没有删除黑节点</font>
* **删除节点存在子节点**：此时删除节点为黑，子节点为红，删除后，可以将子节点变黑补充删除后造成的失黑，直接黑平衡，不用多做处理，<font color=red>属于删除节点自身的子树内调整</font>
* **删除节点为黑，以父节点为顶点的子树内存在红节点**：该部分删除场景分很多种，后续详细分析，暂时只探讨对树的影响。虽然删除了一个黑节点，但是在该节点所属的子树内存在红色节点，红色节点可以变黑弥补这一失去的黑，<font color=red>属于删除节点所在的子树内的调整，红色节点通过旋转变色后可以弥补该子树缺失的那一个黑</font>
* **删除节点为黑，以父节点为顶点的子树内不存在红节点**：<font color=red>此时属于全黑场景，当前所属子树已经没有红色节点可以弥补这一缺失，此时单纯在该子树内进行调整是不可能的，所以可以将子树的另一分支，即删除节点的兄弟节点染红，这样对于当前子树是少了一层黑，然后递归向上，对父节点进行模拟删除处理，看是否可以进行黑平衡，直到根节点，如果还是不能平衡（全黑树），则该树的黑高最终会减1，多出几个红节点</font>

#### 10.11.5.2，删除场景具体分析

##### 1， 删除节点为红色节点

* 此时该节点一定是叶子节点

* 根据红黑树性质4：每个红色节点的两个子节点一定都是黑色，不能有两个红色节点相连；如果该节点存在子节点，其节点一定为黑

* 根据红黑树性质5的推论：如果一个节点存在黑子节点，那么该节点肯定有两个子节点；<font color=red>目前该节点最多只能有一个子节点，具体看前边分析</font>

* 综上，该节点只能是叶子节点！

* 此时可以直接删除，因为删除该红色节点不会造成红黑树失黑，可以直接删除掉

  ![1595232380729](E:\gitrepository\study\note\image\dataStructure\1595232380729.png)

##### 2，删除节点为黑色节点，这种情况下场景较为复杂，可以分为删除节点有子节点，删除节点无子节点等三种情况

###### 2.1，删除节点存在子节点

* 同样，删除节点存在子节点，并且也只存在一个子节点，此时该节点必定为红色，<font color=red>原因可分析红黑树细致</font>

* 此时删除该黑色节点，会导致删除节点所在的这条链黑色节点数量少1，违反红黑树黑高原则

* 但是在该节点下存在一个红色的子节点，可以通过该节点变黑来弥补删除节点造成的失黑现象，完成失黑修复

* <font color=red>用子节点替代删除节点的位置，并将颜色染黑</font>

  ![1595239417260](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1595239417260.png)

###### 2.2，删除节点没有子节点，兄弟节点为红

* 从这部分以后，以删除节点为顶点的子树已经没有多余节点可以进行失黑修复，所以必须借助父节点为顶点的子树，即从兄弟节点处借侄子节点来弥补黑节点缺失

* 兄弟节点为红，则父节点必然为黑，以父节点为顶点的子树黑高为2，则兄弟节点下必然有两个黑节点（黑节点先可能还有红节点）

* 此时以父亲节点为中心节点进行旋转，将父亲节点变红下沉，兄弟节点变黑上浮，兄弟节点的一侧节点挂到父亲节点下

* 旋转后，删除的一侧会有一个红色的父节点和一个黑色的叶子节点，另外一侧有一个黑色的无子节点，且父节点为黑色，此时依旧没有实现黑平衡，但是问题已经转换为**删除节点为黑，兄弟节点为黑，父节点为红的情况**，在下一步进行继续分析

  ![1595233573200](E:\gitrepository\study\note\image\dataStructure\1595233573200.png)

###### 2.3，删除节点没有子节点，兄弟节点为黑

* 这种场景下情况巨多，但是可以分为四种场景进行分析
* <font color=red>此处以删除左子节点为例，兄弟节点为右子节点，删除右子节点相反即可</font>

###### 2.3.1，兄弟节点为黑，右子节点为红，左子节点颜色随意，父节点颜色随意

* <font color=red>首先这一场景可能延续了2.2场景遗留的问题</font>

* 兄弟节点的右子节点如果存在，则必然为红

* 此时限制右子节点为红，是为了构成父节点 - 兄弟节点 - 右子节点的RR结构，只需要一次旋转变色即可完成

* 删除之后，左侧没有节点，父节点存在且颜色不定，右侧有一个为黑的兄弟节点，和至少一个为红的右子节点，*左子节点可能也存在并且为红*

* 所以在以父节点为顶点的当前子树中，是可以通过节点旋转和变色构成黑平衡的，此时进行选择变色处理

* <font color=red>先以父节点为中心节点进行右</font>

* <font color = red>然后将兄弟节点染为父节点颜色，父节点染黑，兄弟节点的右子节点染黑，兄弟节点的左子节点如果存在，此时会挂到父节点的右子节点，并依旧为红色</font>

  ![1595235597516](E:\gitrepository\study\note\image\dataStructure\1595235597516.png)

###### 2.3.2，兄弟节点为黑，左子节点为红，右子节点颜色随意，父节点颜色随意

* <font color=red>首先这一场景可能延续了2.2场景遗留的问题</font>

* 该场景下兄弟节点的右子节点不存在，如果存在会在上一个场景进行分析

* 此时父节点 - 兄弟节点 - 兄弟节点左子节点构成了RL结构，需要两次旋转再变色才可以完成

* <font color=red>先以兄弟节点为中心节点进行右旋，此时不变色，旋转完成后，原来的父节点 - 黑兄弟节点 - 红兄弟节点左子节点的RL结构变成了 父节点 - 红兄弟节点左子节点 - 黑兄弟节点的RR结构</font>

* <font color=red>然后以父节点为中心节点进行右旋，进行第二次旋转，此时父节点到左边，侄子节点到父节点的位置，兄弟节点位置较之前不变</font>

* <font color=red>最后进行变色，将侄子节点变为父节点原来的颜色， 将父节点染黑</font>

* 先旋转后变色还是先变色后选择，这部分可以根据个人来玩

  ![1595237247961](E:\gitrepository\study\note\image\dataStructure\1595237247961.png)

###### 2.3.3，兄弟节点不存在子节点，父节点为黑色

- <font color=red>首先这一场景可能延续了2.2场景遗留的问题</font>

- 在这种场景下，以黑删除节点，黑兄弟节点，红父亲节点组成的子树黑高为1，此时删除一个黑子节点，剩余一个黑子节点和红父亲节点不符合黑高规则；但是，通过变色可以让剩余的子树依旧满足1层黑高

- <font color=red>此时将父亲节点染黑，兄弟节点染红，即可满足黑高不变，即进行颜色互换</font>，这种场景相对比较简单

  ![1595234346822](E:\gitrepository\study\note\image\dataStructure\1595234346822.png)

###### 2.3.4，兄弟节点不存在子节点，父节点为黑

* 兄弟节点不存在子节点，父节点为黑，此时兄弟节点必定为黑
* 这时候是全黑场景，在以父节点为顶点的子树中已经没有节点可以进行失黑补充
* <font color=red>此时为了在该子树中进行黑平衡，只能将兄弟节点染红，该子树黑高从原来的2变为1</font>
* <font color=red>该子树黑节点减1后，将对整棵树的黑高产生影响，此时需要递归向上处理，直到遇到可以进行失黑修复的子树节点（存在红节点的子树）；如果不存在，说明该树为全黑树，则会一直递归到根节点，则最终整棵树的黑高减1，并且全黑树中会调整出几个红节点</font>

* 构造一个简单的全黑树

  > 按顺序增节点：200，100，300, 50, 150，250, 400，20,70,120，180,220，280,350,450，10
  >
  > 按顺序删节点：450,350,280，220，180,120，10,20,70,

![1595237925242](E:\gitrepository\study\note\image\dataStructure\1595237925242.png)

* <font color=red>至此，红黑树删除分析全部完成</font>

### 10.11.6，红黑树代码

```java
package com.self.datastructure.tree.redblacktree;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.Data;

/**
 * 红黑树
 *
 * @author PJ_ZHANG
 * @create 2020-04-17 9:15
 **/
public class RedBlackTree {

    public static void main(String[] args) {
        SelfRedBlackTree selfRedBlackTree = new SelfRedBlackTree();
        // 添加数据层, 这一组数据为了构造全黑树
        selfRedBlackTree.add(200);
        selfRedBlackTree.add(100);
        selfRedBlackTree.add(300);
        selfRedBlackTree.add(50);
        selfRedBlackTree.add(150);
        selfRedBlackTree.add(250);
        selfRedBlackTree.add(4000);
        selfRedBlackTree.add(20);
        selfRedBlackTree.add(70);
        selfRedBlackTree.add(120);
        selfRedBlackTree.add(180);
        selfRedBlackTree.add(220);
        selfRedBlackTree.add(280);
        selfRedBlackTree.add(350);
        selfRedBlackTree.add(4500);
        selfRedBlackTree.add(10);
        selfRedBlackTree.delete(4500);
        selfRedBlackTree.delete(350);
        selfRedBlackTree.delete(280);
        selfRedBlackTree.delete(220);
        selfRedBlackTree.delete(220);
        selfRedBlackTree.delete(180);
        selfRedBlackTree.delete(120);
        selfRedBlackTree.delete(10);
        selfRedBlackTree.delete(20);
        selfRedBlackTree.delete(70);
        selfRedBlackTree.delete(50);
    }

    static class SelfRedBlackTree {
        private Node root = null;

        /**
         * 删除节点
         * @param value
         */
        public boolean delete(int value) {
            // 获取有效的数据, 节点不存在, 返回删除失败
            Node delNode = findNode(this.root, value);
            if (null == delNode) {
                return false;
            }
            return doDeleteNode(delNode);
        }

        private boolean doDeleteNode(Node delNode) {
            // 进行节点删除, 此处需要分三个大场景进行判断
            // 1, 删除节点存在两个子节点
            // 可以转变为删除右侧最左节点 即后继节点
            if (null != delNode.getLeftNode() && null != delNode.getRightNode()) {
                // 取右侧最左节点
                Node nextNode = delNode.getRightNode();
                for (;null != nextNode.getLeftNode(); nextNode = nextNode.getLeftNode());
                // 覆盖值
                delNode.setValue(nextNode.getValue());
                // 再掉一次删除, 此处注意不是递归, 只是再掉一次, 第二次进来不可能是双子节点
                return doDeleteNode(nextNode);
            } else {
                // 2, 删除节点为单子节点
                // 3, 删除节点没有子节点
                // 先对删除节点为根情况进行分析
                // 为根时, 就是删除黑子单子节点或者直接删根,
                if (null == delNode.getParentNode()) {
                    // 子节点可能存在也可能不存在
                    Node childNode = null == delNode.getLeftNode() ? delNode.getRightNode() : delNode.getLeftNode();
                    // 如果存在, 设置子节点为根节点
                    this.root = childNode;
                    // 子节点存在时, 进行失黑修复, 将子节点设置为根节点并且颜色为黑
                    // 子节点不存在时, 树直接就空了
                    if (null != childNode) {
                        childNode.setParentNode(null);
                        childNode.setRed(false);
                    }
                    return true;
                } else {
                    // 父节点, 保存一份, 防止引用传递丢失
                    Node parentNode = null;
                    // 删除节点不是根节点
                    // 将删除节点挂空, 对父节点的子节点和子节点的父节点进行替换
                    // 取子节点, 子节点有一个或者没有, 修改子节点的父节点
                    Node childNode = null == delNode.getLeftNode() ? delNode.getRightNode() : delNode.getLeftNode();
                    if (null != childNode) {
                        childNode.setParentNode(delNode.getParentNode());
                    }
                    // 取父节点, 判断当前节点是父节点的左子还是右子节点
                    parentNode = delNode.getParentNode();
                    if (delNode == parentNode.getLeftNode()) {
                        parentNode.setLeftNode(childNode);
                    } else if (delNode == parentNode.getRightNode()) {
                        parentNode.setRightNode(childNode);
                    }

                    // 删除完成后, 如果删除节点为黑节点, 进行失黑修复
                    // 如果为红节点, 则不用处理
                    // 此处是删除场景1, 删除节点为红色节点
                    // 此时该节点一定是叶子节点
                    // 如果该节点存在单子节点, 因为不能双红, 所以该子节点一定为黑
                    // 如果存在一个黑子节点, 则必须存在两个黑子节点, 否在违反黑高
                    // 所以该节点一定是红色节点
                    // 此时直接删除该节点, 因为删除不包含子节点的红色节点不影响红黑树性质, 可以直接删除, 不用考虑修复
                    if (!delNode.isRed()) {
                        fixedLostBlack(delNode, parentNode);
                    }
                }
            }
            return true;
        }

        /**
         * 黑色节点删除, 进行失黑修复
         * @param delNode
         * @param parentNode 原父节点, 只做保留原始数据和传参用, 获取树中的父节点, 可以直接getParent
         */
        private void fixedLostBlack(Node delNode, Node parentNode) {
            // 场景二: delNode存在单子节点,
            // 因为delNode为黑, 根据红黑树原则, 其单子节点一定是红节点
            if (null != delNode.getLeftNode() || null != delNode.getRightNode()) {
                // 删除节点后会导致黑高少1, 此时将红色变黑, 则黑高
                Node childNode = null == delNode.getLeftNode() ? delNode.getRightNode() : delNode.getLeftNode();
                childNode.setRed(false);
                return;
            }

            // 循环是对全黑情况下的向上递归处理
            // 如果在递归过程中遇到了红色父节点或者兄弟节点会直接平衡完成, 无需继续处理
            // 否则最终递归到根节点
            while (this.root != delNode) {
                Node brotherNode = null;
                // 场景三: delNode不存在单子节点, 此时在它的子树内处理已经不可能, 需要连接其他子树
                // 此处分两种场景进行处理, 分别为当前节点为左节点和右节点
                // 走到此处, 说明删除节点没有子节点, 则父节点的对应位置为null
                if (null == parentNode.getLeftNode() || delNode == parentNode.getLeftNode()) {
                    // 当前节点为左侧, 兄弟节点为右侧
                    brotherNode = parentNode.getRightNode();
                    // 先考虑兄弟节点为红的情况
                    if (brotherNode.isRed()) {
                        // 兄弟节点为红, 则父节点必定为黑
                        // 兄弟节点必定存在两个为黑的子节点
                        // 在删除之前, 该子树上的黑节点一定是平衡的
                        // 此时以父节点为中心节点进行左旋转
                        leftRotateWithoutChange(brotherNode, parentNode);
                        // 再将父节点变为红色, 兄弟节点变为黑色
                        // 即是旋转后的兄弟节点变为黑色, 兄弟节点的左子节点变红
                        brotherNode.setRed(false);
                        brotherNode.getLeftNode().setRed(true);
                        // 重新赋值父节点和兄弟节点
                        parentNode = brotherNode.getLeftNode();
                        brotherNode = parentNode.getRightNode();
                        // 此时兄弟节点的左侧节点挂到父节点的右侧节点, 且为黑色
                        // 那么对于下沉下来变为红色的父节点来说, 此时存在一个删除掉的黑色节点和旋转过来的黑色节点
                        // 这时候问题转化为删除节点为黑, 且无子节点, 兄弟节点为黑的情况了
                        // 留到下一种情况继续处理
                    }
                    // 兄弟节点为黑, 无论上一个循环有没有走到, 都会走到这一步
                    // 如果走到了上一步, 那上一步也会遗留一个问题到这一步解决
                    if (!brotherNode.isRed()) {
                        // case1: 兄弟节点不存在子节点, 父节点为红色
                        // 这种场景先是一种情况, 然后会延续处理上一个情况遗留的问题
                        if (null == brotherNode.getRightNode() && null == brotherNode.getLeftNode()
                                && brotherNode.getParentNode().isRed()) {
                            // 父节点为红色时, 兄弟节点必定为黑
                            // 该子树只有一层黑节点, 删除当前节点, 剩下红色父节点和黑色兄弟节点
                            // 可以将父节点和兄弟节点的颜色互换, 保证该子树黑节点层数不变
                            boolean brotherColor = brotherNode.isRed();
                            brotherNode.setRed(parentNode.isRed());
                            parentNode.setRed(brotherColor);
                            return; // 处理完成可以退出
                        }
                        // case2: 兄弟节点右子节点为红, 左子节点颜色无所谓(要么为空, 要么为空), 父节点颜色随意
                        // 此时从父节点 - 兄弟节点 - 兄弟节点右子节点构成RR, 需一次旋转
                        if (null != brotherNode.getRightNode() && brotherNode.getRightNode().isRed()) {
                            // 删除之后, 左侧没有黑节点, 右侧有一个兄弟节点为黑, 有一个兄弟节点的右子节点为红, 且父节点颜色随意
                            // 先变色, 将兄弟节点染为父节点颜色, 将父节点染黑, 将右子节点染黑, 然后以父节点为中心进行左旋
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            brotherNode.setRed(parentColor);
                            brotherNode.getRightNode().setRed(false);
                            // 再进行左旋
                            leftRotateWithoutChange(brotherNode, brotherNode.getParentNode());
                            return; // 处理完成
                        }
                        // case3: 兄弟节点左子节点为红, 左子节点无所谓(要么为空, 要么为红), 父节点颜色随意
                        // 此时从父节点 - 兄弟节点 - 兄弟节点左子节点构成RL, 需两次旋转
                        if (null != brotherNode.getLeftNode() && brotherNode.getLeftNode().isRed()) {
                            // 先以兄弟节点为中心点进行右旋, 旋转暂时不变颜色
                            rightRotateWithoutChange(brotherNode.getLeftNode(), brotherNode);
                            // 此处注意旋转后位置已经变化
                            // 此时父节点的右子节点变为原兄弟节点的左子节点, 父节点右子节点的右子节点变为原兄弟节点
                            // 将父节点染黑, 父节点的右子节点变为父节点原来的颜色, 再以父节点为中心进行左旋, 结果同上
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            parentNode.getRightNode().setRed(parentColor);
                            leftRotateWithoutChange(parentNode.getRightNode(), parentNode);
                            return; // 处理完成
                        }
                    }
                } else if (null == parentNode.getRightNode() || delNode == parentNode.getRightNode()) {
                    // 为右节点处理, 与左节点相反
                    brotherNode = parentNode.getLeftNode();
                    if (brotherNode.isRed()) {
                        rightRotateWithoutChange(brotherNode, parentNode);
                        brotherNode.setRed(false);
                        brotherNode.getRightNode().setRed(true);
                    }
                    if (!brotherNode.isRed()) {
                        if (null == brotherNode.getRightNode() && null == brotherNode.getLeftNode()
                                && brotherNode.getParentNode().isRed()) {
                            boolean brotherColor = brotherNode.isRed();
                            brotherNode.setRed(parentNode.isRed());
                            parentNode.setRed(brotherColor);
                            return;
                        }
                        if (null != brotherNode.getLeftNode() && brotherNode.getLeftNode().isRed()) {
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            brotherNode.setRed(parentColor);
                            brotherNode.getLeftNode().setRed(false);
                            rightRotateWithoutChange(brotherNode, brotherNode.getParentNode());
                            return;
                        }
                        if (null != brotherNode.getRightNode() && brotherNode.getRightNode().isRed) {
                            leftRotateWithoutChange(brotherNode.getRightNode(), brotherNode);
                            boolean parentColor = parentNode.isRed();
                            parentNode.setRed(false);
                            parentNode.getLeftNode().setRed(parentColor);
                            rightRotateWithoutChange(parentNode.getLeftNode(), parentNode);
                            return;
                        }
                    }
                }
                // case4: 兄弟节点不存在子节点, 父节点为黑色
                // 父节点为黑, 当前节点为黑, 兄弟节点不存在子节点, 则兄弟节点必然为黑
                // 后续递归向上进行全黑处理时, 兄弟节点是肯定存在子节点的
                // 这里只对兄弟节点颜色进行判断, 如果兄弟节点存在子节点符合其他条件, 则在上面分支中已经判断完成
                if (!brotherNode.isRed() && !parentNode.isRed()) {
                    // 此时就是全黑场景, 左侧子树删除一个黑节点, 此时黑色少1, 右侧也没有多余节点补充, 此时只能递归调整数的黑高
                    // 现将兄弟节点改为红色
                    brotherNode.setRed(true);
                    // 再递归向上一直调整各个子树, 直到整个树的黑高平衡
                    delNode = parentNode;
                    parentNode = parentNode.getParentNode();
                }
            }
        }

        /**
         * 查找指定节点
         * @param node
         * @param value
         * @return
         */
        private Node findNode(Node node, int value) {
            if (null == node) {
                return null;
            }
            if (value < node.getValue()) {
                return findNode(node.getLeftNode(), value);
            } else if (value > node.getValue()) {
                return findNode(node.getRightNode(), value);
            } else {
                return node;
            }
        }

        /**
         * 添加数据
         * @param value 数据值
         */
        public void add(Integer value) {
            // 根节点为空, 初始化根节点, 并设置颜色为黑色
            if (null == root) {
                root = new Node(value);
                root.setRed(false);
                return;
            }
            // 根节点不为空, 添加节点
            doAdd(root, value);
        }

        /**
         * 添加红黑树节点数据
         * @param parentNode 父节点
         * @param value 插入数据
         */
        private void doAdd(Node parentNode, Integer value) {
            if (null == parentNode) {
                return;
            }
            // 先添加节点
            if (parentNode.getValue() > value) {
                if (null != parentNode.getLeftNode()) {
                    doAdd(parentNode.getLeftNode(), value);
                } else {
                    Node newNode = new Node(value);
                    newNode.setParentNode(parentNode);
                    parentNode.setLeftNode(newNode);
                    balanceTree(newNode, parentNode);
                }
            } else if (parentNode.getValue() < value) {
                if (null != parentNode.getRightNode()) {
                    doAdd(parentNode.getRightNode(), value);
                } else {
                    Node newNode = new Node(value);
                    newNode.setParentNode(parentNode);
                    parentNode.setRightNode(newNode);
                    balanceTree(newNode, parentNode);
                }
            }
        }

        /**
         * 平衡红黑树
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         */
        private void balanceTree(Node currNode, Node parentNode) {
            // 当前节点是红节点, 父节点是黑节点
            // 直接插入, 不需要变色和旋转
            if (currNode.isRed() && !parentNode.isRed()) {
                return;
            }
            // 当前节点是红节点, 父节点是红节点
            // 此时一定存在祖父节点是黑节点
            // 需要分情况进行处理
            if (currNode.isRed() && parentNode.isRed()) {
                // 如果存在叔叔节点并且叔叔节点为红色
                // 将祖父节点变红, 父节点和叔叔节点变黑
                Node uncleNode = parentNode == parentNode.getParentNode().getLeftNode()
                        ? parentNode.getParentNode().getRightNode() : parentNode.getParentNode().getLeftNode();
                if (null != uncleNode && uncleNode.isRed()) {
                    parentNode.getParentNode().setRed(true);
                    parentNode.setRed(false);
                    uncleNode.setRed(false);
                    // 如果祖父节点是根节点, 则直接染黑
                    if (root == parentNode.getParentNode()) {
                        parentNode.getParentNode().setRed(false);
                    } else { // 祖父节点不是根节点, 以祖父节点作为当前节点继续往上处理
                        balanceTree(parentNode.getParentNode(), parentNode.getParentNode().getParentNode());
                    }
                } else { // 表示叔叔节点不存在, 或者叔叔节点为黑
                    // 如果插入节点的父节点是祖父节点的左子节点
                    if (parentNode == parentNode.getParentNode().getLeftNode()) {
                        // 如果当前节点是父节点左子节点, 则构成LL双红
                        // LL双红, 直接右旋处理
                        if (currNode == parentNode.getLeftNode()) {
                            rightRotate(parentNode, parentNode.getParentNode());
                        }
                        // 如果当前节点是父节点的右子节点, 则构成LR双红
                        // LR双红, 先左旋, 再右旋
                        else if (currNode == parentNode.getRightNode()) {
                            leftRotateWithoutChange(currNode, parentNode);
                            // 左旋后, 当前节点已经变为父节点, 父节点为当前节点的左子节点
                            rightRotate(currNode, currNode.getParentNode());
                        }
                    }
                    // 如果插入节点的父节点是祖父节点的右子节点
                    else if (parentNode == parentNode.getParentNode().getRightNode()) {
                        // 如果当前节点是父节点的右子节点, 则构成RR双红
                        // RR双红, 直接左旋处理
                        if (currNode == parentNode.getRightNode()) {
                            leftRotate(parentNode, parentNode.getParentNode());
                        }
                        // 如果当前节点是父节点的左子节点, 则构成RL双红
                        // RL双红, 先左旋, 再右旋
                        else if (currNode == parentNode.getLeftNode()) {
                            rightRotateWithoutChange(currNode, parentNode);
                            // 右旋后, 当前节点表示父节点, 父节点为当前节点右子节点
                            leftRotate(currNode, currNode.getParentNode());
                        }
                    }
                }

            }
        }

        /**
         * 变色左旋
         * 对于RR双红结构, 需要先变色再左旋, 保证树的完美黑平衡
         * 变色: 将父节点变为黑色, 祖父节点变为红色(祖父节点必定为黑色)
         * 左旋: 将父节点上浮, 祖父节点下沉
         *
         * @param parentNode 父节点
         * @param grandpaNode 祖父节点
         */
        private void leftRotate(Node parentNode, Node grandpaNode) {
            // 变色, 父节点变为黑色, 祖父节点变为红色
            parentNode.setRed(false);
            grandpaNode.setRed(true);
            // 左旋
            leftRotateWithoutChange(parentNode, grandpaNode);
        }

        /**
         * 变色右旋
         * 对于LL双红结构, 需要先变色再右旋, 保证树的完美黑平衡
         * 变色: 将父节点变为黑色, 祖父节点变为红色(此时祖父节点必定为黑色)
         * 右旋: 将父节点上浮, 祖父节点下沉,
         *
         * @param parentNode 父节点
         * @param grandpaNode 祖父节点
         */
        private void rightRotate(Node parentNode, Node grandpaNode) {
            // 变色, 父节点变黑, 祖父节点变红
            parentNode.setRed(false);
            grandpaNode.setRed(true);
            // 右旋
            rightRotateWithoutChange(parentNode, grandpaNode);
        }

        /**
         * 不变色右旋
         * 对于RL双红, 需要先将树结构转换为RR双红
         * 该部分转换只旋转不变色
         * 将父节点下沉, 变为当前节点的右子节点
         * 将当前节点上浮, 变为祖父节点的右子节点
         * 将当前节点的右子节点变为父节点的左子节点
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         */
        private void rightRotateWithoutChange(Node currNode, Node parentNode) {
            // 构造父节点为节点
            Node newNode = new Node(parentNode.getValue(), parentNode.isRed());
            // 父节点的右子节点不变
            newNode.setRightNode(parentNode.getRightNode());
            if (null != parentNode.getRightNode()) {
                parentNode.getRightNode().setParentNode(newNode);
            }
            // 父节点的左子节点为当前节点的右子节点
            newNode.setLeftNode(currNode.getRightNode());
            if (null != currNode.getRightNode()) {
                currNode.getRightNode().setParentNode(newNode);
            }
            // 当前节点的右子节点为新节点, 当前节点的左子节点不变
            currNode.setRightNode(newNode);
            newNode.setParentNode(currNode);
            // 当前节点的父节点, 为父节点的父节点
            currNode.setParentNode(parentNode.getParentNode());
            // 如果祖父节点为根节点, 则替换根节点为父节点
            if (root == parentNode) {
                root = currNode;
            }
            // 如果祖父节点不为根节点, 则替换祖父父节点的右子节点为父节点
            else {
                parentNode.getParentNode().setRightNode(currNode);
            }
            // 这样会直接将原来的parentNode挂空, 等待GC回收
        }

        /**
         * 不变色左旋
         * 对于LR双红, 需要先将树结构转换为LL双红
         * 该部分转换只旋转不变色
         * 将父节点下沉, 变为当前节点的左子节点
         * 将当前节点上浮, 变为祖父节点的左子节点
         * 将当前节点的左子节点变为父节点的右子节点
         *
         * @param currNode 当前节点
         * @param parentNode 父节点
         */
        private void leftRotateWithoutChange(Node currNode, Node parentNode) {
            // 构造父节点为节点
            Node newNode = new Node(parentNode.getValue(), parentNode.isRed());
            // 父节点的左子节点不变
            newNode.setLeftNode(parentNode.getLeftNode());
            if (null != parentNode.getLeftNode()) {
                parentNode.getLeftNode().setParentNode(newNode);
            }
            // 父节点的右子节点为当前节点的左子节点
            newNode.setRightNode(currNode.getLeftNode());
            if (null != currNode.getLeftNode()) {
                currNode.getLeftNode().setParentNode(newNode);
            }
            // 当前节点的左子节点为新节点, 当前节点的右子节点不变
            currNode.setLeftNode(newNode);
            newNode.setParentNode(currNode);
            // 当前节点的父节点, 为父节点的父节点
            currNode.setParentNode(parentNode.getParentNode());
            if (root == parentNode) {
                root = currNode;
            }
            // 如果祖父节点不为根节点, 则替换祖父父节点的左子节点为父节点
            else {
                parentNode.getParentNode().setLeftNode(currNode);
            }
            // 这样会直接将原来的parentNode挂空, 等待GC回收
        }
    }

    @Data
    static class Node {

        // 数据
        private int value;

        // 左子节点
        private Node leftNode;

        // 右子节点
        private Node rightNode;

        // 父节点
        private Node parentNode;

        // 是否红色节点
        private boolean isRed = true;

        Node(int value) {
            this.value = value;
        }

        Node(int value, boolean isRed) {
            this.value = value;
            this.isRed = isRed;
        }

        Node (Node node) {
            this.value = node.getValue();
            this.leftNode = node.getLeftNode();
            this.rightNode = node.getRightNode();
            this.parentNode = node.getParentNode();
            this.isRed = node.isRed();
        }

    }
}
```

# 11，图

## 11.1，图的基本概念

### 11.1.1，图的基本介绍

* 线性表局限于一个直接前驱和一个直接后继的关系
* 树也只能有一个直接前驱也就是父节点
* 当需要多对多的关系的时候，就应该用到图

### 11.1.2，图的常用概念

* **顶点**（Vertex）

* **边（Edge）**

* 路径

* 无向图

  ![1587190668812](E:\gitrepository\study\note\image\dataStructure\1587190668812.png)

* 有向图

* 带权图

  ![1587190681185](E:\gitrepository\study\note\image\dataStructure\1587190681185.png)

### 11.1.3，图的表示方式

* 图的表示方式有两张：二维数组表示（邻接矩阵），链表表示（邻接表）

* 邻接矩阵：是表示图形中顶点之间相邻关系的矩阵，对于N个顶点的图而言，矩阵的`row`和`column`分别表示n个点

  ![1587193943842](E:\gitrepository\study\note\image\dataStructure\1587193943842.png)

* 邻接表

  * 邻接矩阵需要为每个顶点分配n个边的空间，但是其实很多边在图中不存在，会造成额外的空间浪费
  * 邻接表的实现只关心存在的边，不关心不存在的边，不存在空间浪费，邻接表由数组和链表组成

  ![1587194053253](E:\gitrepository\study\note\image\dataStructure\1587194053253.png)

## 11.2.，图的深度优先遍历（Depth First Search - DFS）

* 所谓图的遍历，即是对图的节点遍历。一个图有多个节点，如何遍历这些节点，需要特定的策略，一般分为***深度优先遍历***和***广度优先遍历***两种

### 11.2.1，基本思想

* 深度优先遍历，从初始访问节点出发，首先访问它的第一个邻接节点；然后以该邻接节点作为初始节点，继续访问它的第一个邻接节点；以此类推
* 如果第一个邻接节点不存在或者已经递归遍历完成，则获取第二个邻接节点作为初始节点
* 由此可以看到，深度优先遍历是先向纵深挖掘遍历，纵深遍历完成后，再进行横向遍历
* 显示，深度优先遍历时一个递归的过程

### 11.2.2，算法步骤

1. 访问初始节点`index`，并将该节点标记为已访问

2. 查找初始节点`index`的第一个邻接节点`nextIndex`

3. 若`nextIndex`存在，则继续执行第四步；若`nextIndex`不存在，则退回第二步，继续查找`index`的下一个邻接节点，继续进行第三步判断
4. 若`nextIndex`未被访问，则对`nextIndex`进行深度优先遍历，即从第一步递归
5. 若`nextIndex`已经被访问，则回到第二步，继续查找`index`的下一个临界点，继续判断

### 11.2.3，代码实现

![1593093628336](E:\gitrepository\study\note\image\dataStructure\1593093628336.png)

* 深度优先遍历结果为：1 -> 2 -> 4 -> 8 -> 5 -> 3 -> 6 -> 7

```java
package com.self.datastructure.chart;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 图基本入门
 *
 * @author PJ_ZHANG
 * @create 2020-04-20 16:16
 **/
public class QuickStartChart {

    public static void main(String[] args) {
        MyChart myChart = createChart(8);
        System.out.println("深度优先遍历: ");
        myChart.dfs();
    }

    /**
     * 创建图
     * @param vertexNum 顶点数量
     * @return
     */
    private static MyChart createChart(int vertexNum) {
        MyChart myChart = new MyChart(vertexNum);
        for (int i = 1; i <= vertexNum; i++) {
            myChart.addVertex(String.valueOf(i));
        }
        myChart.addEdge(myChart.indexOf("1"), myChart.indexOf("2"), 1);
        myChart.addEdge(myChart.indexOf("1"), myChart.indexOf("3"), 1);
        myChart.addEdge(myChart.indexOf("2"), myChart.indexOf("4"), 1);
        myChart.addEdge(myChart.indexOf("2"), myChart.indexOf("5"), 1);
        myChart.addEdge(myChart.indexOf("3"), myChart.indexOf("6"), 1);
        myChart.addEdge(myChart.indexOf("3"), myChart.indexOf("7"), 1);
        myChart.addEdge(myChart.indexOf("4"), myChart.indexOf("8"), 1);
        myChart.addEdge(myChart.indexOf("5"), myChart.indexOf("8"), 1);
        return myChart;
    }

    /**
     * 自定义图类进行处理
     */
    static class MyChart {

        /**
         * 顶点数量
         */
        private int vertexNum;

        /**
         * 顶点列表
         */
        private List<String> lstVertex;

        /**
         * 顶点路径
         */
        private int[][] vertexPathArray;

        /**
         * 边数量
         */
        private int edgeNum;

        /**
         * 是否已经被访问
         */
        private boolean[] isVisited;

        MyChart(int vertexNum) {
            this.vertexNum = vertexNum;
            lstVertex = new ArrayList<>(vertexNum);
            vertexPathArray = new int[vertexNum][vertexNum];
            isVisited = new boolean[vertexNum];
        }

        /**
         * 添加顶点, 此处不涉及扩容
         *
         * @param vertex 添加的顶点
         */
        void addVertex(String vertex) {
            if (vertexNum == lstVertex.size()) {
                throw new ArrayIndexOutOfBoundsException("数组已满");
            }
            lstVertex.add(vertex);
        }

        /**
         * 添加多个顶点
         *
         * @param vertexArr 顶点数组, 可变参数
         */
        void addAllVertex(String ... vertexArr) {
            if (vertexNum < lstVertex.size() + vertexArr.length) {
                throw new ArrayIndexOutOfBoundsException("数组已满");
            }
            lstVertex.addAll(Arrays.asList(vertexArr));
        }

        /**
         * 返回顶点所在的下标
         *
         * @param vertex 目标顶点
         * @return 返回下标
         */
        int indexOf(String vertex) {
            return lstVertex.indexOf(vertex);
        }

        /**
         * 添加边
         *
         * @param xIndex 横坐标
         * @param yIndex 纵坐标
         * @param weight 边的权重
         */
        void addEdge(int xIndex, int yIndex, int weight) {
            if (xIndex >= vertexNum || yIndex >= vertexNum) {
                throw new IndexOutOfBoundsException("索引越界");
            }
            vertexPathArray[xIndex][yIndex] = weight;
            vertexPathArray[yIndex][xIndex] = weight;
            edgeNum++;
        }

        /**
         * 获取边数量
         *
         * @return 返回边数量
         */
        int getEdgeNum() {
            return edgeNum;
        }

        /**
         * 展示图
         */
        void showChart() {
            for (int[] array : vertexPathArray) {
                System.out.println(Arrays.toString(array));
            }
        }

        /**
         * 深度优先遍历
         * 从第一个顶点开始进行遍历, 遍历过的顶点标记为已经遍历
         * 先获取遍历该顶点的下一个邻接顶点
         * 如果不存在, 则继续第二个未遍历顶点开始
         * 如果存在, 判断该邻接顶点是否已经遍历过
         * 如果没有遍历过, 则继续深度遍历该顶点(递归)
         * 如果已经遍历过, 则继续寻找下一个邻接顶点
         */
        void dfs() {
            for (int i = 0; i < lstVertex.size(); i++) {
                // 从第一个节点开始进行遍历
                // 先访问初始节点
                if (!isVisited[i]) {
                    dfs(i);
                }
            }
        }

        private void dfs(int index) {
            // 输出当前遍历的节点, 并标记为已访问
            System.out.print(lstVertex.get(index) + " -> ");
            isVisited[index] = true;
            // 获取它的第一个邻接节点进行访问
            int nextIndex = getFirstNeighbor(index);
            // 不等于-1说明存在下一个节点, 继续进行处理
            // 如果等于-1, 说明此次遍历结束, 交由主函数进行下一个节点遍历
            while (nextIndex != -1) {
                if (!isVisited[nextIndex]) {
                    // 如果没有被访问, 则继续深度循环遍历进行处理
                    dfs(nextIndex);
                }
                // 如果已经被访问了, 则查找nextIndex的下一个临界节点
                nextIndex = getNextNeighbor(index, nextIndex);
            }
        }

        /**
         * 获取下一个邻接节点
         * X, Y轴已知, 查找Y轴后面第一个存在权值的节点
         * @param index
         * @param nextIndex
         * @return
         */
        private int getNextNeighbor(int index, int nextIndex) {
            for (int i = nextIndex + 1; i < lstVertex.size(); i++) {
                if (vertexPathArray[index][i] > 0) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 获取第一个邻接节点
         * X轴已知, 获取Y轴上第一个存在权值的节点
         * @param index
         * @return
         */
        private int getFirstNeighbor(int index) {
            // 在该行坐标轴上进行遍历查找
            // 如果对应坐标的权值大于0, 说明这两个点是有关联关系的
            // 直接返回该点对应的下标索引
            for (int i = 0; i < lstVertex.size(); i++) {
                if (vertexPathArray[index][i] > 0) {
                    return i;
                }
            }
            // 如果没有找到, 直接返回-1
            return -1;
        }

    }

}

```

## 11.3，图的广度优先遍历（Broad First Search - BFS）

### 11.3.1，基本思想

* 广度优先遍历，类似于分层搜索的过程；广度优先遍历需要使用一个队列来保持访问过的节点的顺序，以便按照这个顺序来访问这些节点的邻接节点

### 11.3.2，算法步骤

1. 访问初始节点`index`并标记为已访问
2. 节点`index`入队列，*相对于深度遍历，广度遍历需要维护一个局部队列*
3. 当队列不为空时，继续遍历执行，否则算法结束
   * 对于刚开始遍历，起步会入`index`到队列，所以对于第一次必为真
   * 后续操作中，每遍历到一个未访问的节点，都会将该节点入队列，在每一次队列遍历时，移除第一个节点进行广度遍历，等队列最终为空，说明以该节点为初始节点的遍历完成

4. 移除队列的第一个元素，作为初始节点，进行遍历，第一次默认取出`index`节点
5. 横向查找节点`index`的第一个邻接节点`nextIndex`
6. 若节点`index`的邻接节点`nextIndex`不存在，则转到步骤三
7. 如果节点存在，则需要进行下面判断
   * 若节点`nextIndex`没有被访问，则访问节点并标记为已访问
   * 将节点`nextIndex`入队列；
   * 将`nextIndex`标记添加队列完成后，将`nextIndex`节点作为`index`节点，转到步骤五，继续处理
   * <font color=red>一次遍历中，会持续往队列中添加元素，这也是第三步队列判空的意义；如果初始节点`index`存在三个横向的邻接节点，则第一次队列遍历完成后，队列中会移除`index`节点，并添加三个邻接节点，等第二次遍历时，队列中会有三个节点，并继续对这三个节点进行遍历</font>
   * 尽量写明白点，看代码吧，目测看不懂...

### 11.3.3，核心代码

![1593093628336](E:\gitrepository\study\note\image\dataStructure\1593093628336.png)

* 广度优先运行结果：1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8

```java
package com.self.datastructure.chart;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 图基本入门
 *
 * @author PJ_ZHANG
 * @create 2020-04-20 16:16
 **/
public class QuickStartChart {

    public static void main(String[] args) {
        MyChart myChart = createChart(8);
        System.out.println("深度优先遍历: ");
        myChart.dfs();
    }

    /**
     * 创建图
     * @param vertexNum 顶点数量
     * @return
     */
    private static MyChart createChart(int vertexNum) {
        MyChart myChart = new MyChart(vertexNum);
        for (int i = 1; i <= vertexNum; i++) {
            myChart.addVertex(String.valueOf(i));
        }
        myChart.addEdge(myChart.indexOf("1"), myChart.indexOf("2"), 1);
        myChart.addEdge(myChart.indexOf("1"), myChart.indexOf("3"), 1);
        myChart.addEdge(myChart.indexOf("2"), myChart.indexOf("4"), 1);
        myChart.addEdge(myChart.indexOf("2"), myChart.indexOf("5"), 1);
        myChart.addEdge(myChart.indexOf("3"), myChart.indexOf("6"), 1);
        myChart.addEdge(myChart.indexOf("3"), myChart.indexOf("7"), 1);
        myChart.addEdge(myChart.indexOf("4"), myChart.indexOf("8"), 1);
        myChart.addEdge(myChart.indexOf("5"), myChart.indexOf("8"), 1);
        return myChart;
    }

    /**
     * 自定义图类进行处理
     */
    static class MyChart {

        /**
         * 顶点数量
         */
        private int vertexNum;

        /**
         * 顶点列表
         */
        private List<String> lstVertex;

        /**
         * 顶点路径
         */
        private int[][] vertexPathArray;

        /**
         * 边数量
         */
        private int edgeNum;

        /**
         * 是否已经被访问
         */
        private boolean[] isVisited;

        MyChart(int vertexNum) {
            this.vertexNum = vertexNum;
            lstVertex = new ArrayList<>(vertexNum);
            vertexPathArray = new int[vertexNum][vertexNum];
            isVisited = new boolean[vertexNum];
        }

        /**
         * 添加顶点, 此处不涉及扩容
         *
         * @param vertex 添加的顶点
         */
        void addVertex(String vertex) {
            if (vertexNum == lstVertex.size()) {
                throw new ArrayIndexOutOfBoundsException("数组已满");
            }
            lstVertex.add(vertex);
        }

        /**
         * 添加多个顶点
         *
         * @param vertexArr 顶点数组, 可变参数
         */
        void addAllVertex(String ... vertexArr) {
            if (vertexNum < lstVertex.size() + vertexArr.length) {
                throw new ArrayIndexOutOfBoundsException("数组已满");
            }
            lstVertex.addAll(Arrays.asList(vertexArr));
        }

        /**
         * 返回顶点所在的下标
         *
         * @param vertex 目标顶点
         * @return 返回下标
         */
        int indexOf(String vertex) {
            return lstVertex.indexOf(vertex);
        }

        /**
         * 添加边
         *
         * @param xIndex 横坐标
         * @param yIndex 纵坐标
         * @param weight 边的权重
         */
        void addEdge(int xIndex, int yIndex, int weight) {
            if (xIndex >= vertexNum || yIndex >= vertexNum) {
                throw new IndexOutOfBoundsException("索引越界");
            }
            vertexPathArray[xIndex][yIndex] = weight;
            vertexPathArray[yIndex][xIndex] = weight;
            edgeNum++;
        }

        /**
         * 获取边数量
         *
         * @return 返回边数量
         */
        int getEdgeNum() {
            return edgeNum;
        }

        /**
         * 展示图
         */
        void showChart() {
            for (int[] array : vertexPathArray) {
                System.out.println(Arrays.toString(array));
            }
        }

        /**
         * 获取下一个邻接节点
         * X, Y轴已知, 查找Y轴后面第一个存在权值的节点
         * @param index
         * @param nextIndex
         * @return
         */
        private int getNextNeighbor(int index, int nextIndex) {
            for (int i = nextIndex + 1; i < lstVertex.size(); i++) {
                if (vertexPathArray[index][i] > 0) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 获取第一个邻接节点
         * X轴已知, 获取Y轴上第一个存在权值的节点
         * @param index
         * @return
         */
        private int getFirstNeighbor(int index) {
            // 在该行坐标轴上进行遍历查找
            // 如果对应坐标的权值大于0, 说明这两个点是有关联关系的
            // 直接返回该点对应的下标索引
            for (int i = 0; i < lstVertex.size(); i++) {
                if (vertexPathArray[index][i] > 0) {
                    return i;
                }
            }
            // 如果没有找到, 直接返回-1
            return -1;
        }

        /**
         * 广度优先遍历
         */
        public void bfs() {
            for (int i = 0; i < lstVertex.size(); i++) {
                if (!isVisited[i]) {
                    bfs(i);
                }
            }
        }

        private void bfs(int index) {
            LinkedList<Integer> lstSearch = new LinkedList<>();
            // 添加节点到集合
            lstSearch.add(index);
            System.out.print(lstVertex.get(index) + " -> ");
            // 标识节点为已经遍历
            isVisited[index] = true;
            // 队列不为空, 进行顺序处理
            for (;CollectionUtils.isNotEmpty(lstSearch);) {
                // 获取队列第一个顶点
                Integer currIndex = lstSearch.removeFirst();
                // 获取顶点的邻接节点
                int nextIndex = getFirstNeighbor(currIndex);
                // 邻接节点存在
                for (;-1 != nextIndex;) {
                    // 如果邻接节点没有被访问过
                    if (!isVisited[nextIndex]) {
                        lstSearch.add(nextIndex);
                        isVisited[nextIndex] = true;
                        System.out.print(lstVertex.get(nextIndex) + " -> ");
                    }
                    // 获取下一个节点进行处理
                    nextIndex = getNextNeighbor(currIndex, nextIndex);
                }
                // 当前顶点处理完成后, 注意循环开始数据已经被移除
                // 如果集合不为空, 第二次开始时会继续移除下一个顶点, 并对该顶点进行处理
            }
        }

    }

}

```

# 12，程序员常用的十种算法

## 12.1，非递归方式实现二分查找

```java
package com.self.datastructure.algorithm;

/**
 * 非递归方式实现二分查找
 * @author pj_zhang
 * @create 2020-06-26 16:13
 **/
public class BinarySearchWithoutRecursion {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(binarySearchWithoutRecursion(arr, 10));
    }

    public static int binarySearchWithoutRecursion (int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        // 只要left的位置不超过right, 说明有效, 可以继续查找
        for (;left <= right;) {
            int middle = (left + right) / 2;
            // 找到了, 直接返回
            if (arr[middle] == target) {
                return middle;
            } else if (arr[middle] < target) {
                // 中值小于目标值, 向右找
                left = middle + 1;
            } else if (arr[middle] > target) {
                // 中值大于目标值, 向左找
                right = middle - 1;
            }
        }
        // 未找到, 返回-1
        return -1;
    }

}

```

## 12.2，分治算法 -- 汉诺塔问题

### 12.2.1，基本介绍

* 分治算法是一种重要的算法。基本思想就是“分而治之”，将一个复杂的问题分为多个相似的子问题，然后再把子问题分为更小的子问题，直到最后子问题可以以一种最简单的方式直接求解，原问题的解即为子问题解的合并。分治算法的一些经典算法如：二分搜索，棋盘模型，合并排序，快速排序，**汉诺塔**等，以下将用汉诺塔举例

### 12.2.2，分治算法的基本步骤

* 分解：将原问题分解为若干个规模较小，相互独立，与原问题形式相同的子问题
* 解决：如果子问题规模较小，则直接求解；如果规模较大，则递归求解
* 合并：将各个子问题的解合并为主问题的解

### 12.2.3，汉诺塔代码实现

* 如果问题规模足够小，只有一个塔片，则直接从A塔移动到C塔
* 如果A塔上有多个塔盘，则对问题进行分解，将最后一个塔盘上面的所有塔盘移动到B塔，将最后一个塔盘移动到C塔，最后将B塔的所有塔盘移动到C塔
* 如果最后一个塔盘上面的塔盘规模不够小，则继续递归处理，直至问题解决

```java
package com.self.datastructure.algorithm.dac;

/**
 * 分治算法实现之_汉诺塔
 * @author pj_zhang
 * @create 2020-06-26 17:55
 **/
public class HanoiTower {

    public static void main(String[] args) {
        hanoiTower(3, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔实现
     * @param num 汉诺塔数量
     * @param a 处理中的第一个塔柱
     * @param b 处理中的第二个塔柱
     * @param c 处理中的第三个塔柱
     */
    private static void hanoiTower(int num, char a, char b, char c) {
        if (num == 1) {
            // 如果数量为1, 则直接从A移动到C
            System.out.println("将第 " + num + " 个塔片从 " + a + " 移动到 " + c);
        } else {
            // 如果有多个塔片, 则分开处理
            // 首先最下层塔片上面的所有塔片从A移动到B
            hanoiTower(num - 1, a, c, b);
            // 然后将最下面的盘从A移动到C
            System.out.println("将第 " + num + " 个塔片从 " + a + " 移动到 " + c);
            // 最后将移动到B的盘统一移动到C
            hanoiTower(num - 1, b, a, c);
        }
    }

}
```

## 12.3，动态规划算法 -- 背包问题

### 12.3.1，应用场景，背包问题

* 问题描述：有一个容量为4磅的背包，需要装入如列表下的物品，在装入物品可重复和不可重复两种场景下，怎样才能使装入机制最大化

  | 商品名称 | 商品重量 | 商品价格 |
  |:-:|:-:|:-:|
  | 吉他     | 1        | 1500     |
  | 音响     | 4        | 3000     |
  | 电脑     | 3        | 2000     |

### 12.3.2，动态规划算法描述

* 动态规划算法（Dynamic Programming）核心的思想是：将大问题划分为小问题进行解决，从而 ***一步步*** 获得最优解的处理算法（这个一步步是重点，等会就发现真是一步步）
* 动态规范算法和分治算法类似，基本思想都是将待解决问题分解成若干个子问题，先求解子问题，然后从子问题中得到原问题的解
* 与分治算法不同的是，适用于动态规划求解的问题，经分解得到的子问题往往不是相互独立的，下一个子阶段的求解是建立在上一个解决的求解基础上的，依次递进获取最终解
* 动态规划可以通过填表的方式来逐步推进，最终得到最优解

### 12.3.3，动态规划算法的最佳实践——背包问题

* 背包问题，就是给定一个容量的背包，依次放入物品，装入物品可从可重复和不可重复两个维度分析；装入物品总重量不能超过背包容量

* 在这个过程中，可以通过一个二维表格来分析，
  * 横行表示背包容量从0到指定容量的各种情况，这是第一步的分，将大容量的背包先转化为小容量背包，算出子问题的最优解，然后一步步加大容量，算出最终问题的最优解
  * 纵行表示商品信息，且第一横行为空值，作为初始数据的对比值；纵行是第二步的分，先将一个商品放入背包中，算出最优解，逐渐增加商品类型和商品数量，算出最终最优解
  * 最终表格的最右下角的格子，即为数据的最优解

* 不可重复表格填充演示

  | 物品/背包容量 | 0磅  | 1磅  | 2磅  | 3磅  | 4磅  |
  | ------------- | ---- | ---- | ---- | ---- | ---- |
  | 第一横行0     | 0    | 0    | 0    | 0    | 0    |
  | 吉他\|1\|1500 | 0    | 1500 | 1500 | 1500 | 1500 |
  | 音响\|4\|3000 | 0    | 1500 | 1500 | 1500 | 3000 |
  | 电脑\|3\|2000 | 0    | 1500 | 1500 | 2000 | 3500 |
  * 第一横行和第一纵行统一初始化为0，作为后续数据的初始化对比
  * 开始填入物品，从第二横行开始，此时只有一个物品能填入背包，在填入时用物品重量和背包容量进行比较，能填充进去，直接填充并修改背包价值，不能填充进入则背包容量依旧为0，因为不可重复，不考虑放两个一样的，则对于第二横行来说， 吉他重量为1，在背包无论容量是多少，最大价值都是1500
  * 继续填充第三横行，此时可装入背包的物品变为两个，则开始存在判断
    * 如果当前物品重量大于背包容量，则不能装入，背包价值沿用该列上一行的价值，即`table[i-1][j]`，适用于第三横行的前三磅；
    * 如果当前物品重量大于等于背包容量，此处为一种情况，分析中可以分两部分分析，先进行等于分析，装入第四行时进行大于分析
    * 如果当前商品重量等于背包容量，则背包可以装入该商品，但是需要对场景进行分析，此时背包已经装入了其他商品，把其他商品清空，装入该商品后，价值不一定大于原价值，所以需要进行比较，取`Max(customerPrvice + 0, table[i-1][j])`，即用当前商品价值与该列上一行的价值进行比较，取最大值为当前背包价值，此处商品加0是因为相等，大于第四行分析；所以在第三种第四列中，4磅背包容量时，可以装入音响，商品价值为3000 > 原来的1500，装入3000
  * 继续填充第四横行，因为商品重量为3磅，所以0-2磅不符合，直接取上一行的值；3磅时，电脑价值3000 > 原来价值1500，填充为电脑价值，重要的是第四列，背包容量大于当前商品容量时：
    * 背包容量大于当前商品容量，首先必须装入当前商品，依次来获取一个比对价值与上一行价值进行比较来获取一个最优解，但是在装入当前商品时，背包还有容量，此时`4 - 3 = 1`磅，背包还可以装入1磅的商品，则需要从上一行背包容量为1磅的位置，取出背包此时的最高价值与当前商品价值相加，形成在该位置处的当前商品所能带来的最高价值，然后在于该列上一行进行比较，即`Max(customerPrice + table[i-1][packageCapacity - customerWeight], table[i-1][j])`；此时放入本商品的价值是`2000 + 1500 = 3500`，本列上一行的价值是`3000`，则直接覆盖
    * customerPrice：当前商品价格，customerWeight：当前商品重量，packageCapacity：背包容量，`packageCapacity - customerWeight`：背包剩余重量，`table[i-1][packageCapacity - customerWeight]`：背包在当前商品类型剩余重量下的最优解
    * <font color=red>此处必须从上一行找剩余容量的最优解，原因在于商品不能重复，如果从本行找最优解，可能剩余容量的最优解中已经包含了当前商品</font>

* 可重复表格填充演示

  | 物品/背包容量 | 0磅  | 1磅  | 2磅  | 3磅  | 4磅  |
  | ------------- | ---- | ---- | ---- | ---- | ---- |
  | 第一横行0     | 0    | 0    | 0    | 0    | 0    |
  | 吉他\|1\|1500 | 0    | 1500 | 3000 | 4500 | 6000 |
  | 音响\|4\|3000 | 0    | 1500 | 3000 | 4500 | 6000 |
  | 电脑\|3\|2000 | 0    | 1500 | 3000 | 4500 | 6000 |

  * 可重复放入与不可重复放入逻辑基本一致，不过有一个点需要注意，<font color=red>就是在背包容量大于商品重量是，需要在当前商品行找剩余重量的最优解，而不是上一行，因为商品可以重复！</font>
  * 依次逻辑填充第二横行，因为吉他重量是1磅，在1磅时，价值为1500；2磅是，剩余容量为1磅，则取1磅的最优解1500，价值为3000；3磅是，剩余价值为2磅，取2磅最优解3000，价值为4500；4磅为6000
  * 继续填充第三横行，前三磅分别取上一行最优解；4磅处，用3000与6000取最大值6000
  * 继续填充第四横行，前两磅分别取上一行最优解；3磅处，用2000与3000取最大值3000；4磅处，用`2000 + 1500 = 3500`与6000取最大值6000

* 动态规划背包问题算法解析基本完成，可以看到，就是将问题尽量划小，随着背包容量不断增加，商品种类不断增多，来最终获取到预期的最优解

### 12.3.4，动态规划算法——代码实现

```java
package com.self.datastructure.algorithm.dynamic;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 动态规划: 背包问题
 * 问题: 有一个背包, 容量为4磅, 现在有三种不同重量不同价值商品, 怎样放能让背包价值更大
 * 吉他     1磅   1500
 * 音响     4磅   3000
 * 电脑     3磅   2000
 * @author PJ_ZHANG
 * @create 2020-07-02 12:29
 **/
public class KnapsackProblem {

    public static void main(String[] args) {
        // 商品信息, 数组索引相对应
        // 名称数组
        String[] nameArr = {"吉他", "音响", "电脑"};
        // 重量数组
        int[] weightArr = {1, 4, 3};
        // 价格数组
        int[] priceArr = {1000, 3000, 6000};
        // 背包容量
        int packageCapacity = 6;
        // 不可重复
        backpackWithoutRepeat(nameArr, weightArr, priceArr, packageCapacity);
        // 可重复, 与不可重复变更不大, 只对关键部分注释
        backpackWithRepeat(nameArr, weightArr, priceArr, packageCapacity);
    }


    /**
     * 装入背包
     * @param nameArr 名称数组
     * @param weightArr 重量数组
     * @param priceArr 价格数组
     * @param packageCapacity 背包容量
     */
    private static void backpackWithRepeat(String[] nameArr, int[] weightArr, int[] priceArr, int packageCapacity) {
        int[][] packageArr = new int[nameArr.length + 1][packageCapacity + 1];
        // 不可重复的统计中, 使用int[][]二维数组, 通过标志位进行记录
        // 并在后续遍历过程后, 通过倒叙遍历, 依次取出参与背包容量的最优解
        // 但是在可重复的统计中, 在最优解中, 可能存在多种商品参与多次, 此处直接用String进行拼接
        String[][] contentArr = new String[nameArr.length + 1][packageCapacity + 1];
        for (int i = 1; i < packageArr.length; i++) {
            for (int j = 1; j < packageArr[i].length; j++) {
                if (weightArr[i - 1] > j) {
                    packageArr[i][j] = packageArr[i - 1][j];
                } else {
                    int onePart = packageArr[i - 1][j];
                    // 此处取当前行的前置价格
                    // 在当前行中, 可能剩余重量代表的最优解中, 已经包含了该商品, 所以会重复
                    int otherPart = priceArr[i - 1] + packageArr[i][j - weightArr[i - 1]];
                    packageArr[i][j] = Math.max(onePart, otherPart);
                    // 商品已经使用, 通过String数组对名称进行记录
                    // 取上一行的最优解, 直接复制引用的名称串
                    if (packageArr[i][j] == onePart) {
                        contentArr[i][j] = contentArr[i - 1][j];
                    } else {
                        // 通过当前商品加剩余重量拼接形成最优解
                        // 则同样用当前商品名称加上剩余重量处的引用名称组成新的名称引用传
                        contentArr[i][j] = nameArr[i - 1] + "," +
                                (StringUtils.isEmpty(contentArr[i][j - weightArr[i - 1]])
                                        ? ""
                                        : contentArr[i][j - weightArr[i - 1]]);
                    }
                }
            }
        }
        // 最终的背包价值和背包内容就是各自二维数组的最后一个元素
        // 背包价值
        System.out.println("背包价值: " + packageArr[nameArr.length][packageCapacity]);
        // 背包内容,
        System.out.println("背包内容: " + contentArr[nameArr.length][packageCapacity]);
    }


    /**
     * 装入背包
     * @param nameArr 名称数组
     * @param weightArr 重量数组
     * @param priceArr 价格数组
     * @param packageCapacity 背包容量
     */
    private static void backpackWithoutRepeat(String[] nameArr, int[] weightArr, int[] priceArr, int packageCapacity) {
        // 构建背包重量从0到指定重量对应的价格最优解二维数组
        // 该数组是对背包从0到指定重量的所有重点上价格最优解的罗列
        // 第一维表示商品, 并空出第一行, 初始化为0
        // 第二维表示背包重量从0到指定重量,
        // 值表示在当前背包容量和现有商品条件下, 背包包含价值的最优解
        // 如果商品数量是(50), 背包容量是(100),
        // 值(int[i][j])表示在当前背包容量下, 前i个商品在j背包容量时的最优解(不是所有商品和最大容量)
        // int数组初始化为0, 所以不用刻意处理, 空出即可
        int[][] packageArr = new int[nameArr.length + 1][packageCapacity + 1];
        // 构建可能装入背包的二维数组
        // 第一位表示商品, 第二维表示背包重量, 即在背包容量为j时, 当前商品i有没有可能装入背包
        // 此时一个商品可能在各个重量段被装入背包, 注意后续遍历获取逻辑
        // 值为0时说明不会装进背包, 值为1说明可能装入背包, 最终最优解可能不会装入
        int[][] contentArr = new int[nameArr.length + 1][packageCapacity + 1];
        // 装入背包基本计算公式
        // 如果当前商品重量大于当前遍历到的背包容量, 则把当前列(重量列)的上一行值(也可能是取的上一行值)赋给该值
        // 即 int[i][j] = int[i - 1][j]
        // 如果当前商品重点小于等于遍历到的背包容量, 则对两部分内容进行比较
        // 第一部分: 该列上一行的最优解
        // 第二部分: 当前商品价值 +  上一行在(总重量-当前商品重量)处的最优解
        // 开始遍历, 先遍历第一维, 即商品维度, 从1开始遍历, 跳过第一行
        for (int i = 1; i < packageArr.length; i++) {
            // 再遍历第二维, 即背包容量维度, 从1开始遍历, 跳过第一列
            for (int j = 1; j < packageArr[i].length; j++) {
                // 对商品重量和背包容量(j)进行比较
                if (weightArr[i - 1] > j) {
                    // 当前商品 > 背包容量, 取同列上一行数据
                    packageArr[i][j] = packageArr[i - 1][j];
                    // 因为不存在商品装入, 不对contentArr进行处理
                } else {
                    // 当前商品 <= 背包容量, 对两部分内容进行比较
                    // 第一部分, 该列上一行数据
                    int onePart = packageArr[i - 1][j];
                    // 第二部分, 当前商品 + 上一行在(总重量-当前商品重量)处的最优解
                    // priceArr[i - 1]: 当前商品价格
                    // weightArr[i - 1]: 当前商品重量
                    // j - weightArr[i - 1]: 去掉当前商品, 背包剩余容量
                    // 不可重复: packageArr[i - 1][j - weightArr[i - 1]]: 在上一行, 取剩余重量下的价格最优解
                    // 根据是否可以重复取横坐标
                    int otherPart = priceArr[i - 1] + packageArr[i - 1][j - weightArr[i - 1]];
                    // 取最大值为当前位置的最优解
                    packageArr[i][j] = Math.max(onePart, otherPart);
                    // 如果最优解包含当前商品, 则表示当前商品已经被使用, 进行记录
                    if (otherPart == packageArr[i][j]) {
                        contentArr[i][j] = 1;
                    }
                }
            }
        }

        // 不能重复的场景中
        // 如果该位置的标志位为1, 说明该商品参与了最终的背包添加
        // 如果该位置的标志位为0, 即使该位置的价格为最大价格, 也是从其他位置引用的价格
        // 因为不能重复, 所以每行只取一个数据参与最终计算, 并只判断在最大位置该商品是否参与
        // 该最大位置会随着已经遍历出其他元素而对应不断减小, 直到为0

        // 二维数组最后一个元素必然是最大值, 但是需要知道该最大值是自身计算的 还是比较后引用其他的
        int totalPrice = 0;
        // 最大行下标数, 即商品数
        int maxLine = contentArr.length - 1;
        // 最大列下标数, 即重量
        int maxColumn = contentArr[0].length - 1;
        for (;maxLine > 0 && maxColumn > 0;) {
            // 等于1表示在该位置该商品参与了计算
            if (contentArr[maxLine][maxColumn] == 1) {
                // 遍历后, 对重量减少, 下一次从剩余重量中取参与商品
                maxColumn -= weightArr[maxLine - 1];
                totalPrice += priceArr[maxLine - 1];
                System.out.printf("%s 加入了背包 \n", nameArr[maxLine - 1]);
            }
            // 因为不能重复
            // 所以如果该商品参与了背包容量, 则肯定剩余的最大位置处参与,
            // 否则跟该数据无关, 直接跳过
            maxLine--;
        }
        System.out.println("不重复情况下, 背包可容纳的最大价值: " + totalPrice);
    }

}
```



## 12.4，KMP算法 -- 字符串匹配问题

### 12.4.1，暴力匹配算法

#### 12.4.1.1，算法基本介绍

* 暴力匹配算法是对字符串及匹配子串的字符进行一一匹配，完全匹配后则说明字符串匹配
* 假设存在字符串`str`和子串`childStr`，需要进行字符串的暴力匹配，才从头开始匹配
* 此时取字符串`str`的索引位置`i = 0`，子串`childStr`的索引位置`j = 0`，用`str[0]`和`childStr[0]`进行比较，如果匹配，则进行`i++, j++`，并依次进行下一个字符匹配
* 此时如果不匹配，说明`i = 0`处起始匹配是匹配不到的，则进行回溯，重新从`i = 1`处开始重复以上逻辑进行匹配
* 但是此时会存在一个问题，`i`已经经过了多次的递增，不过`i`的递增和`j`的递增是同步的，所以需要回溯到`i`后的一个索引位置，需要进行计算`i = i - j + 1`
* 在过程中可以进行优化，比如字符串`str`的后续长度不足子串`childStr`的长度，则直接可推出
* `java.lang.String.indexOf(..)`就是使用的暴力匹配算法
* <font color=red>暴力匹配算法有一个天然劣势，就是在每一次回溯时是回溯到初始匹配的下一个位置进行下一轮匹配，这样会导致这个回溯时间很长，所以有了KMP算法改进</font>

#### 12.4.1.2，暴力匹配算法代码实现

```java
package com.self.datastructure.algorithm.kmp;

/**
 * 暴力匹配算法
 * 算法解析:
 * * 对一个字符串A, 匹配目标子串a
 * * 定义两个变量, 字符串A的起始标记i, 从0开始, 字符串a的起始比较offset, 从0开始
 * * 用A[i]匹配a[offset], 如果匹配则++
 * * 如果不匹配, i移位到原始i的后一位, 因为过程中, i可能存在++操作
 * * 但是i具体移位了多少是通过offset体现出来的, 所以i的后一位就是(i-j+1)
 * @author pj_zhang
 * @create 2020-07-05 12:33
 **/
public class ViolenceMatch {

    public static void main(String[] args) {
        String source = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String target = "尚硅谷你尚硅你";
        System.out.println(violenceMatch(source, target));
    }

    private static int violenceMatch(String source, String target) {
        // 先分别转换为char数组
        char[] sourceArr = source.toCharArray();
        char[] targetArr = target.toCharArray();

        // 定义source字符串的偏移量i
        // 定义target字符串的偏移量offset
        int i = 0;
        int offset = 0;
        // 计算i的最大范围, 因为target的长度不定, 所以source剩余位置不够即可匹配完成
        int max = sourceArr.length - targetArr.length;
        // 开始匹配, 只要i <= max, 即可满足
        for (;i <= max;) {
            // 先查找到第一个位置, 第一个位置不对, 遍历向后查找
            if (sourceArr[i] != targetArr[offset]) {
                for (;sourceArr[i] != targetArr[offset] && i <= max; i++);
            }
            // if条件符合, 说明已经查找到第一个位置, 进行后续位置查找
            if (i <= max) {
                // 进行后续字符串匹配, 如果全部匹配成功, 则j == targetArr.length - 1
                for (;i < sourceArr.length && offset < targetArr.length && sourceArr[i] == targetArr[offset]; i++, offset++);
                // 如果j不等于该值, 说明中间存在位置没有匹配上
                // 此时对j置0
                // i设置为i的原始值+1
                if (offset != targetArr.length) {
                    i = i - offset + 1;
                    offset = 0;
                } else {
                    // 匹配到, 直接返回结果
                    return i - offset;
                }
            }
        }
        // 没有匹配到直接返回-1
        return -1;
    }

}
```

### 12.4.2，KMP算法

#### 12.4.2.1，KMP算法介绍

* Knuth-Morries-Pratt字符串查找算法，简称**KMP算法**，常用于在一个文本串中查找一个模式串的出现位置，这个算法有Donald Knuth，Vaughan Pratt，James H. Morries三人于1977年联合发标，故取三个人姓氏命名，是最早出现的位置匹配的经典算法
* 暴力匹配算法在经过一段匹配后，如果遇到匹配不成功的字符，则完全回溯，从开始匹配的下一个位置再进行字符匹配，过程中会有很多的无用功且耗时；
* KMP算法就是利用之前判断过的信息，通过一个`next数组`，即部分匹配表，保存模式串中前后最长公共子序列的长度，每次回溯时，通过已经匹配到的位置，从部分匹配表中查找，确定已经匹配过的重合部分，直接移动主串的索引偏移到该位置，减少回溯，节省大量时间

#### 12.4.2.2，部分匹配表

* 在部分匹配表之前，先看看字符串的前缀字符串和后缀字符串：入`bread`
  * 前缀字符串是必须存在第一个字符的所有可能连续字符，且不包含字符串本身：如`b, br, bre, brea`
  * 后缀字符串是必须存在最后一个字符的所有可能连续字符，且不包含字符串本身：如`d, ad, ead, read`
* 部分匹配表是**部分匹配值**的数组，部分匹配值是前缀和后缀的最长共有元素的长度，下以`ABCDABD`举例
  * 对应索引0位置来讲，字符串是`A`，前后缀都为空，则共有元素长度为0，*第一位默认为0*
  * 对于索引1位置，字符串是`AB`，前缀是`[A]`，后缀是`[B]`，共有元素长度0
  * 对于索引2位置，字符串是`ABC`，前缀是`[A, AB]`，后缀是`[C, BC]`，共有元素长度为0
  * 对于索引3位置，字符串是`ABCD`，前缀是`[A, AB, ABC]`，后缀是`[D, CD, BCD]`，共有元素长度为0
  * 对于索引4位置，字符串是`ABCDA`，前缀是`[A, AB, ABC, ABCD]`，后缀是`[A, DA, CDA, BCDA]`，共有元素为`[A]`，最大长度为1
  * 对于索引5位置，字符串是`ABCDAB`，前缀是`[A, AB, ABC, ABCD, ABCDA]`，后缀是`[B, AB, DAB, CDAB, BCDAB]`，共有元素是`[AB]`，最大长度为2
  * 对于索引6位置，字符串是`ABCDABD`，前缀是`[A, AB, ABC, ABCD, ABCDA, ABCDAB]`，后缀是`[D, BD, ABD, DABD, CDABD, BCDABD]`，最大长度为0
  * 所以综上，模式串`ABCDABD`对应的部分匹配值分别为`[0, 0, 0, 0, 1, 2, 0]`，也是该字符串对应的部分匹配表，
* 至于部分匹配表的真正价值何在，下一步继续分析

#### 12.4.2.3，KMP算法匹配演示

1. 在字符串`String str = "BBC ABCDAB ABCDABCDABD"` 中匹配模式串`String childStr = "ABCDABD"`

2. 首先进行第一个字符比对，`str.charAt(0) = B`比对`childStr.charAt(0) = A`，匹配不成功，`str`索引后移，继续比对，直到`str.charAt(4) = A`比对`childStr.charAt(0) = A`成功，开始后续字符匹配

   ![1594175473072](E:\gitrepository\study\note\image\dataStructure\1594175473072.png)

3. 后续字符顺序匹配，在`ABCDAB`段是匹配正常，可以继续匹配到，直到`str.charAt(10) = 空格`比对`childStr.charAt(6) = D`失败

   ![1594175567572](E:\gitrepository\study\note\image\dataStructure\1594175567572.png)

4. 此时，按照暴力匹配算法的原则，会回溯到初始匹配位置的下一位，即`str.charAt(5) = B`，与模式串的第一个字符串，即`childStr.charAt(0) = A`进行比对，如图；此处问题在于之前的`ABCDAB`是已经匹配过的，已知的字符串，并且`ABCDAB`的后缀两位与前缀两位是一致的，也就是部分匹配值为2，是不需要再次进行匹配，可直接用`AB`的下一位匹配模式串的第三的；如果进行后缀两位的识别及初始匹配索引的推进，就是KMP算法接下来要处理的问题

   ![1594175672361](E:\gitrepository\study\note\image\dataStructure\1594175672361.png)

5. 在[部分匹配表](#12.4.2.2，部分匹配表)部分，我们已经算出字符串`ABCDABD`的部分匹配表为`int[] arr = [0, 0, 0, 0, 1, 2, 0]`，其中索引对应的是字符串中各个字符的索引位置，值代表以该字符及之前的字符组成一个完整串时，对应的部分匹配值，如在索引5处，对应的字符串是`ABCDAB`，其前后缀最长匹配串为`AB`，长度为2

   ![1594176101057](E:\gitrepository\study\note\image\dataStructure\1594176101057.png)

6. 继续回到第三步，已知D不匹配，则前面`ABCDAB`六个字符是匹配的，从部分匹配表的对应索引5处寻找该字符的部分匹配值2位，说明前两个字符是与模式串的前两个字符相匹配的，即`AB`，那此时初始匹配位置需要移动到这个`A`的位置，即`str[8]`；又因为AB已经匹配过，则直接从第三位开始匹配，即`str.charAt(10) = 空格`比对`childStr.charAt(2) = C`

   > 移动位数 = 当前已匹配位数 - 部分匹配值 = 6 - 2 = 4位

![1594176605116](E:\gitrepository\study\note\image\dataStructure\1594176605116.png)

7. 因为空格与C不匹配，继续部分匹配表中找C所在索引2对应的部分匹配值`arr[2] = 0`，则`移动位数 = 2 - 0 = 2`，继续后续两位

   ![1594176746985](E:\gitrepository\study\note\image\dataStructure\1594176746985.png)

8. 后移两位后，空格与A不匹配，直接后移一位，注意此处不存在匹配，不考虑部分匹配规则；后移一位后A与A匹配，并继续后续匹配到`str.charAt(17) = C`比对`childStr.charAt(6) = D`失败

![1594176883878](E:\gitrepository\study\note\image\dataStructure\1594176883878.png)

9. 比对失败后，因为已匹配字符是6个，对应的部分匹配值是`arr[5] = 2`，则后移4位，继续从第三位开始进行匹配，直至匹配结束，全部7位匹配完成后，算匹配成功，返回当前匹配阶段的主串初始索引`index = 主串当前索引 - 匹配个数 + 1 = 21 - 7 + 1 = 15`，至此全部匹配完成

   ![1594177014643](E:\gitrepository\study\note\image\dataStructure\1594177014643.png)

### 12.4.3，代码实现

```java
package com.self.datastructure.algorithm.kmp;

/**
 * KMP算法
 * * 生成部分匹配表, 此处注意生成规则
 * * 按照匹配字符依次向后匹配, 如果匹配后则继续匹配,
 * * 如果没有匹配到, 按照已经匹配到的字符长度从部分匹配表中找到对应的部分匹配值, 即已匹配部分的前缀
 * * 匹配的首字符后移位数 = 当前已经匹配位数 - 部分匹配表中以匹配部分对应的值
 * * 后移完成后, 继续从上次匹配的断点与子串需要新对应位置匹配, 匹配到继续, 未匹配到重复步骤
 * * 匹配完成后, 如果要进行所有子串匹配, 则后移子串位数, 继续匹配即可
 * @author PJ_ZHANG
 * @create 2020-07-07 17:35
 **/
public class KMP {

    public static void main(String[] args) {
        //String str = "ABCDABCABCDABCCABCDABCABCDABCD";
        //String childStr = "ABCDABCABCDABCD";
        String str = "BBC ABCDAB ABCDABCDABDE";
        String childStr = "ABCDABD";
        System.out.println(kmp(str, childStr));
    }

    /**
     * 通过KMP算法进行匹配
     * @param str 字符串
     * @param childStr 子串
     * @return 返回索引
     */
    private static int kmp(String str, String childStr) {
        // 获取串的部分匹配值
        int[] arr = partMachingValue(childStr);
        // 进行KMP计算
        // i: 主串的匹配索引位置
        // j: 模式串的匹配索引位置
        for (int i = 0, j = 0; i < str.length(); i++) {
            // 如果存在匹配过的位数, 并且当前字符没有匹配到
            // 则将模式串后移, 通过j减的方式实现
            // 比如j已经匹配到了6位, 第7位没有匹配到
            // 则取前6位的部分匹配值, 即arr[5]
            // 如果前六位有三位是前后缀匹配的, 则继续从第四位开始匹配剩余的部分, 以此类推
            // 如果不存在匹配过的位数, 则直接通过i++进行第一位的匹配
            if (j > 0 && str.charAt(i) != childStr.charAt(j)) {
                j = arr[j - 1];
            }
            // 如果匹配, 则j++ 继续向后比较
            if (str.charAt(i) == childStr.charAt(j)) {
                j++;
            }
            // 如果j的值等于childStr的长度, 则匹配完成
            if (j == childStr.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    /**
     * 部分匹配值数组生成
     * @param modelStr 字符串
     * @return 部分匹配值数组
     */
    private static int[] partMachingValue(String modelStr) {
        int[] arr = new int[modelStr.length()];
        // 第一个空初始化为0, 此处不多余处理
        // 将长字符串先按两个字符进行处理, 并依次添加字符, 知道匹配完成
        for (int i = 1, j = 0; i < modelStr.length(); i++) {
            // 如果对应的两个字符不相等
            // 个人感觉此处基于的思想是:
            // 首先, j > 0, 也就是说已经在前后缀有较长字符串匹配了, 再继续匹配该值
            // 然后, 添加上该值后, 因为该值影响, 前后缀现有的长字符串匹配断开,
            // 再然后, 虽然添加该值影响长匹配断开, 但不排除依旧有较短的前后缀字符串可以匹配, 如: ABCDABCABCDABCD
            // 虽然 ABCDABC 的匹配因为前缀的 A 和后缀的 D匹配不到端口, 但是不影响 ABC 的后一个字符 D 的匹配
            // 所以此处需要往前找, 找到的合适的位置与后缀的新匹配字符进行匹配, 如果匹配到则可关联上部分匹配
            // 此时找的标准是, 先找前缀字符串(ABCDABC)最末索引(j - 1 = 7 - 1 = 6)对应的部分匹配值(arr[j - 1] = 3),
            // 并以该部分匹配值作为索引获取到对应的字符(modelStr.charAt(j) = D)
            //     此处注意, 一定是前缀再前缀, 如果前缀的后一个字符与当前字符能匹配到, 那就不可能走到这一步
            //     继续注意: 前缀与后缀匹配, 前缀再前缀就相当于后缀再后缀, 所以前缀再前缀等于即将与当前字符关联的子串
            // 所以, 判断前缀的后一个字符, 即索引出的字符与当前拼接字符是否相等, 如果相等, 则部分匹配值+1
            // 如果不相等, 继续这部分循环, 直到匹配到或者遍历完前缀确定为0
            while (j > 0 && modelStr.charAt(i) != modelStr.charAt(j)) {
                j = arr[j - 1];
            }
            // 如果两个字符相等, 说明已经匹配到了
            if (modelStr.charAt(i) == modelStr.charAt(j)) {
                // j自增是将j一直向推
                // 如果只有两个字符, 则i表示第一个, j表示第二个
                // 此时如果对应的字符相等, 则匹配到一个
                // 如果有三个字符, 在两个字符的基础上继续推进, i, j都有自增
                // 此时如果对应的字符依旧相等, 则j=2, 表示匹配两个, 以此类推
                j++;
            }
            arr[i] = j;
        }

        return arr;
    }

}
```



## 12.5，贪心（Greed）算法 -- 电台覆盖问题

### 12.5.1，应用场景—集合覆盖问题

* 假设存在下面需要付费的广播电台，以及广播电台可以覆盖的地区。如何选择最少的电台，能实现区域的全覆盖

  ![1594219257470](E:\gitrepository\study\note\image\dataStructure\1594219257470.png)

### 12.5.2，贪心算法介绍

* 贪心算法（贪婪算法）是指在对问题进行求解时，**在每一步的选择中都选择最优解**，从而期望能够导致结果是最优解的算法
* 贪心算法所得到的结果不一定是最优解，但是一定是相对近似最优解的结果

### 12.5.3，贪心算法最佳应用演示—集合覆盖问题

1. 已知存在多少电台，及电台对应的覆盖城市集合；并且各个电台所覆盖城市存在部分重复，需要最少几部电台可实现全覆盖
2. 首先汇总需要覆盖的城市，取各个电台对应城市的并集，作为汇总城市，并全部表示为未覆盖城市
3. 然后遍历各个电台，统计各个电台在未覆盖的城市中可以覆盖几座城市
4. 统计完成后，取覆盖城市最多的电台为最优解，即为先用电台，添加到选用集合；同时，从未覆盖城市集合中删除该电台覆盖的城市
5. 再剩余未覆盖的城市中，重复第3和第4步，直到未覆盖城市数为0，即表示城市已经被全部覆盖
6. 最终返回选用集合，表示最终选择的电台
7. <font color=red>注意：此处虽然在每一步取得了最优解，但是最终结果不一定是最优解，这也是贪心算法的特性</font>

### 12.5.4，代码实现

```java
package com.self.datastructure.algorithm.greed;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 贪心算法_电台覆盖问题
 * * 贪心算法是在每一次选择时力求最优解, 最终达到的结果贴近于最优解的算法
 * * 对应存在多个电台, 每个电台可以覆盖多个不同的城市, 各个电台间覆盖城市有重合, 最少需要多少电台就可以实现全覆盖
 * * 1, 首先汇总还未覆盖的全部城市, 第一次汇总即为全部城市
 * * 2, 然后遍历各个电台, 统计该电台在未覆盖的城市中还可以覆盖几个城市
 * * 3, 找出可覆盖最多城市的电台作为最优解, 进行电台记录, 作为选择之一
 * * 4, 从未覆盖的城市集合中移除该电台可覆盖的城市
 * * 5, 重复2,3,4步, 知道未覆盖城市为空
 * * 6, 统计记录的电台, 即为最终解, 注意该最终解不一定为最优解
 * @author PJ_ZHANG
 * @create 2020-07-08 17:36
 **/
public class Greed {

    public static void main(String[] args) {
        Map<String, Set<String>> redioMap = new HashMap<>(16);
        redioMap.put("K1", new HashSet<>(Arrays.asList("北京", "上海", "天津")));
        redioMap.put("K2", new HashSet<>(Arrays.asList("广州", "北京", "深圳")));
        redioMap.put("K3", new HashSet<>(Arrays.asList("成都", "上海", "杭州")));
        redioMap.put("K4", new HashSet<>(Arrays.asList("上海", "天津")));
        redioMap.put("K5", new HashSet<>(Arrays.asList("杭州", "大连")));
        System.out.println(greed(redioMap));
    }

    /**
     * 贪心算法
     * @param redioMap 电台及对应城市
     * @return 选择的电台集合
     */
    private static Set<String> greed(Map<String, Set<String>> redioMap) {
        // 首先汇总还未覆盖的全部城市, 第一次汇总即为全部城市
        Set<String> lstTotalCities = getTotalCities(redioMap.values());
        // 统计选择的电台
        Set<String> lstRedio = new HashSet<>(10);
        // 统计当前批次匹配到城市最大的电台
        String maxKey = null;
        int maxCount = 0;
        // 进行处理
        for (;lstTotalCities.size() > 0;) {
            maxKey = null;
            maxCount = 0;
            // 遍历各个电台, 获取为匹配的城市数量
            for (Map.Entry<String, Set<String>> entry : redioMap.entrySet()) {
                Set<String> lstCities = entry.getValue();
                // 获取匹配的数量
                int currCount = getRetainCount(lstTotalCities, lstCities);
                // 如果当前匹配数量大于0, 说明匹配到, 有资格进行记录
                // 大于当前最大值, 则进行记录
                if (currCount > 0 && currCount > maxCount) {
                    maxKey = entry.getKey();
                    maxCount = currCount;
                }
            }

            // 一次处理完成后, 统计出需要记录的key进行记录, 并移除匹配的城市
            if (StringUtils.isNotEmpty(maxKey)) {
                lstRedio.add(maxKey);
                lstTotalCities.removeAll(redioMap.get(maxKey));
            }
        }
        return lstRedio;
    }

    /**
     * 获取未匹配城市的交集
     * @param lstTotalCities
     * @param lstCities
     * @return
     */
    private static int getRetainCount(Set<String> lstTotalCities, Set<String> lstCities) {
        int count = 0;
        for (String city : lstCities) {
            if (lstTotalCities.contains(city)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取全部城市
     * @param values
     * @return
     */
    private static Set<String> getTotalCities(Collection<Set<String>> values) {
        Set<String> lstTotalCities = new HashSet<>(10);
        for (Set<String> lstCurrData : values) {
            lstTotalCities.addAll(lstCurrData);
        }
        return lstTotalCities;
    }

}
```



## 12.6，普里姆（Prim）算法（P算法）—修路问题

### 12.6.1，应用场景—修路问题

![1594304853174](E:\gitrepository\study\note\image\dataStructure\1594304853174.png)

* 如图，此时有7个村庄`['A', 'B', 'C', 'D', 'E', 'F', 'G']`，现在需要把这7个村庄连通
* 村庄之间的连接线表示可能修路的图示，权值表示举例
* 此时，如果想要把7个村庄连通，怎么才能让连接的路程最短？
* <font color=red>此时应该尽可能的寻找少的线路，保证每条线路最短，最终达到整体线路最短（该部分有点类似贪心，但是贪心的最终结果不一定是最优解）</font>

### 12.6.2，最小生成树问题

* 修路问题本身就是**最小生成树（Minimum Cost Spanning Tree）**问题，简称**MST**：给定一个无向的带权连接图，如果选取一颗生成树，使树上所有边上全的总和最小，就叫最小生成树

* 树中如果包含N个顶点，则一定包含N-1个边

* 树中必须包含全部的顶点

* N-1个边都必须在图的描述中

* 最小生成树的算法主要是普里姆算法和克鲁斯卡算法

  ![1594305228400](E:\gitrepository\study\note\image\dataStructure\1594305228400.png)

* 如上图所示，表示一张完全图可能生成的生成树，在包含全部顶点的情况下，边的数量一定是N-1；最终最小生成树，就是所有生成树权值相加最小的一个

### 12.6.3，普里姆算法介绍

* 普里姆算法求最小生成树，每次从已访问的顶点集合和未访问的顶点集合中选出其中一个已访问顶点和未访问顶点的连接权值中最小的一个，并对连接点和权值进行记录，最终汇总形成最小生成树
* 普里姆算法具体步骤如下

1. 构建一个顶点集合，和顶点关联关系的二维数组集合，并假设顶点之间已经全部存在关联关系；<font color=red>没有建立关联关系的用一个最大值表示</font>

2. 此时从顶点集合中选出任意一个顶点，并将该顶点标记为已读（通过一个外部数组完成）

3. 将所有已读顶点和未读顶点的关联权值进行比较，<font color=red>注意，此处是用已读顶点和未读顶点比较， 同类不能直接比较</font>，取出权值最小的连接关系

4. 此时该已读顶点和未读顶点的连接权值构成了当前场景下的最优连接权值（此处类似于贪心算法，每一步都期望最优解，普里姆算法最终结果也是最优解）

5. 将该未读顶点标记为已读顶点，然后重复第三步动作，直到所有的路径构建完成

6. 最终需要构建的路径数量 = 顶点数量 - 1条，该部分会在代码第一层循环中体现

7. 图示：

   ![1594305742429](E:\gitrepository\study\note\image\dataStructure\1594305742429.png)

### 12.6.4，代码实现

```java
package com.self.datastructure.algorithm.prim;

import java.util.Arrays;

/**
 * 普里姆算法
 * * 假设顶点之前已经全部存在关联, 没有关联的用一个最大值表示
 * * 从任意一个指定顶点开始, 并将该顶点标记为已读
 * * 将所有已读顶点与所有未读顶点的关联权值进行比较, 取出最小的关联权值
 * * 此时该已读顶点与该未读顶点构成了当前场景下的最优路径(此处类似于贪心算法, 每一步都要最优解, 都要最小路径)
 * * 并将该未读顶点标记为已读顶点
 * * 重复第三步, 直到所有路径都构建完成
 * * 在N个顶点时, 路径有N-1条
 * @author PJ_ZHANG
 * @create 2020-07-09 15:01
 **/
public class Prim {

    /**
     * 表示顶点未连接
     */
    private static final int NOT_CONNECT = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点列表
        char[] vertexArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 顶点对应的与各个顶点的连接情况, 此处没有排除顶点自连接
        // NOT_CONNECT表示没有连接, 且二维顺序与顶点列表顺序一致
        int[][] vertexMap = {
                {NOT_CONNECT, 5, 7, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 2},
                {5, NOT_CONNECT, NOT_CONNECT, 9, NOT_CONNECT, NOT_CONNECT, 3},
                {7, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 8, NOT_CONNECT, NOT_CONNECT},
                {NOT_CONNECT, 9, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 4, NOT_CONNECT},
                {NOT_CONNECT, NOT_CONNECT, 8, NOT_CONNECT, NOT_CONNECT, 5, 4},
                {NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 4, 5, NOT_CONNECT, 6},
                {2, 3, NOT_CONNECT, NOT_CONNECT, 4, 6, NOT_CONNECT}};
        MyGraph myGraph = new MyGraph(vertexArr.length);
        myGraph.setVertexArr(vertexArr);
        myGraph.setVertexMap(vertexMap);
        // 从0索引开始进行连接
        prim(myGraph, 1);
    }

    private static void prim(MyGraph myGraph, int startIndex) {
        // 展示二维图
        myGraph.showVertexMap();
        // 初始化一个顶点访问情况的数组, 未访问为0, 访问为1
        int[] visitArr = new int[myGraph.getVertexCount()];
        // 默认当前节点已经访问
        visitArr[startIndex] = 1;
        // 定义最小长度
        int minValue = NOT_CONNECT;
        // 定义权值最小时, 已访问的顶点坐标和未访问的顶点坐标
        int hasVisited = -1;
        int notVisited = -1;
        // 顶点有N个, 则顶点间的变肯定存在N-1个, 所以一定存在N-1个边
        for (int i = 0; i < myGraph.getVertexCount() - 1; i++) {
            // 下面循环, 从已经被访问的顶点和还没有被访问的顶点中
            // 寻找出权值最小的路径作为下一步需要连接的路径
            for (int x = 0; x < myGraph.getVertexCount(); x++) {
                for (int y = 0; y < myGraph.getVertexCount(); y++) {
                    // x对应值为1表示该顶点已经被访问过
                    // y对应值为0, 表示该顶点还没有被访问过
                    if (visitArr[x] == 1 && visitArr[y] == 0) {
                        // 如果这两个顶点的连接值较小, 则进行记录
                        if (myGraph.getVertexMap()[x][y] < minValue) {
                            minValue = myGraph.getVertexMap()[x][y];
                            hasVisited = x;
                            notVisited = y;
                        }
                    }
                }
            }
            // 一条边处理完成后, 对这条边进行记录
            if (minValue != NOT_CONNECT) {
                // 标记未访问的顶点未已访问
                visitArr[notVisited] = 1;
                // 表示最小长度为初始长度
                minValue = NOT_CONNECT;
                // 打印顶点连接情况
                System.out.println("顶点 " + myGraph.getVertexArr()[hasVisited] + " 与顶点 "
                    + myGraph.getVertexArr()[notVisited] + " 连接, 权值为: "
                    + myGraph.getVertexMap()[hasVisited][notVisited]);
            }
        }
    }


    /**
     * 图对象
     */
    static class MyGraph {

        /**
         * 顶点
         */
        private char[] vertexArr;

        /**
         * 顶点权值图
         */
        private int[][] vertexMap;

        /**
         * 顶点数量
         */
        private int vertexCount;

        public MyGraph(int vertexCount) {
            this.vertexCount = vertexCount;
            this.vertexArr = new char[vertexCount];
            this.vertexMap = new int[vertexCount][vertexCount];
        }

        public void showVertexMap() {
            for (int[] curr : vertexMap) {
                System.out.println(Arrays.toString(curr));
            }
        }

        public char[] getVertexArr() {
            return vertexArr;
        }

        public void setVertexArr(char[] vertexArr) {
            if (vertexArr.length > this.vertexArr.length) {
                throw new IndexOutOfBoundsException("顶点数组越界");
            }
            this.vertexArr = vertexArr;
        }

        public int[][] getVertexMap() {
            return vertexMap;
        }

        public void setVertexMap(int[][] vertexMap) {
            if (vertexMap.length > this.vertexMap.length) {
                throw new IndexOutOfBoundsException("顶点连接线数组越界");
            }
            this.vertexMap = vertexMap;
        }

        public int getVertexCount() {
            return vertexCount;
        }
    }

}
```



## 12.7，克鲁斯卡尔（Kruskal）算法（K算法）—公交站问题

### 12.7.1，应用场景—公交站问题

![1594453684873](E:\gitrepository\study\note\image\dataStructure\1594453684873.png)

* 某城市从新增的7个站点（A，B，C，D，E，F，G），现在需要把7个站点联通
* 各个站点的距离用边权表示，比如A-B为12公里
* 如何修路保证各个站点都能走通，并距离最短
* 从图和问题可以看出，克鲁斯卡尔算法与普里姆算法解决的问题完成一致，只是解决问题的方式不同

### 12.7.2，克鲁斯卡尔算法介绍

* 克鲁斯卡尔算法，是用来求加权连通图的最小生成树的算法

* 基本算法思想：按照边权值大小从小到大的顺序选取`n - 1`条边，并保证这`n - 1`条边不构成回路

* 回路的判断标准是连接边的两个顶点的终点重合

* 算法图解：

  1. 以应用场景中的左图为例

  2. 第一步，取最小的边，即<E，F>，权值为2

     ![1594454423525](E:\gitrepository\study\note\image\dataStructure\1594454423525.png)

  3. 第二步，继续取最小的边，即<C，D>，权值为3

     ![1594454450596](E:\gitrepository\study\note\image\dataStructure\1594454450596.png)

  4. 第三步，继续取<D，E>，此处注意，构成回路的标准是顶点的终点不能一致，不是按照顶点的访问记录判断

     ![1594454512172](E:\gitrepository\study\note\image\dataStructure\1594454512172.png)

  5. 紧接着，取最小的边<C，E>，此处注意，<C，E>构成了回路；因为将<E，F>，<C，D>，<D，E>加入到最小生成树中后，这几个边的顶点<C，D，E，F>就都了各自的终点F，此时再连接<C，E>时，<C，E>的终点都为F，终点重合，则构成了回路，不能构建

  6. 按照第一步到第五步的逻辑，依次类推，则最后的结果如下：

     ![1594454750120](E:\gitrepository\study\note\image\dataStructure\1594454750120.png)

  7. 此时，最小生成树构建完成，最后的结果是<E，F>，<C，D>，<D，E>，<B，F>，<E，G>，<A，B>

### 12.7.3，代码实现

```java
package com.self.datastructure.algorithm.kruskal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 克鲁斯卡尔算法
 * * 克鲁斯卡尔算法与Prim算法解决问题完全一致, 只是解决问题的方式不同
 * * 不同于Prim算法以点为基本单位, 克鲁斯卡尔以边为基本单位
 * * 先构建问题图表, 构建顶点, 并从中读取边的集合(注意不要读取两份)
 * * 然后对边按大小进行升序排列
 * * 遍历边的集合, 依次取出最小的边, 参与最小生成树的生成
 * * 分别从顶点-终点的记录数组中取出该边对应两个顶点的终点
 * * 如果终点重合说明构成了回路, 则不能构建
 * * 终点不重合, 说明还没有连接, 则继续构建
 * * 边集合遍历完成后, 整个最小生成树构建也随之完成
 * * 注意: 此处不能通过顶点已经访问来统计, 比如ABCD四个顶点, AB构成, CD构成, 此时ABCD已经全部访问, 但是不连通
 * @author pj_zhang
 * @create 2020-07-11 12:12
 **/
public class Kruskal {

    private final static int NOT_CONN = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点集合
        char[] lstVertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 连接关系
        int[][] vertexMap = {
                {0, 12, NOT_CONN, NOT_CONN, NOT_CONN, 16, 14},
                {12, 0, 10, NOT_CONN, NOT_CONN, 7, NOT_CONN},
                {NOT_CONN, 10, 0, 3, 5, 6, NOT_CONN},
                {NOT_CONN, NOT_CONN, 3, 0, 4, NOT_CONN, NOT_CONN},
                {NOT_CONN, NOT_CONN, 5, 4, 0, 2, 8},
                {16, 7, 6, NOT_CONN, 2, 0, 9},
                {14, NOT_CONN, NOT_CONN, NOT_CONN, 8, 9, 0}
        };
        // 构建图
        MyGraph myGraph = new MyGraph(lstVertex, vertexMap);
        // 进行克鲁斯卡尔计算
        MyEdge[] result = kruskal(myGraph);
        System.out.println("最终结果如下: ");
        for (MyEdge myEdge : result) {
            System.out.println(myEdge);
        }
    }

    /**
     * 进行克鲁斯卡尔算法计算
     * @param myGraph 图表
     * @return 返回最终的连接关系
     */
    private static MyEdge[] kruskal(MyGraph myGraph) {
        // 结果集, 边的数量为顶点数量-1
        MyEdge[] result = new MyEdge[myGraph.getLstVertex().length - 1];
        int index = 0; // 记录下标位置
        // 顶点的连接终点集合, 初始化为0
        int[] endArr = new int[myGraph.getLstVertex().length];
        // 获取边集合
        MyEdge[] lstEdges = myGraph.getLstEdges();
        // 对边按权值从小到大进行排序
        sortEdges(lstEdges);
        // 对边集合进行遍历, 从最小开始取边进行最小生成树构建
        for (MyEdge myEdge : lstEdges) {
            // 获取边开始和结束的顶点
            char startVertex = myEdge.getStart();
            char endVertex = myEdge.getEnd();
            // 获取顶点对应的下标
            int startIndex = getVertexIndex(myGraph, startVertex);
            int endIndex = getVertexIndex(myGraph, endVertex);
            // 获取顶点连接串的终点, 避免构成回路
            int startEnd = getEndIndex(endArr, startIndex);
            int endEnd = getEndIndex(endArr, endIndex);
            // 如果终点值不重合, 说明不会构成回路, 则进行连接
            if (startEnd != endEnd) {
                // 对终点的终点进行延伸
                endArr[startEnd] = endEnd;
                // 记录边
                result[index++] = myEdge;
            }
        }
        System.out.println("终点数组: " + Arrays.toString(endArr));
        return result;
    }

    /**
     * 获取顶点的终点索引
     * @param endArr 终点记录数组
     * @param index 当前顶点下标
     * @return 终点下标
     */
    private static int getEndIndex(int[] endArr, int index) {
        // 如果当前顶点存在终点, 则继续去找终点的终点
        // 找到最终点, 最终返回该索引
        while (endArr[index] != 0) {
            index = endArr[index];
        }
        // 如果当前顶点的终点为0, 表示顶点的终点就是它自己, 直接返回即可
        return index;
    }

    /**
     * 获取顶点对应的下标
     * @param myGraph 图
     * @param vertex 顶点
     * @return
     */
    private static int getVertexIndex(MyGraph myGraph, char vertex) {
        for (int i = 0; i < myGraph.getLstVertex().length; i++) {
            if (myGraph.getLstVertex()[i] == vertex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 对边按权值进行排序
     * @param lstEdges
     */
    private static void sortEdges(MyEdge[] lstEdges) {
        for (int i = 0; i < lstEdges.length; i++) {
            for (int j = 0; j < lstEdges.length - 1 - i; j++) {
                if (lstEdges[j].getWeight() > lstEdges[j + 1].getWeight()) {
                    MyEdge temp = lstEdges[j];
                    lstEdges[j] = lstEdges[j + 1];
                    lstEdges[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 构建图表
     */
    @Getter
    static class MyGraph {

        /**
         * 顶点数量
         */
        private int vertexCount;

        /**
         * 顶点列表
         */
        private char[] lstVertex;

        /**
         * 顶点图
         */
        private int[][] vertexMap;

        /**
         * 顶点的边集合
         */
        private MyEdge[] lstEdges;

        public MyGraph(char[] lstVertex, int[][] vertexMap) {
            this.vertexCount = lstVertex.length;
            this.lstVertex = lstVertex;
            this.vertexMap = vertexMap;
            // 记录边, 按顺序读取, 保证顺序
            List<MyEdge> lstData = new ArrayList<>(10);
            for (int i = 0; i < vertexCount; i++) {
                // 从下一位开始读, 保证不会生成重复的边
                for (int j = i + 1; j < vertexCount; j++) {
                    // 如果连接, 则进行统计
                    if (vertexMap[i][j] != NOT_CONN) {
                        lstData.add(new MyEdge(lstVertex[i], lstVertex[j], vertexMap[i][j]));
                    }
                }
            }
            lstEdges = new MyEdge[lstData.size()];
            for (int i = 0; i < lstData.size(); i++) {
                lstEdges[i] = lstData.get(i);
            }
        }

    }

    /**
     * 边对象
     */
    @Data
    @AllArgsConstructor
    static class MyEdge {

        /**
         * 起点
         */
        private char start;

        /**
         * 终点
         */
        private char end;

        /**
         * 权重
         */
        private int weight;
    }

}
```



## 12.8，迪杰斯特拉（Dijkstra）算法（D算法）—最短寻径问题

### 12.8.1，应用场景—最短寻径问题

![1594549216294](E:\gitrepository\study\note\image\dataStructure\1594549216294.png)

* 如图，存在7个村庄`['A', 'B', C', 'D', 'E', 'F', 'G']`，现在有6个邮差，从G点出发，需要分别赶往`['A', 'B', C', 'D', 'E', 'F']`六个村庄
* 各个村庄的距离通过边的权值表示，如`A <-> B = 5`

* 问：如何计算G村庄到其他村庄的最短距离
* <font color=red>注意：之前两篇说的普里姆算法和克鲁斯卡尔算法，都是求图内连接各个节点的最短路径；该问题是从一点出发，到各个顶点的最短路径。注意，此处是到各个顶点，不是总和的最短路径</font>

### 12.8.2，迪杰斯塔拉算法介绍

* 首先构建出图的顶点集合和邻接图表，并确定出发顶点
* 然后根据图表信息和出发顶点信息构建顶点访问情况对象，其中包括三个数组属性：
  * 顶点访问情况数组：0表示未访问， 1表示已访问
  * 顶点距离数组：各个顶点到出发顶点的最短距离汇总，也是最终的结果；<font color=red>出发顶点到出发顶点的距离为0，其他顶点到出发顶点的距离初始化为极值</font>
  * 前驱顶点数组：即访问当前顶点的上一个顶点，经过几次前继查找，必定会查找到出发顶点，该数组可能计算完成后，用户复原路线图
* 构建完成后，开始进行计算
  1. 先处理出发顶点，处理之前，先标记当前顶点为已访问，然后从邻接图标中获取该顶点的连接数组，用户顶点距离数组填充，已连接的填充真实距离，未连接的填充为极值（此处注意，出发节点与部分节点未连通，后续处理中会广度遍历其他节点，判断连通）；之后将所有连接节点的前驱节点修改为出发节点
  2. 出发顶点处理完成后，对所有与出发顶点关联的节点已经初步统计完成；但是，此处还存在间接关联的节点没有统计；另外，直接连接的距离不一定比间接连接的距离近，比如A-B=5，B-C=10，A-C=20，这样从A-B-C=15，间接距离是小于直接距离的（此处可不用考虑合理性，只做场景分析，不排除翻山越岭）
  3. 出发顶点处理完成后，按照图广度遍历优先的原则，从距离数组中，依次取出距出发顶点距离最小的未访问顶点，进行访问顶点处理
  4. 访问顶点在遍历顶点集合判断距离时，用访问顶点到出发顶点的距离与访问顶点到当前遍历顶点的距离之和（出发顶点通过间接方式到当前遍历顶点的距离），与当前遍历顶点到出发顶点的距离（直接方式到当前遍历顶点的距离，不存在直接方式则为极值）取最小值，进行顶点距离和顶点前驱节点的覆盖
  5. 循环第3，第4步，直到所有顶点全部为已访问，则计算完成
* 计算全部完成后，顶点访问情况对象里面的顶点距离数组，即为最终的结果呈现！

### 12.8.3，代码实现

```java
package com.self.datastructure.algorithm.dijkstra;

import lombok.Getter;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法_D算法
 * * 先通过连通图和出发顶点构建一个访问过的顶点对象
 * * 该对象中包括: 标记顶点访问情况的数组, 顶点到出发顶点的距离数组, 顶点的前驱顶点数组
 * * 从出发顶点开始, 标记该顶点已经访问, 并计算各个顶点到该顶点的距离, 其中不连通的用一个极值表示
 * * 之后继续依照广度搜索优先的算法原则, 依次向外扩展
 * * 从与出发顶点连接的顶点的中, 依次找到距离最小的未访问顶点进行访问并刷新访问状态, 距离和前驱节点
 * * 直至所有顶点遍历完成后, 距离数组中的数据即为最短距离数据
 * @author pj_zhang
 * @create 2020-07-12 12:40
 **/
public class Dijkstra {

    private static final int NON = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点列表
        char[] lstVertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 顶点图
        int[][] vertexMap = {
                {NON, 5, 7, NON, NON, NON, 2},
                {5, NON, NON, 9, NON, NON, 3},
                {7, NON, NON, NON, 8, NON, NON},
                {NON, 9, NON, NON, NON, 4, NON},
                {NON, NON, 8, NON, NON, 5, 4},
                {NON, NON, NON, 4, 5, NON, 6},
                {2, 3, NON, NON, 4, 6, NON}
        };
        MyGraph myGraph = new MyGraph(lstVertex, vertexMap);
        dijkstra(myGraph);
    }

    /**
     * 开始迪杰斯塔拉算法
     * @param myGraph 连接图表
     */
    private static void dijkstra(MyGraph myGraph) {
        // index在此处作为出发顶点
        int index = myGraph.getVertexCount() - 1;
        // 初始化已经连接顶点类
        VisitedVertex visitedVertex = new VisitedVertex(myGraph.getVertexCount(), index);
        // 先从出发顶点, 修改连接顶点的距离和前置顶点
        updateVisitedVertex(visitedVertex, myGraph, index);
        // 出发顶点访问完成后, 还存在间接关联节点没有关联到
        // 同时直接关联顶点的距离不一定比间接关联的距离小
        // 所以此处参考图的广度遍历, 取还没有被访问过的距出发节点距离最小的顶点作为访问顶点继续处理
        // 因为已经访问了一个顶点, 所以此处少访问一个
        for (int i = 0; i < myGraph.getVertexCount() - 1; i++) {
            // index在此处重新赋值, 作为访问顶点
            index = getNextIndex(visitedVertex);
            // 从访问顶点继续修改顶点距离和前置顶点
            updateVisitedVertex(visitedVertex, myGraph, index);
        }
        System.out.println("最终结果: " + Arrays.toString(visitedVertex.getVertexDis()));
    }

    /**
     * 获取下一个未被访问的顶点
     * 注意此顶点一定要是距出发顶点距离最小的
     * @param visitedVertex
     * @return
     */
    private static int getNextIndex(VisitedVertex visitedVertex) {
        int[] visitedArr = visitedVertex.getVisitedArr();
        // 取最小距离为未连通状态下的最大距离
        int minDis = NON;
        int index = -1;
        for (int i = 0; i < visitedArr.length; i++) {
            if (visitedArr[i] == 0 && visitedVertex.getVertexDis()[i] < minDis) {
                index = i;
                minDis = visitedVertex.getVertexDis()[i];
            }
        }
        return index;
    }

    /**
     * 修改当前顶点下, 从出发顶点到各个顶点的距离
     * * 此处主要考虑存在顶点与出发顶点间接关联, 比如通过该顶点关联
     * * 则间接顶点到出发顶点的距离 = 当前顶点到出发顶点的距离 + 间接顶点到当前顶点的距离
     * @param visitedVertex 访问顶点记录对象
     * @param myGraph 顶点下标关联图
     * @param index 当前顶点下标
     */
    private static void updateVisitedVertex(VisitedVertex visitedVertex, MyGraph myGraph, int index) {
        // 标记当前访问顶点为已访问
        visitedVertex.getVisitedArr()[index] = 1;
        // 获取访问图
        int[][] vertexMap = myGraph.getVertexMap();
        // 当前顶点到各个顶点的距离图
        int[] distanceArr = vertexMap[index];
        for (int i = 0; i < distanceArr.length; i++) {
            // 遍历获取该顶点到每一个关联顶点的距离
            // 不为NON, 说明存在关联
            if (distanceArr[i] != NON) {
                // 如果当前顶点没有被访问
                // 并且当前顶点到访问顶点的距离 + 访问顶点到出发顶点的距离小于现有的当前顶点到访问的距离
                // 则进行替换, 并标记为前驱节点
                if (visitedVertex.getVisitedArr()[i] == 0
                        && (myGraph.getVertexMap()[index][i] + visitedVertex.getVertexDis()[index] < visitedVertex.getVertexDis()[i])) {
                    visitedVertex.getVertexDis()[i] = (myGraph.getVertexMap()[index][i] + visitedVertex.getVertexDis()[index]);
                    visitedVertex.getPreVertexArr()[i] = index;
                }
            }
        }
    }

    /**
     * 已访问顶点记录对象
     */
    @Getter
    static class VisitedVertex {

        /**
         * 已访问的顶点数组
         * 0表示未访问, 1表示已访问
         */
        private int[] visitedArr;

        /**
         * 顶点距离
         * 表示当前顶点到目标顶点的距离
         * 遍历过程中体现的是当前距离
         * 全部处理完成即最终结果
         */
        private int[] vertexDis;

        /**
         * 顶点的前驱顶点
         * 最后最后绘制连接图, 表示从出发顶点访问到当前顶点, 需要经过的路径
         */
        private int[] preVertexArr;

        /**
         * 初始化已连接顶点类
         * @param vertexCount 顶点数量
         * @param startIndex 出发顶点索引
         */
        public VisitedVertex(int vertexCount, int startIndex) {
            // 初始化已访问顶点数组
            this.visitedArr = new int[vertexCount];
            // 初始化距离数组
            this.vertexDis = new int[vertexCount];
            // 初始化距离为未连通距离
            Arrays.fill(vertexDis, NON);
            // 初始化出发节点距离为0
            this.vertexDis[startIndex] = 0;
            // 初始化前驱节点数组
            this.preVertexArr = new int[vertexCount];
        }

    }

    /**
     * 图表类
     */
    @Getter
    static class MyGraph {

        /**
         * 顶点数量
         */
        private int vertexCount;

        /**
         * 顶点列表
         */
        private char[] lstVertex;

        /**
         * 顶点连接图
         */
        private int[][] vertexMap;

        public MyGraph(char[] lstVertex, int[][] vertexMap) {
            this.vertexCount = lstVertex.length;
            this.lstVertex = lstVertex;
            this.vertexMap = vertexMap;
        }

    }

}
```



## 12.9，弗洛伊德（Floyd）算法—最短寻径问题

### 12.9.1，应用场景—最短寻径问题

![1594549216294](E:\gitrepository\study\note\image\dataStructure\1594549216294.png)

* 弗洛伊德算法与迪杰斯特拉算法解决问题完全一致，这是解题思路不同

### 12.9.2，弗洛伊德算法介绍

* 和迪杰斯特拉（Dijkstra）算法一样，弗洛伊德（Floyd）算法也是一种用于寻找加权图中顶点间最短路径的算法，该算法创始人为1978年图灵奖获得者**罗比特 · 弗洛伊德**

* 与迪杰斯塔拉算法不同的是：迪杰斯特拉算法基于一个给定的出发顶点，求出该顶点到其他顶点的最短距离；弗洛伊德算法将每一个顶点作为出发顶点，所以需要求出每一个顶点到其他顶点的最短路径；

* 迪杰斯特拉算法最后给出的结果是一个一维数组，即给定顶点到其他顶点的最短距离数组；弗洛伊德算法最后给出的结果是一个二维数组，即每一个顶点到其他顶点的最短距离数组

* 图解分析：

  1. 弗洛伊德算法基本思想：设顶点`vi`到顶点`vk`的最短路径已知为`Lik`，顶点`vk`到顶点`vj`的最短路径已知为`Lkj`，那么`vi`到`vj`的最短路径距离为`min((Lik + Lkj), Lij)`，`vk`的取值可能为所有顶点

  2. 弗洛伊德算法的代码呈现就是三层for循环，循环对象都是全部顶点

     * 第一层循环，中间顶点循环，确定中间顶点`k`
     * 第二层循环，出发顶点循环，确定出发顶点`i`
     * 第三层循环，结束顶点循环，确定结束顶点`j`
     * 以出发顶点`i` -> 中间顶点`k` -> 结束顶点`j`构成完成路径，然后参考第一步进行距离比较，如果符合替换标准，则对距离表和顶点前驱关系表进行更新
     * 上一步比较完成后，结束顶点递增
     * 结束顶点一轮递增完成后，出发顶点递增
     * 出发顶点递增完成后，中间顶点递增
     * 全部比较完成后， 则弗洛伊德算法完成

  3. 以图为例，对于应用场景中给出的无向图，初始化各顶点之间的距离表和前驱关系表后，此处前驱关系表在初始化时默认初始化为各自本身

     ![1594566358577](E:\gitrepository\study\note\image\dataStructure\1594566358577.png)

  4. 开始以A顶点作为中间顶点，进行第一轮的比较，比较完成后更新图如下：

     ![1594566459937](E:\gitrepository\study\note\image\dataStructure\1594566459937.png)

     * B -> C没有连通，所以通过A作为中间顶点，所以B -> C = A -> C + A -> B = 12，同理可知C -> G = 9，则距离表进行对应改变
     * 在前驱关系表中，B -> C因为有了中间顶点A的介入，则对应的前驱顶点应该改为A

  5. 进行以A之后的顶点分别作为中间顶点进行后续计算，直到全部执行完成

### 12.9.3，代码实现

```java
package com.self.datastructure.algorithm.floyd;

import lombok.Getter;

import java.util.Arrays;

/**
 * 弗洛伊德算法
 * * 弗洛伊德算法其实就是三层for循环, 通过间接连接比较直接连接是否距离最短
 * * 三层for循环都是以全部顶点进行循环
 * * 第一层循环作为中间顶点循环
 * * 第二层循环作为出发顶点循环
 * * 第三层循环作为结束顶点循环
 * * 如果通过中间顶点过度后的三点距离, 小于出发顶点到结束顶点的距离,
 * * 则对距离表进行替换, 并更新前驱关系表的前驱顶点为当前遍历的中间顶点
 * * 第一层循环的全部顶点作为中间顶点遍历完成后, 则算法完成
 * @author pj_zhang
 * @create 2020-07-12 22:05
 **/
public class Floyd {

    private static final int NON = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 顶点列表
        char[] lstVertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 顶点图, 该图中各自访问为0点
        int[][] vertexMap = {
                {0, 5, 7, NON, NON, NON, 2},
                {5, 0, NON, 9, NON, NON, 3},
                {7, NON, 0, NON, 8, NON, NON},
                {NON, 9, NON, 0, NON, 4, NON},
                {NON, NON, 8, NON, 0, 5, 4},
                {NON, NON, NON, 4, 5, 0, 6},
                {2, 3, NON, NON, 4, 6, 0}
        };
        MyGraph myGraph = new MyGraph(lstVertex, vertexMap);
        // 进行弗洛伊德计算
        floyd(myGraph);
        // 处理完成, 进行结果输出
        int[][] result = myGraph.getVertexMap();
        System.out.println("距离结果输出: ");
        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
        char[][] preVertexArr = myGraph.getPreVertexArr();
        System.out.println("前驱顶点结果输出: ");
        System.out.println("    A  B  C  D  E  F  G ");
        for (int i = 0; i < preVertexArr.length; i++) {
            System.out.print(lstVertex[i] + ": ");
            System.out.println(Arrays.toString(preVertexArr[i]));
        }
    }

    /**
     * 弗洛伊德计算
     * @param myGraph
     */
    private static void floyd(MyGraph myGraph) {
        int[][] vertexMap = myGraph.getVertexMap();
        char[][] preVertexArr = myGraph.getPreVertexArr();
        char[] lstVertex = myGraph.getLstVertex();
        // 首先遍历中间顶点
        for (int middleIndex = 0; middleIndex < lstVertex.length; middleIndex++) {
            int[] middleDis = vertexMap[middleIndex];
            // 然后遍历起始顶点
            for (int startIndex = 0; startIndex < lstVertex.length; startIndex++) {
                // 如果顶点不连通, 直接进行下一轮循环
                if (NON == middleDis[startIndex]) {
                    continue;
                }
                // 最后遍历结束顶点
                for (int endIndex = 0; endIndex < lstVertex.length; endIndex++) {
                    // 顶点不连通, 直接进行下一轮循环
                    if (NON == middleDis[endIndex]) {
                        continue;
                    }
                    // 顶点连通的情况下进行后续处理
                    // 获取中间顶点到初始顶点的值
                    int startMidDis = vertexMap[middleIndex][startIndex];
                    // 获取中间顶点到到结束顶点的值
                    int endMidDis = vertexMap[middleIndex][endIndex];
                    // 获取初始顶点到结束顶点的值
                    int startEndDis = vertexMap[startIndex][endIndex];
                    // 如果通过中间顶点连接后, 距离小于直接连接的距离(未连接为极值)
                    // 则进行顶点值替换, 并将前驱顶点数组对应位置的前驱顶点改为中间顶点
                    if ((startMidDis + endMidDis) < startEndDis) {
                        vertexMap[startIndex][endIndex] = startMidDis + endMidDis;
                        vertexMap[endIndex][startIndex] = startMidDis + endMidDis;
                        preVertexArr[startIndex][endIndex] = lstVertex[middleIndex];
                        preVertexArr[endIndex][startIndex] = lstVertex[middleIndex];
                    }
                }
            }
        }
    }


    /**
     * 图表类
     */
    @Getter
    static class MyGraph {

        /**
         * 顶点数量
         */
        private int vertexCount;

        /**
         * 顶点列表
         */
        private char[] lstVertex;

        /**
         * 顶点连接图
         */
        private int[][] vertexMap;

        /**
         * 前驱顶点数组
         */
        private char[][] preVertexArr;

        public MyGraph(char[] lstVertex, int[][] vertexMap) {
            this.vertexCount = lstVertex.length;
            this.lstVertex = lstVertex;
            this.vertexMap = vertexMap;
            // 初始化前驱顶点数组
            // 将每一组顶点的前驱顶点先设置为自身
            preVertexArr = new char[this.vertexCount][this.vertexCount];
            for (int i = 0; i < preVertexArr.length; i++) {
                char[] currData = this.preVertexArr[i];
                for (int j = 0; j < currData.length; j++) {
                    preVertexArr[i][j] = lstVertex[i];
                }
            }
        }

    }

}
```



## 12.10，骑士周游（马踏棋盘）问题

### 12.10.1，马踏棋盘算法介绍

* 马踏棋盘问题也被称为骑士周游问题

* 将马随机放在国际象棋的8*8的棋盘中的某个格子里，马按照走棋规则（日子）进行移动。要求每个方格只进入一次，走遍64个方格

  ![1594654171796](E:\gitrepository\study\note\image\dataStructure\1594654171796.png)

### 12.10.2，马踏棋盘算法思路分析

* 马踏棋盘算法是对图的深度遍历优先（DFS）的使用，通过递归加回溯实现对问题的解决，<font color=red>同时可使用贪心算法对最终算法进行优化</font>
* 从一点出发，对该点标记为已访问，并寻找该点通过日子走法，下一步可能访问的点坐标的集合
* 如果存在对应的点坐标集合，并且存在点未访问，则按顺序对集合中的未访问坐标点进行递归访问，即重复上一步动作
* 如果存在对应的点坐标集合，但是集合中的所有坐标点已经被访问，此时需要判断走了多少步
* 如果马踏棋盘完美完成，则马应该是走了 8 * 8 = 64步，如果此时马已经走完了64步，则算法正常结束，求出最终解
* 如果此时马走了不到64步，则说明马在走到该坐标时，已经走到了死胡同，则需要回溯，该步无效，对该步的坐标点记录归零
* 通过上面几步递归执行后，已经可以完成马踏棋盘的基本算法，此时可以通过贪心算法进行优化
* 因为算法主要是图的深度遍历优先的使用，则对于马在的当前坐标点，找到下一步可能的坐标点集合时，遍历走到下一步，会继续以当前走到的点为新的节点，寻找下一步可能的坐标点集合
* 此时可以尽量在第一步的时候让深度遍历的工作量更小，即马走的下一个点的下一步可能的坐标点集合更小，这样一步步从最少的访问，等到遍历到多的，其下一步的节点集合也已经被访问了部分，在递归场景下会节省大量时间（使用贪心前20S左右，使用后100MS左右）
* 在算法中，只需要去取得的下一步可能走到坐标点集合进行增序排列，排序标准即是各自对应的下一步可能的坐标点的数量
* 至此，马踏棋盘算法完成

### 12.10.3，代码实现

```java
package com.self.datastructure.algorithm.horse;

import java.awt.Point;
import java.util.*;

/**
 * 马踏棋盘问题_不通过贪心算法求解
 * * 此处通过递归加回溯进行马踏棋盘求解
 * * 先初始化一个棋盘, 并对应的确定纵横坐标
 * * 定义一点作为马踏棋盘的出发点, 并取该点可以走的附近最多8个坐标
 * * 遍历每一个可走的点, 如果该点没有被走过, 则继续以该点进行深度遍历
 * * 对每一个已经被访问的点标记为已读
 * * 全部遍历完成后, 如果马踏棋盘失败, 则重置棋盘为0值
 * @author pj_zhang
 * @create 2020-07-13 22:07
 **/
public class Horse {

    /**
     * 横坐标最大值
     */
    private static int MAX_X;

    /**
     * 纵坐标最大值
     */
    private static int MAX_Y;

    private static boolean flag;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("开始进行计算");
        // 构建一个8*8的棋盘
        MAX_X = 8;
        MAX_Y = 8;
        int[][] chessBoardArr = new int[MAX_X][MAX_Y];
        horse(chessBoardArr, 0, 0, 1);
        System.out.println("计算完成...., cost: " + (System.currentTimeMillis() - start));
        for (int[] data : chessBoardArr) {
            System.out.println(Arrays.toString(data));
        }
    }

    /**
     * 马踏棋盘求解
     * @param chessBoardArr 构架棋盘的二维数组
     * @param row 当前访问的横坐标
     * @param column 当前访问的纵坐标
     * @param step 当前走的步数
     */
    private static void horse(int[][] chessBoardArr, int row, int column, int step) {
        // 默认当前点已经被访问, 并且走的步数为step
        chessBoardArr[row][column] = step;
        // 获取邻接的最多8个可选步数
        List<Point> lstData = getNextPoint(new Point(row, column));

        // 使用贪心算法对访问的下一个点进行优化
        // 将下一个可能访问到的点集合进行排序
        // 按下一个点能访问到的下一个点的数量进行增量排序
        // 因为基础逻辑是基于深度遍历优先
        // 如果访问点的下一次可选点是最小的, 则可能让这一轮深度尽快完成
        // 不使用贪心算法优化时, 不排序即可
        sort(lstData);

        for (Point point : lstData) {
            // 判断节点没有被访问
            if (chessBoardArr[point.x][point.y] == 0) {
                // 递归根据深度遍历优先原则, 进行访问
                horse(chessBoardArr, point.x, point.y, step + 1);
            }
        }
        // 节点遍历完成后, 判断是否完成
        // 如果当前走的步数没有覆盖完整个棋盘, 说明失败, 则对棋盘该位置进行置0
        // 此处也就是回溯的逻辑所在, 该点走错了, 回去继续走
        // 如果全部走错了, 也就完全归0了
        if (step < MAX_Y * MAX_X && !flag) {
            chessBoardArr[row][column] = 0;
        } else {
            // 表示全部走完
            flag = true;
        }
    }

    /**
     * 贪心算法优化
     * 对下一步可能访问的节点按照该节点下一步可能访问节点的数量增序排列
     * 在初始深度遍历时, 减少遍历的次数
     * @param lstPoint
     */
    private static void sort(List<Point> lstPoint) {
        Collections.sort(lstPoint, (o1, o2) -> (getNextPoint(o1).size() - getNextPoint(o2).size()));
    }

    /**
     * 获取当前节点的周边可走节点
     * point.x: 表示横坐标
     * point.y: 表示纵坐标
     * @param point 当前节点
     * @return 周边可走节点集合, 最多为8个点
     */
    private static List<Point> getNextPoint(Point point) {
        List<Point> lstPoint = new ArrayList<>(10);
        Point addPoint = new Point();
        // 5号点位, 横坐标-2, 纵坐标-1
        if ((addPoint.x = point.x - 2) >= 0 && (addPoint.y = point.y - 1) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 6号点位, 横坐标-1, 纵坐标-2
        if ((addPoint.x = point.x - 1) >= 0 && (addPoint.y = point.y - 2) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 7号点位, 横坐标+1, 纵坐标-2
        if ((addPoint.x = point.x + 1) < MAX_X && (addPoint.y = point.y - 2) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 0号点位, 横坐标+2, 纵坐标-1
        if ((addPoint.x = point.x + 2) < MAX_X && (addPoint.y = point.y - 1) >= 0) {
            lstPoint.add(new Point(addPoint));
        }
        // 1号点位, 横坐标+2, 纵坐标+1
        if ((addPoint.x = point.x + 2) < MAX_X && (addPoint.y = point.y + 1) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 2号点位, 横坐标+1, 纵坐标+2
        if ((addPoint.x = point.x + 1) < MAX_X && (addPoint.y = point.y + 2) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 3号点位, 横坐标-1, 纵坐标+2
        if ((addPoint.x = point.x - 1) >= 0 && (addPoint.y = point.y + 2) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 4号点位, 横坐标-2, 纵坐标+1
        if ((addPoint.x = point.x - 2) >= 0 && (addPoint.y = point.y + 1) < MAX_Y) {
            lstPoint.add(new Point(addPoint));
        }
        // 最终返回最多8个点位
        return lstPoint;
    }

}
```

