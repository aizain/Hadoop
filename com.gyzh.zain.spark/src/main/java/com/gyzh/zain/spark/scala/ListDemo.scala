package com.gyzh.zain.spark.scala

object ListDemo {
  def main(args: Array[String]): Unit = {
    // 编写算法
    val fn = (n: Int) => {
      n%2 == 0
    }
    
    val array = Array(1,2,3,4,5).toList
    // 产生新的多个数组，一个奇数数组，一个偶数数组
    println(array.partition(fn))
    
    val list = List(1,2,3,4)
    println(list(0)) // 获取数组中的第二个元素
    for (x <- list) println(x) // 遍历数据
    println(list.head)
    println(list.take(2))
    println(list.takeRight(2))
    println(list.tail) 
    println(list.length)
    println(list.:+(8)) // 在尾巴增加新元素
    println(list.+:(8)) // 在前面增加元素
    // 插入元素
    println(list.take(3) ::: List(0) ::: list.takeRight(list.length-3)) // 先截取前三个元素，再截取后面全部的元素，中间拼一个元素，之后通过三个:连接 
    // 删除
    list.drop(3)
    println(list)
    
  }
}