package com.gyzh.zain.spark.scala

object Hello {
  /**
   * 程序的入口main
   * java中规则：
   * 	public void main(String[] args) {}
   * 	<String> 泛型
   * 	返回值方函数前面 void
   * scala规则：
   * 	def定义方法或者称为函数
   * 	main入口方法名
   * 	args参数，先写参数名: 后面声明类型 name: String
   * 	[String] 泛型
   * 	返回值放在函数后面：Unit
   * 	加一个=号，后面方法体{}
   * 	
   */
  def main(args: Array[String]): Unit = {
    // 打印hello world
    print("hello world");
  }
  
}