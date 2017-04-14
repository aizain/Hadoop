package com.gyzh.zain.spark.scala

import scala.collection.mutable._

/**
 * 可变数组
 */
object ArrayBufferDemo {
  def main(args: Array[String]): Unit = {
    // 定义一个可变数组
    val ab = ArrayBuffer(1,2,3,4,5,6,7)
    ab += 6
    println(ab)
    
    // set集合不能重复，乱序
    val set = Set(1,1,2,3,4)
    println(set)
    println(set+=5)
    
    val map = Map(1 -> "北京")
    println(map += 2->"上海" += 3->"南京" += 4->"山西")
    println(map(2))
    println(map-=4)
    
    // tuple 元组(x1,x2,x3...)，类似数据库中的一条，最多22个
    val t1 = (1, "bj", "100086")
    println(t1)
    println(t1._2) // 获取某个元素_n，n的下标从1开始
    
    // Range
    val t2 = (1.to(23))
    println(t2)
    
    
  }
}