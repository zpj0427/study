package com.self.study.spark.sparkcore.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 文件_分区设定
 */
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
