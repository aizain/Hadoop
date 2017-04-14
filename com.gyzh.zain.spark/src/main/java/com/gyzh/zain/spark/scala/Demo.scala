package com.gyzh.zain.spark.scala

/**
 * Scala语法案例
 */
object Demo {
  def main(args: Array[String]): Unit = {
    val result1 = div(3, 2);
    val result2 = div2(3, 2);
    val result3 = div3(3, 2);
    val result31 = div3(3, 0);
    println(result1);
    println(result2);
    println(result3);
    println(result31);
    
    // result1 = 1 // 定义常量不能改变
    var r = 10 // 设置变量的初始值
    r = 20
    println(r)
    
    val c = if (r == 20) { 
      println("OK"); "OK"
    } else { 
      println("no OK"); "no OK"
    }
    println(c)
    
    // 定义一个数组，然后数组元素都+1
    val array = Array(1, 2, 3, 4, 5) // 可以省去new关键字
    for (a <- array) println(a) 
    val array2 = for (a <- array) yield a+1
    for (b <- array) println(b) 
    
    var r5 = ?(2,1)
    println(r5)
    
    
    
  }
  
  def div(a: Int, b: Int): Int = {
    a/b; // 约定最后一行的计算的结果就是返回的结果
  }
  
  def div2(a: Int, b: Int) = {
    a/b; // 约定最后一行的计算的结果就是返回的结果
  }
  
  def div3(a: Int, b: Int): Option[Int] = {
    if (b != 0) Some(a/b) else None
  }
  
  def ?(a: Int, b: Int): Option[Int] = {
    if (b != 0) Some(a/b) else None
  }
  
}