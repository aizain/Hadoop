package com.gyzh.zain.spark.maven

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * 线程并发
 */
object TestConcurrent3 {
  def main(args: Array[String]): Unit = {
    // 定义一个Future对象，实现线程并发
    val fu1 = Future {
      println("开始并行计算1...")
      Thread.sleep(200)
      100
    }
    
    val fu2 = Future {
      println("开始并行计算2...")
      Thread.sleep(400)
      100
    }
    
    val c = for(a<-fu1;b<-fu2) yield a+b
    
    // 阻塞等待所有线程完成，第二个参数为是否持久化。
    val r = Await.result(c, Duration.Inf)
    println(r)
    
    Thread.sleep(700)
    println("main 结束")
  }
}