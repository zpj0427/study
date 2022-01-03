* `Spark` 版本：`Spark-3.0.0` + `Hadoop-3.2.1`

# 1，`Spark` 框架

## 1.1，`Spark` 运行环境搭建

## 1.2，`Spark` 核心组件

### 1.2.1，`Driver` & `Executor`

> `Driver`

* `Spark` 驱动器节点，用于执行 `Spark` 任务中的 `main` 方法，负责实际代码的执行工作。`Driver` 在 `Spark` 作业执行时主要负责：
  * 将用户程序转化为作业 `job`
  * 在 `Executor` 之间调度任务 `task`
  * 跟踪 `Executor` 的执行情况
  * 通过 `UI` 展示查询运行情况
* 实际上，我们无法准确地描述 `Driver` 的定义，因为在整个的编程过程中没有看到任何有关`Driver` 的字眼。所以简单理解，所谓的 `Driver` 就是驱使整个应用运行起来的程序，也称之为`Driver` 类。

> `Executor`

* `Spark Executor` 是集群中工作节点 `Worker` 中的一个 JVM 进程，负责在 `Spark` 作业中运行具体任务`Task`，任务彼此之间相互独立。`Spark` 应用启动时，`Executor` 节点被同时启动，并且始终伴随着整个 Spark 应用的生命周期而存在。如果有 Executor 节点发生了故障或崩溃，Spark 应用也可以继续执行，会将出错节点上的任务调度到其他 Executor 节点上继续运行;
* `Executor` 有两个核心功能:
  * 负责运行组成 `Spark` 应用的任务，并将结果返回给驱动器进程
  * 它们通过自身的块管理器 `Block Manager` 为用户程序中要求缓存的 `RDD` 提供内存式存储。`RDD` 是直接缓存在 `Executor` 进程内的，因此任务可以在运行时充分利用缓存数据加速运算。

### 1.2.2，`Master` & `Worker`

* `Spark` 集群的独立部署环境中，不需要依赖其他的资源调度框架，自身就实现了资源调度的功能，所以环境中还有其他两个核心组件：`Master` 和 `Worker`，这里的 `Master` 是一个进程，主要负责资源的调度和分配，并进行集群的监控等职责，<font color=red>类似于 `Yarn` 环境中的 `RM`</font>；而 `Worker` 呢，也是进程，一个 `Worker` 运行在集群中的一台服务器上，由 `Master` 分配资源对数据进行并行的处理和计算，<font color=red>类似于 `Yarn` 环境中 `NM`</font>。

### 1.2.3，`ApplicationMaster`

* `Driver` 和 `Executor` 表示计算相关的组件；
* `Master` 和 `Worker` 表示资源相关的组件；
* 在计算和资源之间，通过 `ApplicationMaster` 进行协调，实现解耦

## 1.3，`Spark` 核心概念

### 1.3.1，`Executor` 和 `Core`

* `Spark Executor` 是集群中运行在工作节点 `Worker` 中的一个 JVM 进程，是整个集群中的专门用于计算的节点。在提交应用中，可以提供参数指定计算节点的个数，以及对应的资源。这里的资源一般指的是工作节点 `Executor` 的内存大小和使用的虚拟 CPU 核（Core）数量。

  ![1640512811999](E:\gitrepository\study\note\images\Spark\1640512811999.png)

### 1.3.2，并行度 （`Parrallelism`）

* 在分布式计算框架中一般都是多个任务同时执行，由于任务分布在不同的计算节点进行计算，所以能够真正地实现多任务并行执行，<font color=red>记住，这里是并行，而不是并发。</font>这里我们将整个集群并行执行任务的数量称之为并行度。那么一个作业到底并行度是多少呢？这个取决于框架的默认配置。应用程序也可以在运行过程中动态修改。

### 1.3.3，有向无环图 （`DAG`）

![1640512868258](E:\gitrepository\study\note\images\Spark\1640512868258.png)

* 以 `Spark` 为代表的第三代计算引擎的特定主要是 `Job` 内部的 `DAG` 支持（不跨越 `Job`），以及实时计算
* 这里所谓的有向无环图，并不是真正意义的图形，而是由 `Spark` 程序直接映射成的数据流的高级抽象模型

* `DAG(Directed Acyclic Graph)` 有向无环图是由点和线组成的拓扑图形，该图形具有方向，不会闭环。

### 1.3.4，提交流程

* 所谓的提交流程，其实就是我们开发人员根据需求写的应用程序通过 `Spark` 客户端提交给 `Spark` 运行环境执行计算的流程。在不同的部署环境中，这个提交过程基本相同，但是又有细微的区别，我们这里不进行详细的比较，但是因为国内工作中，将 `Spark` 引用部署到 `Yarn` 环境中会更多一些，所以本课程中的提交流程是基于 `Yarn` 环境的。

  ![1640513135760](E:\gitrepository\study\note\images\Spark\1640513135760.png)

# 2，`Spark Core`

* `Spark` 计算框架为了能够进行高并发和高吞吐的数据处理，封装了三大数据结构，用于处理不同的应用场景。三大数据结构分别是：
  * **RDD** : 弹性分布式数据集
  * **累加器**：分布式共享只写变量 
  * **广播变量**：分布式共享只读变量

## 2.1，RDD

![1640523825927](E:\gitrepository\study\note\images\Spark\1640523825927.png)

### 2.1.1，基本概念

> RDD（Resilient Distributed Dataset）叫做弹性分布式数据集，是 Spark 中最基本的数据处理模型。代码中是一个抽象类，它代表一个弹性的、不可变、可分区、里面的元素可并行计算的集合。

1. 弹性
   * 存储的弹性：内存与磁盘可自动切换
   * 容错的弹性：数据丢失可以自行恢复
   * 计算的弹性：计算出错重试机制
   * 分片的弹性：可根据需要重新分片
2. 分布式：数据存储在大数据集群不同节点上
3. 数据集：`RDD` 封装了计算逻辑，并不存储数据
4. 数据抽象：`RDD` 是一个抽象类，需要子类具体实现
5. 不可变：`RDD` 封装了计算逻辑，是不可改变的，如果需要改变，只能产生新的 `RDD`，在新的 `RDD` 里面封装计算逻辑
6. 可分区，并行计算

### 2.1.2，核心属性

> RDD的核心属性有五个，并在源码注释中有所体现

![1640524574075](E:\gitrepository\study\note\images\Spark\1640524574075.png)

1. 分区列表

   > RDD 数据结构中存在分区列表，用于执行任务时并行计算，是实现分布式计算的重要属性。

   ![1640524644621](E:\gitrepository\study\note\images\Spark\1640524644621.png)

2. 分区计算函数

   > Spark 在计算时，是使用分区函数对每一个分区进行计算

   ![1640524699697](E:\gitrepository\study\note\images\Spark\1640524699697.png)

3. `RDD` 之间的依赖关系

   > RDD 是计算模型的封装，当需求中需要将多个计算模型进行组合时，就需要将多个 RDD 建立依赖关系

   ![1640524724852](E:\gitrepository\study\note\images\Spark\1640524724852.png)

4. 分区器

   > 当数据为 KV 类型数据时，可以通过设定分区器自定义数据的分区 

   ![1640524880972](E:\gitrepository\study\note\images\Spark\1640524880972.png)

5. 首选位置

   > 计算数据时，可以根据计算节点的状态选择不同的节点位置进行计算

   ![1640524911784](E:\gitrepository\study\note\images\Spark\1640524911784.png)

### 2.1.3，执行原理

* 从计算的角度来讲，数据处理过程中需要计算资源（内存 & CPU）和计算模型（逻辑）。执行时，需要将计算资源和计算模型进行协调和整合

* Spark 框架在执行时，先申请资源，然后将应用程序的数据处理逻辑分解成一个一个的计算任务。然后将任务发到已经分配资源的计算节点上, 按照指定的计算模型进行数据计算。最后得到计算结果。

* RDD 是 Spark 框架中用于数据处理的核心模型，接下来我们看看，在 Yarn 环境中，RDD的工作原理如下：

  * 启动 `Yarn` 集群环境，此处 `ResourceManager` 进行任务协调，并发送到 `NodeManager` 具体执行

    ![1640525728612](E:\gitrepository\study\note\images\Spark\1640525728612.png)

  * `Spark` 通过申请资源创建调度节点和计算节点；此处 `Spark` 流程统一在 `NodeManager` 节点上处理，在其中一个 `NodeManager` 上启动 `Driver` 任务，并在其他 `NodeManager` 上启动具体的 `Executor` 执行任务 

    ![1640525735620](E:\gitrepository\study\note\images\Spark\1640525735620.png)

  * `Spark` 框架根据需求将计算逻辑根据分区划分成不同的任务；在 `Driver` 中将任务拆分成一个个的 `Task`，并放置在 `TaskPool` 中

    ![1640525743637](E:\gitrepository\study\note\images\Spark\1640525743637.png)

  * 调度节点将任务池中的任务根据计算节点状态发送到对应的计算节点进行计算

    ![1640525759371](E:\gitrepository\study\note\images\Spark\1640525759371.png)

* 从以上流程可以看出 RDD 在整个流程中主要用于将逻辑进行封装，并生成 Task 发送给Executor 节点执行计算

### 2.1.4，基础编程

#### 2.1.4.1，`RDD` 创建

> `RDD` 创建方式有四种，包括从集合（内部）中创建、从文件（外部）中创建、从其他 `RDD` 创建、直接创建RDD（new），其中后两种在实际编码中应用不多，基本在框架中应用

1. 从集合中创建 `RDD`

   ```scala
   object CreateRDDFromCollection {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
       val sc: SparkContext = new SparkContext(sparkConf)
   
       // parallelize
       val rdd1: RDD[Int] = sc.parallelize(List(4, 5, 6))
       rdd1.collect().foreach(println)
   
       // makeRdd方式
       // makeRdd方法底层调用的是parallelize方法
       val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3))
       rdd.collect().foreach(println)
     }
   }
   ```

2. 从文件中创建 `RDD`

   ```scala
   object CreateRDDFromFile {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
       val sc: SparkContext = new SparkContext(sparkConf)
   
       // 如果指定到具体文件, 则会读取指定文件
       val rdd: RDD[String] = sc.textFile("input/rddfile")
       rdd.collect().foreach(println)
       println("============================")
       // 如果只是指定到了文件夹, 则会读取文件夹下所有文件
       val rdd1: RDD[String] = sc.textFile("input")
       rdd1.collect().foreach(println)
       println("============================")
       // 文件读取可以使用通配符
       val rdd2: RDD[String] = sc.textFile("input/w*.txt")
       rdd2.collect().foreach(println)
       println("============================")
       // 可以访问远程路径
   //    sc.textFile("hdfs://xxxxx")
   //    rdd2.collect().foreach(println)
       // 上面无论从文件夹还是通配符, 都是只拿到了文件内容, 并没有拿到具体文件, 如果需要知道具体文件,
       // 输出内容为: (文件绝对路径, 文件中的每一行数据)
       // 注意: 一个文件, 文件绝对路径只输出一次, 在第一行数据前输出, 后续依旧为每行数据
       val rdd4: RDD[(String, String)] = sc.wholeTextFiles("input")
       rdd4.collect().foreach(println)
     }
   }
   ```

#### 2.1.4.2，集合_分区设定

> `RDD` 的分区，可以在创建的时候进行指定；如果不指定，则默认会取当前机器的最大核数

```scala
object RDDParallelizeByCollection {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    // 创建RDD时, 可以指定分区数量, 通过第二个参数进行指定
    // 此处2  表示有2个分区
    // 如果不写第二个参数, 则会取默认参数表示分区数量
    // 从源码中可以看到取默认参数的代码
    //   scheduler.conf.getInt("spark.default.parallelism", totalCores)
    // 表示会从配置中取 spark.default.parallelism 参数, 如果没有该参数, 会取 totalCores
    // totalCores 表示当前机器的总核数
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    // 执行结果以文件的形式进行输出, 输出到output文件夹中
    // 可以在最终输出结果中看到, 有几个分区就会有几个输出文件
    rdd.saveAsTextFile("output/" + System.currentTimeMillis())
  }
}
```

#### 2.1.4.3，集合_分区数据分配

> 分区数据分配，根据一定的算法进行分配，集合分配算法如下

![1640530256658](E:\gitrepository\study\note\images\Spark\1640530256658.png)

```scala
// 以一个例子为例, 集合长度为7, 分数数量为3
(List(1, 2, 3, 4, 5, 6, 7), 3)
// 此时三个分区, 分别为分区0, 分区1, 分区2, 开始进行计算
// 注意集合分区划分是包含左侧不包含右侧
// 计算分区0
分区0.start = ((0 * 7) / 3) = 0
分区0.end = (((0 + 1) * 7) / 3) = 2
分区0: [0, 2) = 1, 2
// 计算分区1
分区1.start = ((1 * 7) / 3) = 2
分区1.end = (((1 + 1) * 7) / 3) = 4
分区1: [2, 4) = 3, 4
// 计算分区2
分区2.start = ((2 * 7) / 3) = 4
分区2.end = (((2 + 1) * 7) / 3) = 7
分区2: [4, 7) = 5, 6, 7
```

```scala
object RDDParallelizeDataByCollection {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 3)
    rdd.saveAsTextFile("output/" + System.currentTimeMillis())
  }
}
```

#### 2.1.4.4，文件_分区设定

> 文件的分区设定，也在创建RDD的时候进行设定；不同于集合的分区设定，文件设定分区数表示最少分区，最终实际分区数可能会大于设定分区数，最大值 = 设定数 + 1

* 源码位置：`org.apache.hadoop.mapred.FileInputFormat#getSplits`

![1641096899671](E:\gitrepository\study\note\images\Spark\1641096899671.png)

![1641096916981](E:\gitrepository\study\note\images\Spark\1641096916981.png)

```java
// 一个小计算
// 文件大小为7个字节，目标最小分为2个分区
// 每个分区字节数 = 7 / 2 = 3 ... 1
// 剩余的1个字节占每个分区字节数3个字节的比例为 = 1 / 3 = 33%
// 因为剩余的字节数大于10%，所以对剩余的字节需要单开分区
// 实际分区数 = 2 + 1 = 3
```

```scala
object RDDParallelizeByFile {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    // 文件方式, 第二个参数传递最小分区数
    // Spark读取文件走的是Hadoop读取文件的逻辑
    // Spark的分区设定, 遵循Hadoop的分区设定逻辑
    // 在设置分区时, 首先读取目标文件的总大小: totalSize
    // 根据文件的总大小, 和设定的最小分区数相除, 获取的结果表示每一个分区目标处理文件大小: goalSize
    // 在Hadoop处理逻辑中, 如果剩余文件内容大小小于10%, 则与最后一个分区进行合并处理, 如果大于, 则会新开一个分区
    // 所以, 如果剩余文件字节数大于goalSize的10%, 则最终分区数 = 设定分区数 + 1
    val rdd: RDD[String] = sc.textFile("input/num.txt", 2)
    rdd.saveAsTextFile("output/" + System.currentTimeMillis())
  }

}
```

#### 2.1.4.5，文件_分区数据分配

```java
// 文件内容如下, 注意每一次换行包含一个换行符, 一个换行符包括两个字节
1
2
3
```

* 以行为单位进行读取：`Spark` 读取文件，采用的是 `Hadoop` 读取文件方式进行读取，一行一行进行文件读取，和字节数没有关系

* 以偏移量为数据读取参考：偏移量数据不会被重复读取

```java
// 在上述文件中, 每一行数据后加2个字符的换行符, 所以文件共有7个字节,其中
1\r\n    => 012 三个字节
2\r\n    => 345 三个字节
3		 => 6   一个字节
```

* 数据分区的偏移量计算原则

```java
// 在分区设定中, 实际分为三个分区进行执行, 每个分区目标读取三个字节的数据
// 此处注意, 从0开始, 第一个分区结尾的0 + 分区字节
// 后面分区从前一个分区的结尾为开始, 结尾为 开始 + 分区字节
// 各个分区统计如下
0 => [0, 3]
1 => [3, 6]
2 => [6, 7] // 最多7个字节

// 分区统计完成后, 进行计算结果映射
0 => [0, 3] => 取第一行所有和第二行部分, 因为第一原则是以行进行读取, 所以会读取第二第三行
1 => [3, 6] => 345字节已经读取完成, 只读取6, 所以读取第三行
2 => [6, 7] => 因为全部读取完成, 第三分区无内容
```

* <font color=red>多文件处理：先按总大小进行分区，再以文件为单位进行处理</font>

  ```java
  // 比如存在两个文件, 一个文件1200字节, 一个文件600字节, 分两个分区进行处理
  // 则参与计算的总大小为1800字节, 分区数量为 1800 / 2 = 900字节
  // 所以理论上分为两个分区：
  	* 第一个分区在第一个文件中取 0 ~ 900 字节的内容,
  	* 第二个分区取第一个文件的 900~1200 字节内容和第二个文件的600字节内容
  // 再因为数据在处理时候, 多文件情况下需要进行文件隔离, 所以最终的情况会分为三个分区
      * 第一个分区不变, 为第一个文件的 0~900 字节内容
      * 第二个分区, 理论上需要900字节内容, 因为第二个文件只剩下300字节, 所以只取这300字节为独立分区
      * 第三分区, 第二个文件的600字节内容
  // 最终分为三个分区进行处理
  ```

### 2.1.5，转换算子

> 功能的补充和封装，将旧的RDD包装成新的RDD，进行数据转换
>
> 转换算子只进行RDD的包装，不会进行数据的实际执行，在通过行动算子触发后，会统一进行数据处理

#### 2.1.5.1，`map(..)`：遍历并返回

> 进行数据遍历转换，不带分区号，最终返回 `List[Anything]`

1. 数据遍历演示

   ```scala
   package com.self.study.spark.sparkcore.transform
   
   import org.apache.spark.rdd.RDD
   import org.apache.spark.{SparkConf, SparkContext}
   
   /**
    * 转换算子_Map算子
    */
   object Map {
   
     def main(args: Array[String]): Unit = {
       // 初始化 sc
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
   
       // 构建RDD
       val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
   
       // 对每一个数字x 2
       val rdd1: RDD[Int] = rdd.map(x => {
         x * 2
       })
       rdd1.collect().foreach(println)
   
       // 简写
       val rdd2: RDD[Int] = rdd.map(_ * 2)
       rdd2.collect().foreach(println)
     }
   
   }
   ```

2. 数据分区执行演示

   ```scala
   package com.self.study.spark.sparkcore.transform
   
   import org.apache.spark.rdd.RDD
   import org.apache.spark.{SparkConf, SparkContext}
   
   /**
    * 转换算子_Map算子_分区并行演示
    */
   object MapByPartition {
   
     def main(args: Array[String]): Unit = {
       // 初始化 sc
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
   
       // 构建RDD, 分为两个分区进行执行
       val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
   
       // 对RDD进行第一层数据转换
       val rdd1: RDD[Int] = rdd.map(x => {
         println(">>>>>>>>" + x)
         x
       })
   
       // 进行第二层数据转换
       val rdd2: RDD[Int] = rdd1.map(x => {
         println("########" + x)
         x
       })
       // 执行最终输出结果可以自行演示
       // 结论:
       // 1, RDD算子在分区内是按数据顺序, 一次去执行全逻辑, 只有一个数据执行完成后, 才会去执行下一个数据
       //    每一个数据执行, 是指执行完所有包装的RDD
       //    分区内执行是有序的
       // 2, 在多个分区间, 是进行线程抢占执行的
       //    分区间是乱序的
       rdd2.collect()
     }
   
   }
   ```

   ![1641115390580](E:\gitrepository\study\note\images\Spark\1641115390580.png)

#### 2.1.5.2，`mapPartitions(..)`：分区缓冲区遍历

> `map()` 算子是对数据进行一个一个处理，不存在数据缓冲的概念；
>
> 在 `mapPartitions()` 算子中，会先将整个分区的数据添加到内存中进行引用，然后从内存中取数据进行计算，速度较快，占用资源较高

```scala
package com.self.study.spark.sparkcore.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 转换算子: mapPartitions
 */
object MapPartitions {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 构建RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // mapPartitions 以分区为单位进行数据处理, iter表示该分区下的所有数据, 是一个迭代器
    val rdd1: RDD[Int] = rdd.mapPartitions(iter => {
      // 对该分区数据再进行转换处理, 并返回结果
      iter.map(_ * 2)
    })

    rdd1.collect().foreach(println)
  }

}
```

1. **小练习：获取每个分区数据的最大值**

```scala
package com.self.study.spark.sparkcore.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: mapPartitions
 * 小练习: 获取每个分区的最大值
 */
object MapPartitions_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 构建RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val rdd1: RDD[Int] = rdd.mapPartitions(iter => {
      // 取最大值
      val max: Int = iter.max
      // 转迭代器
      List(max).toIterator
    })

    rdd1.collect().foreach(println)
  }

}
```

2. `map()` 和 `mapPartitions()` 算子的区别

   * 数据处理角度
     * `map()` 算子是分区内一个数据一个数据的执行，类似于串行执行；
     * `mapPartitions()` 算子是以分区为单位进行的批量处理

   * 功能角度
     * `map()` 算子主要目的是进行数据转换和改变，不会减少和增多算子
     * `mapPartitions()` 算子是以迭代器进行交互，在迭代器中未要求元素内容，可能增加和减少数据
   * 性能角度
     * `map()` 算子是串行操作，性能较低；但是一个数据执行完成后会释放掉，占用内存较小
     * `mapPartitions()` 算子是以分区为单位的批处理，性能较高；但是会对分区数据进行长时间引用，占用内存较高
   * <font color=red>完成比完美更重要</font>

#### 2.1.5.3，`mapPartitionsWithIndex(..)`： 带索引号的分区缓冲区

> 与 `mapPartitions()` 算子功能基本一致。在其基础上，显示的增加的分区号呈现

```scala
package com.self.study.spark.sparkcore.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: mapPartitionsWithIndex
 */
object MapPartitionsWwithIndex {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val rdd1: RDD[Int] = rdd.mapPartitionsWithIndex((index, iter) => {
      println("current partition: " + index)
      println(index + " data: " + iter.toList())
      iter
    })

    rdd1.collect()
  }

}
```

1. 小练习：**获取第二个分区的数据**

   ```scala
   object MapPartitionsWwithIndex_Test {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
       val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
   
       val rdd1: RDD[Int] = rdd.mapPartitionsWithIndex((index, iter) => {
         // 第二个分区, 索引为1, 直接返回数据
         if (index == 1) {
           iter
         } else {
           // 空迭代器
           // Nil表示空集合
           Nil.iterator
         }
       })
   
       rdd1.collect()
     }
   
   }
   ```

#### 2.1.5.4，`flatMap(..)`：扁平映射

```scala
object FlatMap {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.makeRDD(List("Hello World", "Hello Java"))

    // 外层对内存返回的可迭代集合再进行扁平化处理
    val rdd1: RDD[String] = rdd.flatMap(str => {
      // 内层返回一个可迭代的集合
      str.split(" ")
    })

    rdd1.collect().foreach(println)
  }

}
```

1. **小练习：将 `List(List(1,2),3,List(4,5))` 进行扁平化操作**

   ```scala
   object FlatMap_Test {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
       val rdd = sc.makeRDD(List(List(1,2),3,List(4,5)))
   
       val rdd1 = rdd.flatMap(ele => {
         // 通过模式匹配进行实现
         ele match {
           case list: List[_] => list
           case data => List(data)
         }
       })
       rdd1.collect().foreach(println)
     }
   
   }
   ```

#### 2.1.5.5，`glom()`：分区数据

> 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变

```scala
object Glom {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // 进行分区处理
    // 返回值为Array[Int], 表示把同一个分区的数据作为一个数组返回
    val rdd1: RDD[Array[Int]] = rdd.glom()
    rdd1.collect().foreach(data => {
      // 打印分区下的数据
      println(data.mkString(", "))
    })
  }

}
```

1. **小练习：计算所有分区最大值求和（分区内取最大值，分区间最大值求和）**

   ```scala
   object Glom_Test {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
       val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
   
       // 进行分区处理
       // 返回值为Array[Int], 表示把同一个分区的数据作为一个数组返回
       val rdd1: RDD[Array[Int]] = rdd.glom()
       // 取分区内最大值
       val rdd2: RDD[Int] = rdd1.map(_.max)
       // 执行并求和
       println(rdd2.collect().sum)
     }
   
   }
   ```

#### 2.1.5.6，`groupBy(..)`：分组函数

> 在方法中以返回的数据作为  `key` 对目标数据进行分组并返回
>
> `groupBy()` 会将分区数据打散重组，这个操作称之为 `shuffle`
>
> <font color=red>一个组的数据在一个分区中，但并不是说一个分区中只有一个组</font>

```scala
object GroupBy {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    // 以 x % 2 获取的值作为key, 目标值作为value进行分组
    // 进行奇偶数据分组
    val rdd1: RDD[(Int, Iterable[Int])] = rdd.groupBy(x => x % 2)
    rdd1.collect().foreach(println)
  }

}
```

1. **小练习：将 List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组**

   ```scala
   object GroupBy_Test_1 {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
       val rdd: RDD[String] = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"))
       rdd.groupBy(x => x.charAt(0))
       rdd1.collect().foreach(println)
     }
   
   }
   ```

2. **小练习：WordCount**

   ```scala
   object GroupBy_Test_2 {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
       val rdd: RDD[String] = sc.makeRDD(List("hello spark", "hello java", "hello scala"))
       // 平铺集合数据, 拆解成一个一个的单次
       val rdd1: RDD[String] = rdd.flatMap(_.split(" "))
       // 对单次进行分组, 以每一个单次为key, value表示出现的每一次
       val rdd2: RDD[(String, Iterable[String])] = rdd1.groupBy(x => x)
       // 对一个单次对应的value进行取长度, 统计最终wordcount
       val rdd3: RDD[(String, Int)] = rdd2.map {
         case (ele, iter) => {
           (ele, iter.size)
         }
       }
       rdd3.collect().foreach(println)
     }
   
   }
   ```

#### 2.1.5.7，`filter(..)`：过滤函数

>将数据按照指定的规则进行筛选过滤，符合规则的保留，不符合规则的丢弃
>
><font color=red>数据过滤完成后，数组分区不变，在生产环境可能会产生数据不均衡问题</font>

```scala
object Filter {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // 过滤奇数
    val rdd1: RDD[Int] = rdd.filter(x => x % 2 == 0)
    rdd1.collect().foreach(println)
  }

}
```

1. **小练习：从服务器日志数据 apache.log 中获取 2015 年 5 月 17 日的请求路径**

   ```scala
   object Filter_Test {
   
     def main(args: Array[String]): Unit = {
       val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
       val sc: SparkContext = new SparkContext(sparkConf)
       val rdd: RDD[String] = sc.textFile("input/apache.log")
   
       // 遍历并过滤数据
       val rdd1: RDD[String] = rdd.filter(line => {
         // 取时间字段, 按时间段进行数据匹配
         val time: String = line.split(" ")(3)
         time.startsWith("17/05/2015")
       })
   
       rdd1.collect().foreach(println)
     }
   }
   ```

#### 2.1.5.8，`sample(..)`：抽取数据

> 根据指定的规则从数据集中抽取数据

```scala
object Sample {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8))
    // 抽取不放回
    // 第一个参数: 表示抽取不放回
    // 第二个参数: 表示数据被抽取的概率, 范围在[0, 1]之间, 0表示全不取, 1表示全取
    // 第三个参数: 随机数种子, 不填以当前时间为标准
    val rdd1: RDD[Int] = rdd.sample(false, 0.1)

    // 抽取放回
    // 第一个参数: 表示抽取并放回
    // 第二个参数: 表示每个数据期望被抽到的次数
    val rdd2: RDD[Int] = rdd.sample(true, 2)
    rdd1.collect().foreach(print)
    println()
    rdd2.collect().foreach(print)
  }

}
```

#### 2.1.5.9，`distinct(..)`：去重算子

> 将数据集中重复的数据去重

```scala
object Distinct {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4))

    // Scala: 去重通过把List转为Set实现
    // Spark: 去重通过reduceByKey()实现
    val rdd1: RDD[Int] = rdd.distinct()
    rdd1.collect().foreach(print)
  }

}
```

#### 2.1.5.10，`coalesce(..)`：缩减分区

> 根据数据量缩减分区，用于在大数据量过滤后，提升小数据量的执行效率；
>
> * `coalesce` 算子默认情况下，缩减分区是对多余的分区进行合并，不会 `shuffle`，可能会存在数据不均衡
> * 可以通过对第二个参数设置为 `true`，在缩减分区时候触发一个 `shuffle`；`shuffle` 是无规则的，最终结果可能与预期分区不一致
> * `coalesce()` 算子可以进行扩大分区操作，但是必须 `shuffle`

```scala
object Colease {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 此处默认表示10个分区
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4))
    val rdd1: RDD[Int] = rdd.coalesce(2)
    rdd1.saveAsTextFile("output/" + System.currentTimeMillis())

    // 带shuffle处理
    val rdd2: RDD[Int] = rdd.coalesce(2, true)
    rdd2.saveAsTextFile("output/" + System.currentTimeMillis())
  }

}
```

#### 2.1.5.11，`repartition(..)`：扩大分区

> * `coalesce()` 算子可以进行扩大分区操作，在扩大时必须要进行 `shuffle` 处理
> * `Spark` 内部提供了直接扩大分区的算子 `repartition()`，底层也是调用的 `coalesce()` 算子

```scala
object Repartition {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 此处默认表示10个分区
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4), 2)
    val rdd1: RDD[Int] = rdd.repartition(4)
    rdd1.saveAsTextFile("output/" + System.currentTimeMillis())
  }

}
```

#### 2.1.5.12，`sortBy(..)`：排序

```scala
object SortBy {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 5, 7, 2, 3, 9, 6), 2)
    // 单值顺序排序
    val rdd1: RDD[Int] = rdd.sortBy(x => x)
    rdd1.collect().foreach(print)
    println("-----")
    // 单值逆序排序
    val rdd2: RDD[Int] = rdd.sortBy(x => x, false)
    rdd2.collect().foreach(print)
    println("-----")
    // key-value处理
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(List(("1", 1), ("2", 2), ("11", 11)))
    // 按key进行排序, key是字符串类型, 会按照字典顺序排序
    val mapRdd1: RDD[(String, Int)] = mapRdd.sortBy(tuple => tuple._1)
    mapRdd1.collect().foreach(print)
    println("-----")
    // 按key进行排序, 转为数字进行排序
    val mapRdd2: RDD[(String, Int)] = mapRdd.sortBy(tuple => tuple._1.toInt)
    mapRdd2.collect().foreach(print)
    println("-----")
    // 按value进行排序
    val mapRdd3: RDD[(String, Int)] = mapRdd.sortBy(tuple => tuple._2)
    mapRdd3.collect().foreach(print)
  }

}
```

#### 2.1.5.13，双 `Value` 类型

> 双 `Value` 类型，包括多个算子，在这个章节里面一次性说明
>
> * <font color=red>交集、并集和差集要求两个RDD的类型必须保持一致；拉链操作数据类型可以不一致</font>
>
> * `intersection()`：取两组数据的交集
> * `union()`：取两组数据的并集，并集不会去重
> * `subtract()`：取两组数据的差集
> * `zip()`：拉链函数，将两个RDD中的元素，以第一个为主，进行 `Key` - `Value` 映射，必须位置相对应；
>   * <font color=red>在拉链时，两个RDD的元素数量必须一致，不然会报数量不一致异常；</font>
>   * <font color=red>RDD元素分区数量必须完全一致，不然会报分区数量不一致异常</font>
>   * <font color=red>此处不是真正的key-value格式，所以key值会重复，主要看左侧RDD的数据</font>

```scala
object DoubleValue {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rddA: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val rddB: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6))
    // 交集
    val rdd3: RDD[Int] = rddA.intersection(rddB)
    // 并集
    val rdd4: RDD[Int] = rddA.union(rddB)
    // 差集
    val rdd5: RDD[Int] = rddA.subtract(rddB)
    // 拉链
    val rdd6: RDD[(Int, Int)] = rddA.zip(rddB)
    rdd3.collect().foreach(print)
    println()
    rdd4.collect().foreach(print)
    println()
    rdd5.collect().foreach(print)
    println()
    rdd6.collect().foreach(print)
    println()
  }

}
```

#### 2.1.5.14，`partitionBy()`：重新分区

> 将数据按指定分区进行重新分区，此处可通过自定义分区器进行分区

```scala
object PartitionBy {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // PartitionBy算子只对key-value结构生效
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 4)))
    // RDD会通过隐式转换转换为PairRDDFunctions
    // partitionBy() 参数需要传递 Partitioner 的子类, 自定义分区器可继承该类进行处理
    val rdd1: RDD[(String, Int)] = rdd.partitionBy(new HashPartitioner(2))
    rdd1.saveAsTextFile("output/" + System.currentTimeMillis())
  }

}
```









### 2.1.6，行动算子

> 触发任务的调度和作业的执行