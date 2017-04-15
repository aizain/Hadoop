package com.gyzh.zain.spark.maven.akka

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem

// 定义第一个Actor
class Actor1() extends Actor {
    override def receive = {
      case msg: String => {
    	  println("Actor1接收到的消息：" + msg)
    	  // 给Actor2发送消息
    	  val a2 = context.actorOf(Props[Actor2]) // 从上下文对象中获取Actor2对象
    	  a2 ! "向Actor2发送的消息"
      }
    }
}

// 定义第二个Actor
class Actor2() extends Actor {
  override def receive = {
    case msg: String => {
      println("Actor2接收到的消息：" + msg)
    }
  }
}

object TestActor {
  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("sys") // 创建系统环境
    val a1 = sys.actorOf(Props[Actor1]) // 从系统环境中获取Actor1对象
    a1 ! "向Actor1发送的消息"
  }
}