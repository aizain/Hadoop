package com.gyzh.zain.spark.maven.akka

import akka.actor.Actor
import java.util.HashMap
import java.util.ArrayList
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props

class Actor6() extends Actor {
  override def receive = {
  case msg: String => println(msg)
  }
}

object RemoteActor2 extends Conf {
  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("master", ConfigFactory.parseMap(getConf("2550")))
    sys.actorOf(Props[Actor6], "jt")  //设定Actor的名字
    //访问地址akka.tcp://ActorSystem名称@IP:端口/user/jt    [akka.tcp://master@127.0.0.1:2550]
  }
  
}