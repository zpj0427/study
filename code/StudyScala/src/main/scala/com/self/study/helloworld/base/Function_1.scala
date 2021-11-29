package com.self.study.helloworld.base

/**
 * 函数高阶用法
 */
object Function_1 {

  def main(args: Array[String]): Unit = {
    returnFunction
  }

  // 函数作为返回值返回
  def returnFunction(): Unit = {
    def f1() = {
      def f2(): Unit = {
        println("内部函数执行了")
      }

      f2 _
    }

    val f = f1()
    f()
    // 可以简化为该写法
    f1()()
  }

  // 函数可以作为参数进行传递
  def paramFunction(): Unit = {
    // 定义一个函数, 入参是一个函数签名
    def f1(functionName: (Int, Int) => Int): Int = {
      functionName(2, 4)
    }

    // 定义实际执行的函数, 即 functionName 表示的函数
    def add(one: Int, two: Int): Int = one + two
    // 函数调用
    println(add(1, 2))
    println(f1(add))
    println(f1(add _))
  }

  // 函数可以作为值进行传递
  def valueFunction(): Unit = {
    // 常规调用
    val num = foo
    println(num)

    // 方法作为一个整体传递, 后面加下划线
    val foo1 = foo _
    println(foo1())

    // 如果明确返回值, 不加下划线也可以
    val foo2: () => Int = foo
    println(foo2())

  }

  def foo(): Int = {
    println("执行了FOO...")
    1
  }

}
