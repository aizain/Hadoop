package com.gyzh.zain.spark.maven.akka

import akka.actor.Actor
import java.util.HashMap
import java.util.ArrayList
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props

class Actor4() extends Actor {
  override def receive = {
  case msg: String => println(msg)
  }
}

object RemoteActor {
  def main(args: Array[String]): Unit = {
    // 配置信息封装到Map中
    val conf = new HashMap[String, Object]()
    val IP = "127.0.0.1"
    val PORT = "2550"
   
    val list = new ArrayList[String]()
    list.add("akka.remote.netty.tcp")
     
    conf.put("akka.remote.enabled-transports", list)  //参数是个集合
    conf.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    conf.put("akka.remote.netty.tcp.hostname", IP)
    conf.put("akka.remote.netty.tcp.port", PORT)
     
    val sys = ActorSystem("master", ConfigFactory.parseMap(conf))
    sys.actorOf(Props[Actor4], "jt")  //设定Actor的名字
    //访问地址akka.tcp://ActorSystem名称@IP:端口/user/jt    [akka.tcp://master@127.0.0.1:2550]
  }
  
}