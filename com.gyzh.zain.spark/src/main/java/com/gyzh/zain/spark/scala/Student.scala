package com.gyzh.zain.spark.scala

class Student(override val name: String, override val name1: String, val school: String) extends Persion2(name, name1) {
  
}

class Student2(name: String, name1: String, val school: String) extends Persion2(name, name1) {
  
}