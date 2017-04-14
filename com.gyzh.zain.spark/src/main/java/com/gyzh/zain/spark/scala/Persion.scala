package com.gyzh.zain.spark.scala

/**
 *  val定义，这个属性只读
 */
class Persion(val name: String, var name1: String) {
  val age: Int = 1
}

/**
 * case加了序列化，基础方法
 */
case class Persion2(val name: String, val name1: String) {
  val age: Int = 1
}