package com.gyzh.zain.spark.maven

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * 线程并发
 */
object TestConcurrent2 {
  def main(args: Array[String]): Unit = {
    // 定义一个Future对象，实现线程并发
    var fu = Future {
      println("开始并行计算...")
      Thread.sleep(200)
      100
    }
    
    // 阻塞等待所有线程完成，第二个参数为是否持久化。
    val r = Await.result(fu, Duration.Inf)
    println(r)
    
    Thread.sleep(500)
    println("main 结束")
  }
}