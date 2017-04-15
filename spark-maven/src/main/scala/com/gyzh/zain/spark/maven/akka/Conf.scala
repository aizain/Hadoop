package com.gyzh.zain.spark.maven.akka

import java.util.HashMap
import java.util.ArrayList

/**
 * 定义一个抽象类，获取配置参数
 */
trait Conf {
  def getConf(PORT: String) = {
    // 配置信息封装到Map中
    val conf = new HashMap[String, Object]()
    val IP = "127.0.0.1"
   
    val list = new ArrayList[String]()
    list.add("akka.remote.netty.tcp")
     
    conf.put("akka.remote.enabled-transports", list)  //参数是个集合
    conf.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    conf.put("akka.remote.netty.tcp.hostname", IP)
    conf.put("akka.remote.netty.tcp.port", PORT)
    conf
  }
    
}