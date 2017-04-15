package com.gyzh.zain.spark.maven.akka

import java.util.HashMap
import java.util.ArrayList
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props

object ClientActor1 {
  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("client", ConfigFactory.load().getConfig("ClientConf"))
    val actor = sys.actorSelection("akka.tcp://master@127.0.0.1:2550/user/jt")
    actor ! "我是Client"
  }
  
}