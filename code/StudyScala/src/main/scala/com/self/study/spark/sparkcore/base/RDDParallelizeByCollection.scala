package com.self.study.spark.sparkcore.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 集合_分区设定
 */
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
