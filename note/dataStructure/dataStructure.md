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
  * 如果大于当前优先级, 则先从符号栈弹出一个符号, 并从数字栈弹出两个数字进行计算, 计算完成后, 数字入栈并继续该操作直到符合第5步
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
* 所有运算符两边的数字，与后缀表达式的排列顺序一致

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
     * @param targetFirstIndex 目标诶只的一维坐标
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
        } else { // 目标节点不为2, 则继续迷宫探路
            // 为0表示未走过
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
  * 用常数1代替运行时间中的所有加法常熟，为了区别出`O(1)`
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

* 插入排序输入内部排序法，是对欲排序的元素以插入的方式找到合适的位置，以达到排序的目的
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
//        int[] array = {4, 6, 7, 8, 1, 3, 5, 2, 9, 0};
        // 10万个数测试
        int[] array = new int[100000];
        for (int i = 0; i < 100000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        sortAndMove(array);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
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
                if (temp < array[j - gap]) {
                    for (;j - gap >= 0 && temp < array[j - gap]; j -= gap) {
                        array[j] = array[j - gap];
                    }
                    array[j] = temp;
                }
            }
        }
    }


}
```

## 7.8，快速排序