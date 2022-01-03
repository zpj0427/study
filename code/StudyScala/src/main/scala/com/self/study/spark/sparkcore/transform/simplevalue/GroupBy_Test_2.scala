package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: GroupBy
 * WordCount
 */
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
