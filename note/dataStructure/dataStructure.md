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
              //
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
* **添加**
  * 先找到需要插入到双向链表的目标节点
  * 讲节点和对应前置节点与后置节点的`pre`和`next`属性进行修改
* 删除
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

* 设编号为1，2 ... n的n个人围成一个圈，并约定编号为K（1<=K<=n）的人从1开始报数，数到M的那个人出列，他的下一位又从1开始数，依次类推，知道所有人出列
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
                    // 移除, 此处不需要调移除, 可以直接处理调
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

* 栈是一种先入后出（FIFO）的有序列表

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

中缀表达式简易计算器：不带括号，可以考虑对自责运算排序（/ * - +）

