package com.self.study.helloworld.oo.construct

object SingleObject {

  val name: String = "zhangsan"

  def apply(): SingleObject = {
    println("调用了apply初始化")
    new SingleObject
  }

}

class SingleObject {

  val age: String = "12"

}

object Test {

  def main(args: Array[String]): Unit = {
    println(SingleObject.name)

    val obj = new SingleObject
    println(obj.age)
    // 不适用new关键字, 使用类名直接初始化
    // 调用的是半生对象的 apply()
    SingleObject()
  }

}
