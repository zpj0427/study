package com.self.study.scala.construct

import scala.beans.BeanProperty

class Person(nameParam: String) {

  val TEST_NAME = "父类"

  @BeanProperty
  var name: String = nameParam

  var age: Int = _

  def this(name: String, age: Int) {
    this(name)
    this.age = age
    println("父类辅助构造方法执行了...")
  }

  def hello: Unit = {
    println("父类方法执行...")
  }

  println("父类主构造方法执行了...")

}

class SunPerson(nameParam: String, ageParam: Int) extends Person(nameParam, ageParam) {

  var sex: String = _

  override val TEST_NAME = "子类"

  def this(nameParam: String, ageParam: Int, sex: String) {
    this(nameParam, ageParam)
    this.sex = sex
    println("子类辅助构造方法执行了")
  }

  override def hello: Unit = {
    println("子类方法执行...")
  }

  println("子类主构造方法执行了...")

}

object Person {
  def main(args: Array[String]): Unit = {

    val sunPerson: SunPerson =  new SunPerson("zhangsan", 10, "男")
    sunPerson.hello
    println(sunPerson.TEST_NAME)

    val person: Person = new SunPerson("zhangsan", 10, "男")
    person.hello
    println(sunPerson.TEST_NAME)

    val realPerson: Person = new Person("")
    realPerson.hello
    println(realPerson.TEST_NAME )
  }
}
