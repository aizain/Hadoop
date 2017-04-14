package com.gyzh.zain.spark.scala

trait Trait {
  val name = "tony"
  // 抽象类中有个实现的方法
  def action = {println("action")}
  // 抽象方法
  def todo
}