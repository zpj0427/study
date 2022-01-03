package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: GroupBy
 * 将 List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组
 */
object GroupBy_Test_1 {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"))
    val rdd1: RDD[(Char, Iterable[String])] = rdd.groupBy(x => x.charAt(0))
    rdd1.collect().foreach(println)
  }

}
