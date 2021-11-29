package com.self.study.helloworld.collection.base

import scala.collection.mutable

/**
 * Queue队列测试
 * FIFO队列
 */
object MyQueue {

  def main(args: Array[String]): Unit = {
    val que = new mutable.Queue[String]()
    que.enqueue("123", "456", "789")
    println(que.dequeue())
    println(que.dequeue())
    println(que)
  }

}
