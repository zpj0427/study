package com.self.study.spark.sparkcore.transform.simplevalue

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
