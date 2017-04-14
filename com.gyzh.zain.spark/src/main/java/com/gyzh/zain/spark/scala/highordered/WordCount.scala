package com.gyzh.zain.spark.scala.highordered

import scala.io.Source

/**
 * 单词统计
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    //读取一个外部文件的每一行，并转换为List
    val list = Source.fromFile("""D:\tmp\words.txt""").getLines().toList
      .map(x => (x,1))       //映射成一个tuple
      .groupBy(x => x._1)    //按tuple的key进行分组
      .mapValues(i => i.map(t => t._2).reduce((x,y) => x+y))
    list.foreach( x=> println(x) )
  }
}