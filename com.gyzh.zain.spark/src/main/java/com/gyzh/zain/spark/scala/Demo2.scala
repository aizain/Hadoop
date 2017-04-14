package com.gyzh.zain.spark.scala

/**
 * Scala语法案例2
 */
object Demo2 {
  def main(args: Array[String]): Unit = {
    for (x <- 1.to(10)) println(x)
    
    val name = "tony"
    val str = "this teacher is " + name
    println(str);
    
    // scala字符串上下文对象 StringContext
    val str1 = s"this teacher is ${name}"
    val str2 = s"this teacher is $name"
    println(str1);println(str2);
    
    val json = "{\"name\": \"tony\", \"age\": 10}" 
    println(json)
    val json1 = """{"name": "tony", "age": 10}"""
    println(json1)
    
    // fn变量
    // 匿名函数自动创建apply()，这个是Scala自动创建的
    var fn = (x: Int) => println(x)
    fn(10)
    
    var p = new Persion("tony", "hello") // Person对象
    p.name1 = "hehe"
    println(p.name)
    println(p.name1)
    println(p.age)
    
    var p2 = new Persion2("tom", "lulu");
    println(p2.toString());
    
  }
}