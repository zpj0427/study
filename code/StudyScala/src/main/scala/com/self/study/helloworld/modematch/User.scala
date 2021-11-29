package com.self.study.helloworld.modematch

class User(val name: String, val age: Int)

object User {

  def apply(name: String, age: Int): User = new User(name, age)

  def unapply(user: User): Option[(String, Int)] = {
    if (user == null)
      None
    else {
      Some(user.name, user.age)
    }
  }

}
