package com.gyzh.zain.spark.maven.akka

import java.util.HashMap
import java.util.ArrayList
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props

object ClientActor {
  def main(args: Array[String]): Unit = {
    // 配置信息封装到Map中
    val conf = new HashMap[String, Object]()
    val IP = "127.0.0.1"
    val PORT = "2551"
   
    val list = new ArrayList[String]()
    list.add("akka.remote.netty.tcp")
     
    conf.put("akka.remote.enabled-transports", list)  //参数是个集合
    conf.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    conf.put("akka.remote.netty.tcp.hostname", IP)
    conf.put("akka.remote.netty.tcp.port", PORT)
     
    val sys = ActorSystem("client", ConfigFactory.parseMap(conf))
    val actor = sys.actorSelection("akka.tcp://master@127.0.0.1:2550/user/jt")
    
    actor ! "我是Client"
    
  }
  
}