package com.gyzh.zain.spark.maven

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

/**
 * 线程并发
 */
object TestConcurrent {
  def main(args: Array[String]): Unit = {
    // 定义一个Future对象，实现线程并发
    var fu = Future {
      println("开始并行计算...")
      Thread.sleep(200)
      100
    }
    // 成功的化会触发下面方法
    fu.onSuccess {
      case x:Int => println(x)
      // case y:String => println(y)
    }
    
    Thread.sleep(500)
    println("main 结束")
  }
}