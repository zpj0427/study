package com.self.study.spark.sparkcore.transform.keyvalue

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 转换算子_key-value_PartitionBy
 */
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
