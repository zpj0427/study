package com.self.study.helloworld.base

/**
 * 函数式编程
 */
object Function {

  def main(args: Array[String]): Unit = {
    def function(): Unit = {
      println("无参, 无返回值")
    }

    function()

    def functionWithReturn(): String = {
      println("无参, 有返回值_1")
      "无参, 有返回值_2"
    }

    val result = functionWithReturn()
    println(result)

    def functionWithParam(name: String): Unit = {
      println(name)
    }

    functionWithParam("有参, 无返回值")

    def functionWithParamAndReturn(name: String): String = {
      println("有参, 有返回值")
      name
    }

    val name = functionWithParamAndReturn("zhangsan")
    println(name)

    def functionWithManyParam(name: String, age: Int): Unit = {
      println(s"多参, 无返回值, $name -- $age")
    }

    functionWithManyParam("zhangsan", 123)

    // 可变参数
    def changeParam(args: String*): Unit = {
      println(args)
    }

    changeParam("zhangsan", "lisi")

    // 默认值参数
    def defParam(name: String, age: String = "123"): Unit = {
      println(s"$name -- $age")
    }

    defParam("zhangsan")
    defParam("zhangsan", "456")
    // 命名函数
    defParam(age = "567", name = "lisi")

  }


}
